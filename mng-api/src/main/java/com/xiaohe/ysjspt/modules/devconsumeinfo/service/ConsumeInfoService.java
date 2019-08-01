package com.xiaohe.ysjspt.modules.devconsumeinfo.service;

import com.xiaohe.ysjspt.modules.devconsumeinfo.entity.ConsumeInfo;
/**
 * <p>
 * 财务分析 ConsumeInfoService服务类
 * </p>
 *
 * @author gmq
 * @since 2018-12-17
 */
public interface ConsumeInfoService {
    /**
     * 查询指定时间的消费信息
     * @param type 时间类型 0 自定义时间范围 1今天 2最近七天 3本月 4上个月
     * @param devNo 设备编号
     * @param startTime 开始时间
     * @param endTime 截止时间
     * @return ConsumeInfo
     */
    ConsumeInfo getConsumeInfoByTimes(Integer type, String devNo,String startTime,String endTime);

    /**
     * 查询指定时间的消费排行
     * @param type 时间类型 0 自定义时间范围 1今天 2最近七天 3本月 4上个月
     * @param startTime 开始时间
     * @param endTime 截止时间
     * @return ConsumeInfo
     */
    ConsumeInfo getRankByTimes(Integer type,String startTime,String endTime,Integer userId);



}
