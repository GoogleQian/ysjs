package com.xiaohe.ysjspt.controller;

import com.xiaohe.ysjspt.entity.*;
import com.xiaohe.ysjspt.log.ControllerLog;
import com.xiaohe.ysjspt.service.OrdersService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

/**
 * @author wzq
 */
@RestController
@RequestMapping("/order")
public class OrderController {
    @Autowired
    OrdersService ordersService;

    /**
     * 分页查询
     *
     * @param page
     * @param pageSize
     * @return Page<TbOrders>
     * @throws Exception
     */
    @RequestMapping(value = "/findAll")
    @ControllerLog(description = "findAll")
    @RequiresPermissions("sys:order:list")
    public Result findAll(@RequestParam(value = "page", required = false, defaultValue = "1") Integer page,
                          @RequestParam(value = "page_size", required = false, defaultValue = "1") Integer pageSize,
                          @RequestParam(value = "device_id", required = false) String deviceId) {
        CommonResult result = new CommonResult();
        PageBean<Order> pageBean = new PageBean<>();
        Page<Order> all = ordersService.findAll(page, pageSize, deviceId);
        result.ok();
        if (all == null) {
            pageBean.setCurPage(1);
            pageBean.setItemCounts(0);
            pageBean.setList(new ArrayList<>());
            pageBean.setPageSize(10);
            result.setDatas(pageBean);
            return result;
        }
        pageBean.setCurPage(page);
        pageBean.setPageSize(pageSize);
        pageBean.setList(all.getContent());
        pageBean.setItemCounts((int) all.getTotalElements());
        result.setDatas(pageBean);

        return result;
    }
}
