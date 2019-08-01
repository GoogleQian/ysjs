package com.xiaohe.ysjspt.modules.repairedrecord.controller;

import com.xiaohe.ysjspt.config.aspect.LogOperate;
import com.xiaohe.ysjspt.config.validate.XException;
import com.xiaohe.ysjspt.entity.CommonResult;
import com.xiaohe.ysjspt.entity.DeviceEntity;
import com.xiaohe.ysjspt.entity.PageBean;
import com.xiaohe.ysjspt.entity.Result;
import com.xiaohe.ysjspt.modules.complaints.service.ComplaintsService;
import com.xiaohe.ysjspt.modules.repairedrecord.entity.RepairRecord;
import com.xiaohe.ysjspt.modules.repairedrecord.entity.RepairRecordVo;
import com.xiaohe.ysjspt.modules.repairedrecord.service.RepairRecordService;
import com.xiaohe.ysjspt.modules.repairer.entity.Repairer;
import com.xiaohe.ysjspt.modules.repairer.service.RepairerService;
import com.xiaohe.ysjspt.modules.sys.entity.SysUserEntity;
import com.xiaohe.ysjspt.modules.sys.shiro.ShiroUtils;
import com.xiaohe.ysjspt.modules.sys.untils.Constant;
import com.xiaohe.ysjspt.service.DeviceService;
import com.xiaohe.ysjspt.utils.DevUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 维修详情表 RepairRecordController层
 *
 * @author gmq
 * @since 2018-12-10
 */
@RestController
@RequestMapping(value = "/repairRecords")
public class RepairRecordsController {
    private static Logger log = LoggerFactory.getLogger(RepairRecordsController.class);
    @Autowired
    private RepairRecordService repairRecordService;
    @Autowired
    private RepairerService repairerService;
    @Autowired
    private ComplaintsService complaintsService;
    @Autowired
    private DeviceService deviceService;

    /**
     * 保存对象<br/>
     *
     * @param repairRecord 对象
     */
    @PostMapping(value = "/")
    @LogOperate(description = "新增维修记录")
    public Result save(@Validated @RequestBody RepairRecord repairRecord) {
        Result result = new Result();
        try {
            if (repairRecord.getMaintenanceTime() == null) {
                Date date = new Date();
                repairRecord.setMaintenanceTime(date);
            }
            DeviceEntity deviceEntity = deviceService.findByDeviceId(repairRecord.getDevCode());
            if (deviceEntity == null) {
                throw new XException("设备不存在");
            }
            repairRecordService.save(repairRecord);
//            complaintsService.updateStatusByDevCode(repairRecord.getDevCode(), 1, new Date());
        } catch (Exception e) {
            log.error(e.getMessage());
            return result.error(2001, "新增失败");
        }
        return result.ok();
    }

    /**
     * 更新
     *
     * @param repairRecord
     * @return
     */
    @PutMapping(value = "/{id}")
    @RequiresPermissions("sys:repair-record:update")
    @LogOperate(description = "更新维修记录")
    public Result updateBanner(@Validated @RequestBody RepairRecord repairRecord, @PathVariable("id") Integer id) {
        Result result = new Result();
        try {
            boolean exists = repairRecordService.exists(repairRecord.getId());
            if (!exists) {
                return result.error(2002, "修改失败，未找到");
            }
            repairRecordService.save(repairRecord);
        } catch (Exception e) {
            log.error(e.getMessage());
            return result.error(2002, "修改失败");
        }
        return result.ok();
    }

    /**
     * 通过id集合删除对象
     *
     * @param id id集合
     */
    @DeleteMapping(value = "/{id}")
    @RequiresPermissions("sys:repair-record:delete")
    @LogOperate(description = "删除维修记录")
    public Result deleteById(@PathVariable("id") Integer id) {
        Result result = new Result();
        try {
            repairRecordService.delete(id);
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
     * @return RepairRecord 对象
     */
    @GetMapping(value = "/{id}")
    @RequiresPermissions("sys:repair-record:info")
    public Result findById(@PathVariable("id") Integer id) {
        CommonResult result = new CommonResult();
        try {
            result.setDatas(repairRecordService.findById(id));
        } catch (Exception e) {
            log.error(e.getMessage());
            return result.error(2004, "不存在");
        }
        return result.ok();
    }

    /**
     * 分页查询
     *
     * @return Page<RepairRecord> 对象
     */
    @PostMapping(value = "/pages")
    @RequiresPermissions("sys:repair-record:list")
    public Result findByPage(@RequestBody RepairRecordVo repairRecordVo) {

        CommonResult result = new CommonResult();
        //商户登录
        SysUserEntity sysUserEntity = ShiroUtils.getUserEntity();
        List<Integer> ids = null;
        //获取维修人员列表
        if (sysUserEntity != null) {
            Long userId = sysUserEntity.getUserId();
            List<Repairer> repairers;
            //超级管理员查看所有
            if (userId.intValue() == Constant.SUPER_ADMIN) {
                repairers = repairerService.findList();
            } else {
                repairers = repairerService.findAllByManagerId(userId.intValue());
            }
            ids = DevUtils.getIntIds(repairers);
        }

        try {
            int page = repairRecordVo.getPage();
            int pageSize = repairRecordVo.getPageSize();
            Page<RepairRecord> all = repairRecordService.findAll(page - 1, pageSize, repairRecordVo, ids);
            PageBean<RepairRecord> pageBean = new PageBean<>();
            if (all == null) {
                pageBean.setList(new ArrayList<>());
                result.setDatas(pageBean);
                return result.ok();
            }
            pageBean.setCurPage(page);
            pageBean.setItemCounts((int) all.getTotalElements());
            pageBean.setPageSize(pageSize);
            List<RepairRecord> records = all.getContent();
            for (RepairRecord record : records) {
                Repairer repairer = repairerService.findById(record.getRepairerId());
                if (repairer != null) {
                    record.setRepairerName(repairer.getRealName());
                }
            }
            pageBean.setList(records);
            result.setDatas(pageBean);
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage());
            return result.error(2005, "查询出错");
        }
        return result.ok();
    }
}