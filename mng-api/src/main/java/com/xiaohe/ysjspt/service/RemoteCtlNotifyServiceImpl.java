package com.xiaohe.ysjspt.service;


import com.alibaba.fastjson.JSONObject;
import com.xiaohe.entity.DevSysConfig;
import com.xiaohe.entity.DevUserConfig;
import com.xiaohe.hservice.NotifyService;
import com.xiaohe.hservice.WaterBaseData;
import com.xiaohe.ysjspt.entity.Order;
import com.xiaohe.ysjspt.service.impl.DevUserConfigServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

/**
 * @author wzq
 */
public class RemoteCtlNotifyServiceImpl implements NotifyService {

    private static final Logger log = LoggerFactory.getLogger(RemoteCtlNotifyServiceImpl.class);
    @Autowired
    private DevSysConfigService devSysConfigService;
    @Autowired
    private UserConfigService userConfigService;
    @Autowired
    private DevUserConfigServiceImpl devUserConfigServiceImpl;
    @Autowired
    private OrdersService ordersService;

    @Override
    public void notifyTurnOnStatus(int controlId, int status) {
        log.info("硬件返回状态" + controlId + "######" + status);
        // 修改订单状态
        Order orders = ordersService.findById(controlId);
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
    public void notifySetModel(int status) {
        System.out.println("notifySetModel_in");
    }

    @Override
    public void notifyRestoreDefaults(int status) {

    }

    @Override
    public void notifyTimedStartAndCancel(int status) {

    }

    @Override
    public void notifyChangeWater(int status) {

    }

    @Override
    public void notifyDisinfection(int status) {

    }

    @Override
    public void notifyFlushing(int status) {

    }

    @Override
    public void notifyResetWaterVolume(int status) {

    }

    @Override
    public void notifySetSysEnergy(int status) {

    }

    @Override
    public void notifySetSysStep(int status) {

    }

    @Override
    public void notifySetUserSetting(int status) {

    }

    @Override
    public int notifySetSysConfig(DevSysConfig devSysConfig) {
        System.out.println("@@@@@@@@@@" + JSONObject.toJSONString(devSysConfig));
        try {
            return devSysConfigService.saveBySysConfig(devSysConfig);
        } catch (Exception e) {
            e.printStackTrace();
            return 1;
        }
    }

    @Override
    public int notifySetUserConfig(DevUserConfig devUserConfig) {
        System.out.println("###############" + JSONObject.toJSONString(devUserConfig));
        try {
            return userConfigService.saveByUserConfig(devUserConfig);
        } catch (Exception e) {
            e.printStackTrace();
            return 1;
        }
    }

    @Override
    public int notifyGetUserSetting(DevUserConfig devUserConfig) {
        devUserConfigServiceImpl.updateShowTemp(devUserConfig);
        return 0;
    }

    @Override
    public int notifyGetShowTemp(DevUserConfig devUserConfig) {
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

    @Override
    public int notifyCtlMichineStatus(int result) {
        return 0;
    }
}
