package com.xiaohe.ysjspt.modules.complaints.controller;

import com.xiaohe.ysjspt.config.aspect.RepairerRequest;
import com.xiaohe.ysjspt.config.aspect.LogOperate;
import com.xiaohe.ysjspt.entity.CommonResult;
import com.xiaohe.ysjspt.entity.DeviceEntity;
import com.xiaohe.ysjspt.entity.PageBean;
import com.xiaohe.ysjspt.entity.Result;
import com.xiaohe.ysjspt.modules.complaints.entity.Complaints;
import com.xiaohe.ysjspt.modules.complaints.entity.ComplaintsVo;
import com.xiaohe.ysjspt.modules.complaints.service.ComplaintsService;
import com.xiaohe.ysjspt.service.DeviceService;
import com.xiaohe.ysjspt.utils.DevUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 投诉建议表 ComplaintsController层
 * 维修员登录
 *
 * @author gmq
 * @since 2018-12-10
 */
@RestController
@RequestMapping(value = "/complaint")
public class ComplaintController {
    private static Logger log = LoggerFactory.getLogger(ComplaintController.class);
    @Autowired
    private ComplaintsService complaintsService;
    @Autowired
    private DeviceService deviceService;

    /**
     * 保存对象<br/>
     *
     * @param complaints 对象
     */
    @PostMapping
    @RepairerRequest
    @LogOperate(description = "新增投诉建议")
    public Result save(@Validated @RequestBody Complaints complaints) {
        Result result = new Result();
        try {
            Date date = new Date();
            complaints.setReportTime(date);
            complaints.setUpdateTime(date);
            complaintsService.save(complaints);
        } catch (Exception e) {
            log.error(e.getMessage());
            return result.error(2001, "新增失败");
        }
        return result.ok();
    }

    /**
     * 更新
     *
     * @param complaints
     * @return
     */
    @PutMapping(value = "/{id}")
    @RepairerRequest
    @LogOperate(description = "更新投诉建议")
    public Result updateBanner(@Validated @RequestBody Complaints complaints, @PathVariable("id") String id) {
        Result result = new Result();
        try {
            boolean exists = complaintsService.exists(complaints.getId());
            if (!exists) {
                return result.error(2002, "修改失败，未找到");
            }
            complaints.setUpdateTime(new Date());
            complaintsService.save(complaints);
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
    @RepairerRequest
    @LogOperate(description = "删除投诉建议")
    public Result deleteById(@PathVariable("id") Integer id) {
        Result result = new Result();
        try {
            complaintsService.delete(id);
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
     * @return Complaints 对象
     */
    @GetMapping(value = "/{id}")
    @RepairerRequest
    public Result findById(@PathVariable("id") Integer id) {
        CommonResult result = new CommonResult();
        try {
            result.setDatas(complaintsService.findById(id));
        } catch (Exception e) {
            log.error(e.getMessage());
            return result.error(2004, "不存在");
        }
        return result.ok();
    }

    /**
     * 分页查询
     *
     * @return Page<Complaints> 对象
     */
    @PostMapping(value = "/pages")
    @RepairerRequest
    public Result findByPage(@RequestBody ComplaintsVo complaintsVo) {

        CommonResult result = new CommonResult();
        int page = complaintsVo.getPage();
        int pageSize = complaintsVo.getPageSize();
        PageBean<Complaints> pageBean = new PageBean<>();
        pageBean.setCurPage(page);
        pageBean.setPageSize(pageSize);
        List<DeviceEntity> deviceEntities = deviceService.getRepairerDev(complaintsVo.getRepairerId());
        if (CollectionUtils.isEmpty(deviceEntities)) {
            pageBean.setList(new ArrayList<>());
            result.setDatas(pageBean);
            return result.ok();
        }
        try {
            Page<Complaints> all = complaintsService.findAll(page - 1, pageSize, complaintsVo, DevUtils.getDevCode(deviceEntities));
            if (all == null) {
                pageBean.setList(new ArrayList<>());
                result.setDatas(pageBean);
                return result.ok();
            }
            pageBean.setItemCounts((int) all.getTotalElements());
            pageBean.setList(all.getContent());
            result.setDatas(pageBean);
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage());
            return result.error(2005, "查询出错");
        }
        return result.ok();
    }
}