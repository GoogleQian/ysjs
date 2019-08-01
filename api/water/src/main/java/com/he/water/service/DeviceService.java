package com.he.water.service;


import com.he.water.entity.DeviceEntity;
import com.he.water.jpa.DeviceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * 设备service服务类
 *
 * @version <pre>
 * @Author Version        Date		Changes
 * admin    1.0  2018年05月03日 Created
 *
 * </pre>
 * @since 1.
 */
@Service("deviceService")
@Transactional(rollbackFor = Exception.class)
public class DeviceService {

    @Autowired
    private DeviceRepository deviceRepository;


    public DeviceEntity findByImei(String imei) {
        return deviceRepository.findByImei(imei);
    }


    public DeviceEntity findByDeviceId(String deviceId) {
        return deviceRepository.findByDeviceId(deviceId);
    }
}
