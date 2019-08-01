package com.xiaohe.ysjspt.modules.repairer.controller;

import com.xiaohe.ysjspt.config.aspect.RepairerRequest;
import com.xiaohe.ysjspt.config.aspect.LogOperate;
import com.xiaohe.ysjspt.entity.*;
import com.xiaohe.ysjspt.modules.repairer.entity.QueryRepairer;
import com.xiaohe.ysjspt.modules.repairer.entity.Repairer;
import com.xiaohe.ysjspt.modules.repairer.service.RepairerService;
import com.xiaohe.ysjspt.modules.sys.controller.AbstractController;
import com.xiaohe.ysjspt.modules.sys.shiro.ShiroUtils;
import com.xiaohe.ysjspt.service.DeviceService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * RepairerController层
 *
 * @author gmq
 * @since 2018-12-10
 */
@RestController
@RequestMapping(value = "/repairers")
public class RepairerController extends AbstractController {
    private static Logger log = LoggerFactory.getLogger(RepairerController.class);
    @Autowired
    private RepairerService repairerService;

    @Autowired
    private DeviceService deviceService;


    /**
     * 保存对象<br/>
     *
     * @param repairer 对象
     */
    @PostMapping(value = "/")
    @RequiresPermissions("sys:repairers:save")
    @LogOperate(description = "新增维修工")
    public Result save(@Validated @RequestBody Repairer repairer) {
        Result result = new Result();
        try {
            //检查是否登录名重复
            List<String> allName = repairerService.findAllName();
            if (allName.contains(repairer.getLoginName())) {
                return result.error(2001, "用户名已存在");
            }
            repairer.setManagerId(getUserId().intValue());
            repairerService.save(repairer);
        } catch (Exception e) {
            log.error(e.getMessage());
            return result.error(2001, "新增失败");
        }
        return result.ok();
    }

    /**
     * 更新
     *
     * @param repairer
     * @return
     */
    @PutMapping(value = "/{id}")
    @RequiresPermissions("sys:repairers:update")
    @LogOperate(description = "更新维修工")
    public Result updateBanner(@Validated @RequestBody Repairer repairer) {
        Result result = new Result();
        try {
            boolean exists = repairerService.exists(repairer.getId());
            if (!exists) {
                return result.error(2002, "修改失败，未找到");
            }
            repairerService.save(repairer);
        } catch (Exception e) {
            log.error(e.getMessage());
            return result.error(2002, "修改失败");
        }
        return result.ok();
    }

    /**
     * 通过id删除对象
     *
     * @param id id集合
     */
    @DeleteMapping(value = "/{id}")
    @RequiresPermissions("sys:repairers:delete")
    @LogOperate(description = "删除维修工")
    public Result deleteById(@PathVariable("id") Integer id) {
        Result result = new Result();
        try {
            repairerService.delete(id);
            //解绑修工绑定的设备
            List<Integer> integers = new LinkedList<>();
            integers.add(id);
            List<DeviceEntity> byRepairerIdIn = deviceService.findByRepairerIdIn(integers);
            for (DeviceEntity deviceEntity : byRepairerIdIn) {
                deviceEntity.setRepairerId(0);
            }
            deviceService.saveAll(byRepairerIdIn);
        } catch (Exception e) {
            log.error(e.getMessage());
            return result.error(2003, "删除失败");
        }
        return result.ok();
    }


    /**
     * 通过id查找对象
     *
     * @param id id
     * @return Repairer 对象
     */
    @GetMapping(value = "/{id}")
    @RequiresPermissions("sys:repairers:info")
    public Result findById(@PathVariable("id") Integer id) {
        CommonResult result = new CommonResult();
        try {
            result.setDatas(repairerService.findById(id));
        } catch (Exception e) {
            log.error(e.getMessage());
            return result.error(2004, "不存在");
        }
        return result.ok();
    }

    /**
     * 分页查询
     *
     * @return Page<Repairer> 对象
     */
    @PostMapping(value = "")
    @RequiresPermissions("sys:repairers:list")
    public Result findByPage(@RequestBody QueryRepairer queryRepairer) {

        CommonResult result = new CommonResult();
        try {
            int page = queryRepairer.getPage();
            int pageSize = queryRepairer.getPageSize();
            //商户过滤 admin全部看到
            queryRepairer.setManagerId(getUserId()==1?null:getUserId().intValue());
            Page<Repairer> all = repairerService.findAll(page - 1, pageSize, queryRepairer);
            PageBean<Repairer> pageBean = new PageBean<>();
            if (all == null) {
                pageBean.setList(new ArrayList<>());
                result.setDatas(pageBean);
                return result.ok();
            }
            pageBean.setCurPage(page);
            pageBean.setItemCounts((int) all.getTotalElements());
            pageBean.setPageSize(pageSize);
            pageBean.setList(all.getContent());
            result.setDatas(pageBean);
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage());
            return result.error(2005, "查询出错");
        }
        return result.ok();
    }

    /**
     * 商户获取置顶维修工已绑定设备列表
     *
     * @param id 维修工id
     * @return
     */
    @GetMapping("/dev/{id}")
    @RequiresPermissions("sys:repairers:unbind")
    public Result getDevList(@PathVariable Integer id, @RequestParam(value = "page", defaultValue = "1") Integer page,
                             @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize) {
        CommonResult result = new CommonResult();
        try {
            Page<DeviceEntity> all = deviceService.getRepairerDevByPage(id, page - 1, pageSize);
            PageBean<DeviceEntity> pageBean = new PageBean<>();
            if (all == null) {
                pageBean.setList(new ArrayList<>());
                result.setDatas(pageBean);
                return result.ok();
            }
            pageBean.setCurPage(page);
            pageBean.setItemCounts((int) all.getTotalElements());
            pageBean.setPageSize(pageSize);
            pageBean.setList(all.getContent());
            result.setDatas(pageBean);
        } catch (Exception e) {
            log.error(e.getMessage());
            return result.error(2004, "不存在");
        }
        return result.ok();
    }

    /**
     * 获取维修工列表
     *
     * @return
     */
    @GetMapping("/own")
    public Result getRepairerList() {
        CommonResult result = new CommonResult();
        try {
            Integer managerId = ShiroUtils.getUserEntity().getUserId().intValue();
            List<Repairer> repairerDev = repairerService.findAllByManagerId(managerId);
            if (CollectionUtils.isEmpty(repairerDev)) {
                result.setDatas(new ArrayList());
                return result;
            }
            result.setDatas(repairerDev);
        } catch (Exception e) {
            log.error(e.getMessage());
            return result.error(2004, "不存在");
        }
        return result.ok();
    }

    /**
     * 查看设备故障
     * faultFlag  1 故障设备 0所有设备
     *
     * @return
     */
    @PostMapping("/devFaults")
    @RepairerRequest
    public Result devFault(@RequestBody QueryRepairer queryRepairer) {
        CommonResult commonResult = new CommonResult();
        int page = queryRepairer.getPage();
        int pageSize = queryRepairer.getPageSize();
        if (page < 1) {
            commonResult.setMsg("参数非法");
            commonResult.setRet(2007);
            return commonResult;
        }
        page--;

        Page<DeviceEntity> all;
        long count;
        all = repairerService.findAllDev(page, pageSize, queryRepairer);

        count = all.getTotalElements();
        List<DeviceEntity> deviceEntities = all.getContent();
        //是否过滤异常设备
        if (Integer.valueOf(1).equals(queryRepairer.getFaultFlag())) {
            //分页返回的list
            List<DeviceEntity> listEnd;
            //自己所有的故障设备
            List<DeviceEntity> errorList = repairerService.findAllDevFaultByRepairerId(queryRepairer.getId(),queryRepairer.getDevNo());

            PageBean<DeviceEntity> pageBean = new PageBean<>();
            int total = errorList.size();
            if (CollectionUtils.isEmpty(errorList)) {
                pageBean.setList(new ArrayList<>());
                commonResult.setDatas(pageBean);
            }
            listEnd = errorList.subList((page) * pageSize, page + 1 <= (total / pageSize) ? page * pageSize : total);
            pageBean.setCurPage(page);
            pageBean.setItemCounts(total);
            pageBean.setPageSize(pageSize);
            pageBean.setList(listEnd);
            commonResult.setDatas(pageBean);
            return commonResult.ok();
        }
        PageBean<DeviceEntity> datas = new PageBean<>();
        datas.setCurPage(page + 1);
        datas.setItemCounts((int) count);
        datas.setPageSize(pageSize);
        datas.setList(deviceEntities);

        commonResult.setDatas(datas);
        return commonResult.ok();
    }

    /**
     * 商户获取维修工绑定和未绑定设备列表
     *
     * @param id 维修工id
     * @return
     */
    @GetMapping("/devBind/{id}")
    @RequiresPermissions("sys:repairers:unbind")
    public Result getDevList2(@PathVariable Integer id, @RequestParam(value = "page", defaultValue = "1") Integer page,
                              @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize) {
        CommonResult result = new CommonResult();
        try {

            //维修工已绑定和未绑定的设备
            Page<DeviceEntity> all = deviceService.getRepairerDev(getUserId(), id, page - 1, pageSize);
            PageBean<DeviceEntity> pageBean = new PageBean<>();
            if (all == null) {
                pageBean.setList(new ArrayList<>());
                result.setDatas(pageBean);
                return result.ok();
            }
            pageBean.setCurPage(page);
            pageBean.setItemCounts((int) all.getTotalElements());
            pageBean.setPageSize(pageSize);
            pageBean.setList(all.getContent());
            result.setDatas(pageBean);
        } catch (Exception e) {
            log.error(e.getMessage());
            return result.error(2004, "查询出错");
        }
        return result.ok();
    }

}