package com.he.water.service;


import com.alibaba.fastjson.JSONObject;
import com.he.water.entity.Orders;
import com.xiaohe.entity.DevSysConfig;
import com.xiaohe.entity.DevUserConfig;
import com.xiaohe.hservice.NotifyService;
import com.xiaohe.hservice.WaterBaseData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * Created by Administrator on 2018/1/20.
 */
@Service
public class RemoteCtlNotifyService implements NotifyService {
    public static Logger log = LoggerFactory.getLogger(RemoteCtlNotifyService.class);

    @Autowired
    private OrdersService ordersService;

    @Override
    public void notifyTurnOnStatus(int controlId, int status) {
        log.info("硬件返回状态" + controlId + "######" + status);
        // 修改订单状态
        Orders orders = ordersService.findById(controlId);
        if (orders != null) {
            orders.setSellStatus(status);
            orders.setHardwareNotifyTime(new Date());
            ordersService.save(orders);
        }
    }

    @Override
    public void notifySetSwitchTime(String s, int i) {

    }

    @Override
    public void notifySetModel(int i) {

    }

    @Override
    public void notifyRestoreDefaults(int i) {

    }

    @Override
    public void notifyTimedStartAndCancel(int i) {

    }

    @Override
    public void notifyChangeWater(int i) {

    }

    @Override
    public void notifyDisinfection(int i) {

    }

    @Override
    public void notifyFlushing(int i) {

    }

    @Override
    public void notifyResetWaterVolume(int i) {

    }

    @Override
    public void notifySetSysEnergy(int i) {

    }

    @Override
    public void notifySetSysStep(int i) {

    }

    @Override
    public void notifySetUserSetting(int i) {

    }

    @Override
    public int notifySetSysConfig(DevSysConfig sysConfig) {
        return 0;
    }

    @Override
    public int notifySetUserConfig(DevUserConfig userConfig) {

        System.out.println("@@@@@@@@111" + JSONObject.toJSONString(userConfig));
        return 0;
    }

    @Override
    public int notifyGetUserSetting(DevUserConfig userConfig) {
        System.out.println("###############111" + JSONObject.toJSONString(userConfig));
        return 0;
    }


    public int notifySetUserCnfig(DevUserConfig userConfig) {
        return 0;
    }

    @Override
    public int notifyGetShowTemp(DevUserConfig userConfig) {
        return 0;
    }

    @Override
    public int notifyGetWaterAmount(WaterBaseData waterBaseData) {
        return 0;
    }

    @Override
    public int notifySetPassageInfo(int i) {
        return 0;
    }

    public OrdersService getOrdersService() {
        return ordersService;
    }

    public void setOrdersService(OrdersService ordersService) {
        this.ordersService = ordersService;
    }

    @Override
    public int notifyCtlMichineStatus(int i) {
        return 0;
    }
}
