package com.xiaohe.ysjspt.modules.devconsumeinfo.service.impl;

import com.xiaohe.ysjspt.config.validate.XException;
import com.xiaohe.ysjspt.modules.devconsumeinfo.entity.ConsumeInfo;
import com.xiaohe.ysjspt.modules.devconsumeinfo.jpa.ConsumeInfoRepository;
import com.xiaohe.ysjspt.modules.devconsumeinfo.service.ConsumeInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @program: ysjsApi
 * @description: 消费统计 实现类
 * @author: Gmq
 * @date: 2018-12-17 11:35
 */
@Service
public class ConsumeInfoServiceImpl implements ConsumeInfoService {

    @Autowired
    private ConsumeInfoRepository consumeInfoRepository;

    /**
     * 查询指定时间的消费信息
     *
     * @param type      时间类型 0 自定义时间范围 1今天 2最近七天 3本月 4上个月
     * @param devNo 设备编号
     * @param startTime 开始时间
     * @param endTime 截止时间
     * @return
     */
    @Override
    public ConsumeInfo getConsumeInfoByTimes(Integer type, String devNo, String startTime, String endTime) {
        ConsumeInfo consumeInfo = new ConsumeInfo();
        List<Object[]> list = new ArrayList();
        List<Object[]> waterList = new ArrayList();
        try {
            switch (type) {
                case 0:
                    list = consumeInfoRepository.findConsumeByTime(devNo, startTime, endTime);
                    waterList = consumeInfoRepository.findWaterAmountByTime(devNo,devNo,startTime, endTime);
                    break;
                case 1:
                    list = consumeInfoRepository.findConsumeByDay(devNo);
                    waterList = consumeInfoRepository.findWaterAmountByDay(devNo,devNo);
                    break;
                case 2:
                    list = consumeInfoRepository.findConsumeByWeek(devNo);
                    waterList = consumeInfoRepository.findWaterAmountByWeek(devNo,devNo);
                    break;
                case 3:
                    list = consumeInfoRepository.findConsumeByCurrentMonth(devNo);
                    waterList = consumeInfoRepository.findWaterAmountByCurrentMonth(devNo,devNo);
                    break;
                case 4:
                    list = consumeInfoRepository.findConsumeByLastMonth(devNo);
                    waterList = consumeInfoRepository.findWaterAmountByLastMonth(devNo,devNo);
                    break;
                default:
                    break;
            }
            if (CollectionUtils.isEmpty(list) || CollectionUtils.isEmpty(waterList)) {
                return null;
            }
            //消费大杯小杯
            String[] cupName = new String[waterList.size()];
            List<Map<String,Object>> cupUseCount = new ArrayList<>();
            for (int i = 0; i < waterList.size(); i++) {
                Object[] objects = waterList.get(i);
                Map<String, Object> map = new HashMap<>();
                String name = objects[1] == null ? null : objects[1].toString();
                int count = objects[3] == null ? 0 : Integer.valueOf(objects[3].toString());
                cupName[i] = name;
                map.put("name", name);
                map.put("value", count);
                cupUseCount.add(map);
            }
            Map<String,Object> map2=new HashMap<>(2);
            map2.put("cupName", cupName);
            map2.put("cupAmount", cupUseCount);
            //水量使用情况
            consumeInfo.setCup(cupName[0]==null?null:map2);
            //消费金额
            Object[] objects = list.get(0);
            consumeInfo.setUsageCount(objects[0] == null ? null : Integer.valueOf(objects[0].toString()));
            consumeInfo.setDevId(objects[1] == null ? null : objects[1].toString());
            consumeInfo.setWaterTemp(objects[2] == null ? null : Integer.valueOf(objects[2].toString()));
            consumeInfo.setSellStatus(objects[3] == null ? null : Integer.valueOf(objects[3].toString()));
            consumeInfo.setWaterAmount(objects[4] == null ? null : ((Double) (Integer.valueOf(objects[4].toString()) / 1000.00)).toString());
            consumeInfo.setMoneyAmount(objects[5] == null ? null : ((Double) (Integer.valueOf(objects[5].toString()) / 100.00)).toString());
        } catch (Exception e) {
            e.printStackTrace();
            throw new XException("类型转化异常", 6001);
        }
        return consumeInfo;
    }
    /**
     * 查询指定时间的消费排行
     *
     * @param type      时间类型 0 自定义时间范围 1今天 2最近七天 3本月 4上个月
     * @param startTime 开始时间
     * @param endTime 截止时间
     * @return
     */
    @Override
    public ConsumeInfo getRankByTimes(Integer type, String startTime, String endTime,Integer userId) {
        ConsumeInfo consumeInfo = new ConsumeInfo();
        List<Object[]> rankList = new ArrayList<>();
        try {
            switch (type) {
                case 0:
                    rankList = consumeInfoRepository.findRankByTime(startTime, endTime,userId);
                    break;
                case 1:
                    rankList = consumeInfoRepository.findRankByDay(userId);
                    break;
                case 2:
                    rankList = consumeInfoRepository.findRankByWeek(userId);
                    break;
                case 3:
                    rankList = consumeInfoRepository.findRankByCurrentMonth(userId);
                    break;
                case 4:
                    rankList = consumeInfoRepository.findRankByLastMonth(userId);
                    break;
                default:
                    break;
            }
            if (CollectionUtils.isEmpty(rankList)) {
                return null;
            }
            //限制10条
            int size = rankList.size() > 10 ? 10 : rankList.size();
            //devNo
            String[] devNoArray = new String[size];
            //订单数
            Integer[] orderArray = new Integer[size];
            //售水量
            String[] waterArray = new String[size];
            //销售额
            String[] moneyArray = new String[size];
            for (int i = 0; i < size; i++) {
                Object[] objects = rankList.get(i);
                String name = objects[0] == null ? null : objects[0].toString();
                Integer order = objects[2] == null ? 0 : Integer.valueOf(objects[2].toString());
                Double aDouble = (Integer.valueOf(objects[3]==null?"0":(objects[3].toString())) / 1000.00);
                Double aDouble1 = (Integer.valueOf(objects[4]==null?"0":(objects[4].toString())) / 100.00);
                String water = objects[3] == null ? "0" : aDouble.toString();
                String money = objects[4] == null ? "0" : aDouble1.toString();
                devNoArray[i] = name;
                orderArray[i] = order;
                waterArray[i] = water;
                moneyArray[i] = money;
            }
            Map<String,Object> map2=new HashMap<>(4);
            map2.put("devNo", devNoArray);
            map2.put("order", orderArray);
            map2.put("water", waterArray);
            map2.put("money", moneyArray);
            //rank情况
            consumeInfo.setCup(map2);
        } catch (Exception e) {
            e.printStackTrace();
            throw new XException("类型转化异常", 6001);
        }
        return consumeInfo;
    }
}
