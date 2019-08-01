package com.xiaohe.ysjspt.controller;

import com.xiaohe.ysjspt.entity.CommonResult;
import com.xiaohe.ysjspt.entity.HistoryEntity;
import com.xiaohe.ysjspt.entity.PageBean;
import com.xiaohe.ysjspt.entity.Result;
import com.xiaohe.ysjspt.log.ControllerLog;
import com.xiaohe.ysjspt.service.HistoryService;
import com.xiaohe.ysjspt.utils.DateUtil;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

/**
 * @author Administrator
 */
@RestController
@RequestMapping("/history")
public class HistoryController {

    @Autowired
    HistoryService historyService;

    @RequestMapping(value = "/get_info")
    @ControllerLog(description = "get_info")
    @RequiresPermissions("sys:water:list")
    public Result getInfo(@RequestParam(value = "page", required = false, defaultValue = "1") Integer page,
                          @RequestParam(value = "page_size", required = false, defaultValue = "10") Integer pageSize,
                          @RequestParam(value = "device_id", required = false, defaultValue = "") String deviceId,
                          @RequestParam(value = "start_time", required = false) Long startTime,
                          @RequestParam(value = "end_time", required = false) Long endTime) {
        CommonResult commonResult = new CommonResult();
        if (page < 1) {
            commonResult.setMsg("参数非法");
            commonResult.setRet(2007);
            return commonResult;
        }
        page--;

        Page<HistoryEntity> all;
        long count;
        Date startTime1 = null;
        Date endTime1 = null;
        if (startTime != null) {
            startTime1 = DateUtil.stampToDate(startTime + "");
        }
        if (endTime != null) {
            endTime1 = DateUtil.stampToDate(endTime + "");
        }
        all = historyService.findAll(page, pageSize, deviceId, startTime1, endTime1);
        commonResult.setRet(0);
        commonResult.setMsg("success");
        PageBean<HistoryEntity> datas = new PageBean<>();
        if (all == null) {
            datas.setCurPage(1);
            datas.setItemCounts(0);
            datas.setPageSize(10);
            datas.setList(new ArrayList<>());
            commonResult.setDatas(datas);
            return commonResult;
        }
        count = all.getTotalElements();
        List<HistoryEntity> historyEntities = all.getContent();

        commonResult.setRet(0);
        commonResult.setMsg("success");


        datas.setCurPage(page + 1);
        datas.setItemCounts((int) count);
        datas.setPageSize(pageSize);
        datas.setList(historyEntities);

        commonResult.setDatas(datas);
        return commonResult;

    }
}
