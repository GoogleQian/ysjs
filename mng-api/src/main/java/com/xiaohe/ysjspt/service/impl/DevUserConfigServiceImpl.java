package com.xiaohe.ysjspt.service.impl;

import com.xiaohe.entity.DevUserConfig;
import com.xiaohe.ysjspt.entity.DeviceEntity;
import com.xiaohe.ysjspt.entity.config.UserConfigs;
import com.xiaohe.ysjspt.jpa.UserConfigRepository;
import com.xiaohe.ysjspt.service.DeviceService;
import com.xiaohe.ysjspt.service.UserConfigService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * <p>
 * UserConfig服务类
 * </p>
 *
 * @author hzh
 * @since 2018-09-26
 */

@Service
public class DevUserConfigServiceImpl implements UserConfigService {

    @Autowired
    private UserConfigRepository userConfigRepository;
    @Autowired
    private DeviceService deviceService;

    @Override
    public UserConfigs save(UserConfigs userConfig) {
        return userConfigRepository.save(userConfig);
    }

    @Override
    public void delete(Integer id) {
        userConfigRepository.delete(id);
    }

    @Override
    public boolean exists(Integer id) {
        return userConfigRepository.exists(id);
    }

    @Override
    public long count() {
        return userConfigRepository.count();
    }

    @Override
    public UserConfigs findById(Integer id) {
        return userConfigRepository.findOne(id);
    }

    @Override
    public Page<UserConfigs> findAll(int page, int pageSize) {
        return null;
    }

    @Override
    public UserConfigs findByDevId(Long devId) {
        return userConfigRepository.findByDevId(devId);
    }

    @Override
    public UserConfigs findByDevIdAndState(Long devId, Integer state) {
        return userConfigRepository.findByDevIdAndState(devId, state);
    }



    public int updateShowTemp(DevUserConfig devUserConfig){
        DeviceEntity byImei = deviceService.findByImei(devUserConfig.getImei());
        return userConfigRepository.updateShowTemp(byImei.getId(),devUserConfig.getState(),devUserConfig.getShowTemp());
    }

    @Override
    public int saveByUserConfig(DevUserConfig devUserConfig) {
        //获取devid
        DeviceEntity byImei = deviceService.findByImei(devUserConfig.getImei());

        if (byImei == null) {
            return 1;
        }
        //通过devId和机型查询是否存在数据
        UserConfigs byDevId = findByDevIdAndState(byImei.getId(), devUserConfig.getState());
        //存在则设置id,执行更新，否则新增
        if (byDevId != null) {
            devUserConfig.setId(byDevId.getId());
        }
        devUserConfig.setDevId(byImei.getId());
        devUserConfig.setReportTime(new Date());
        UserConfigs userConfigs = new UserConfigs();
        BeanUtils.copyProperties(devUserConfig, userConfigs);
        save(userConfigs);
        return 0;
    }
}


