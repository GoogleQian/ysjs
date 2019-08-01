package com.xiaohe.ysjspt.controller;

import com.xiaohe.ysjspt.entity.CommonResult;
import com.xiaohe.ysjspt.entity.PageBean;
import com.xiaohe.ysjspt.entity.Result;
import com.xiaohe.ysjspt.entity.Status;
import com.xiaohe.ysjspt.service.StatusService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

/**
 * 设备状态表 StatusController层
 *
 * @author gmq
 * @since 2018-11-26
 */
@RestController
@RequestMapping(value = "/status")
public class StatusController {
    private static Logger log = LoggerFactory.getLogger(StatusController.class);
    @Autowired
    private StatusService statusService;

    /**
     * 保存对象<br/>
     *
     * @param status
     * @throws Exception
     */
    @PostMapping(value = "/")
    public Result save(Status status) {
        Result result = new Result();
        try {
            statusService.save(status);
        } catch (Exception e) {
            log.error(e.getMessage());
            return result.error(2000, "新增失败");
        }
        return result.ok();
    }

    /**
     * 修改对象<br/>
     *
     * @param id
     * @throws Exception
     */
    @PutMapping(value = "/{id}")
    public Result update(@PathVariable(value = "id") Integer id, @RequestBody Status status) {
        Result result = new Result();
        try {
            statusService.save(status);
        } catch (Exception e) {
            log.error(e.getMessage());
            return result.error(2000, "修改失败");
        }
        return result.ok();
    }

    /**
     * 通过id删除对象
     *
     * @param id
     * @throws Exception
     */
    @DeleteMapping(value = "/{id}")
    public Result deleteById(@PathVariable(value = "id") Integer id) {
        Result result = new Result();
        try {
            statusService.delete(id);
        } catch (Exception e) {
            log.error(e.getMessage());
            return result.error(2000, "删除失败");
        }
        return result.ok();
    }

    /**
     * 通过id查找对象
     *
     * @param id
     * @return Status
     * @throws Exception
     */
    @GetMapping(value = "/{id}")
    public Result findById(@PathVariable(value = "id") Integer id) {
        CommonResult result = new CommonResult();
        try {
            result.setDatas(statusService.findById(id));
        } catch (Exception e) {
            log.error(e.getMessage());
            return result.error(2000, "未查找");
        }
        return result.ok();
    }

    /**
     * 分页查询
     *
     * @param page
     * @param pageSize
     * @return Page<Status>
     * @throws Exception
     */
    @GetMapping(value = "/")
    public Result findAll(@RequestParam(value = "page", required = false, defaultValue = "1") Integer page,
                          @RequestParam(value = "pageSize", required = false, defaultValue = "10") Integer pageSize
            , @RequestParam(value = "imei", required = false, defaultValue = "") String imei
            , @RequestParam(value = "start_time", required = false, defaultValue = "") String startTime
            , @RequestParam(value = "end_time", required = false, defaultValue = "") String endTime) {
        CommonResult result = new CommonResult();
        try {
            Status status = new Status();
            status.setImei(imei);
            status.setStartTime(startTime);
            status.setEndTime(endTime);
            Page<Status> all = statusService.findAll(page - 1, pageSize, status);
            PageBean<Status> pageBean = new PageBean<>();
            if (all == null) {
                pageBean.setList(new ArrayList<>());
                result.setDatas(pageBean);
                return result.ok();
            }
            pageBean.setCurPage(page);
            pageBean.setPageSize(pageSize);
            pageBean.setList(all.getContent());
            pageBean.setItemCounts((int) all.getTotalElements());
            result.setDatas(pageBean);
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage());
            return result.error(2000, "查询出错");
        }
        return result.ok();
    }
}