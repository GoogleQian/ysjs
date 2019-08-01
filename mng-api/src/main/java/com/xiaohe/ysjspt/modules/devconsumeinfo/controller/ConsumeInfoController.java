package com.xiaohe.ysjspt.modules.devconsumeinfo.controller;

import com.xiaohe.ysjspt.entity.CommonResult;
import com.xiaohe.ysjspt.entity.Result;
import com.xiaohe.ysjspt.modules.devconsumeinfo.entity.ConsumeInfo;
import com.xiaohe.ysjspt.modules.devconsumeinfo.service.ConsumeInfoService;
import com.xiaohe.ysjspt.modules.sys.controller.AbstractController;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * @program: ysjsApi
 * @description: 消费统计信息
 * @author: Gmq
 * @date: 2018-12-17 11:18
 **/
@RestController
@RequestMapping("/consumeInfo")
@RequiresPermissions("sys:statictics:list")
public class ConsumeInfoController extends AbstractController {
    private static Logger log = LoggerFactory.getLogger(ConsumeInfoController.class);
    @Autowired
    private ConsumeInfoService consumeInfoService;


    /**
     * 商户设备消费排行
     *
     * @param type      日期参数
     * @param devNo     设备编号
     * @param startTime 开始时间
     * @param endTime   结束时间
     * @return Result
     */
    @GetMapping("/consume")
    public Result getConsumeInfoByTime(@RequestParam(value = "type") Integer type,
                                       @RequestParam(value = "devNo") String devNo,
                                       @RequestParam(value = "startTime", required = false) String startTime,
                                       @RequestParam(value = "endTime", required = false) String endTime) {
        CommonResult result = new CommonResult();
        try {
            ConsumeInfo consumeInfo = consumeInfoService.getConsumeInfoByTimes(type, devNo, startTime, endTime);
            Map<String, Object> map = new HashMap<>(2);
            map.put("consumeInfo", consumeInfo);
            map.put("cup", consumeInfo == null ? null : consumeInfo.getCup());
            result.setDatas(map);
        } catch (Exception e) {
            log.error(e.getMessage());
            return result.error(2001, "获取出错");
        }
        return result.ok();
    }

    /**
     * 商户设备消费排行
     *
     * @param type      日期参数
     * @param startTime 开始时间
     * @param endTime   结束时间
     * @return Result
     */
    @GetMapping("/rank")
    public Result getRankByTime(@RequestParam(value = "type") Integer type,
                                @RequestParam(value = "startTime", required = false) String startTime,
                                @RequestParam(value = "endTime", required = false) String endTime) {
        CommonResult result = new CommonResult();
        try {
            ConsumeInfo consumeInfo = consumeInfoService.getRankByTimes(type, startTime, endTime, getUserId().intValue());
            result.setDatas(consumeInfo);
        } catch (Exception e) {
            log.error(e.getMessage());
            return result.error(2001, "获取出错");
        }
        return result.ok();
    }
}
