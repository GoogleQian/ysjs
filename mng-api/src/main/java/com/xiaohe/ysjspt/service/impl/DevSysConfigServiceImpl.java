package com.xiaohe.ysjspt.service.impl;

import com.xiaohe.entity.DevSysConfig;
import com.xiaohe.ysjspt.entity.DeviceEntity;
import com.xiaohe.ysjspt.entity.config.SysConfigs;
import com.xiaohe.ysjspt.jpa.DevSysConfigRepository;
import com.xiaohe.ysjspt.service.DevSysConfigService;
import com.xiaohe.ysjspt.service.DeviceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * <p>
 * SysConfig服务类
 * </p>
 *
 * @author hzh
 * @since 2018-09-26
 */

@Service
public class DevSysConfigServiceImpl implements DevSysConfigService {
    private static final Logger log = LoggerFactory.getLogger(DevSysConfigServiceImpl.class);
    @Autowired
    private DevSysConfigRepository devSysConfigRepository;
    @Autowired
    private DeviceService deviceService;

    @Override
    public SysConfigs save(SysConfigs sysConfig) {
        return devSysConfigRepository.save(sysConfig);
    }

    @Override
    public void delete(Integer id) {
        devSysConfigRepository.delete(id);
    }

    @Override
    public boolean exists(Integer id) {
        return devSysConfigRepository.exists(id);
    }

    @Override
    public long count() {
        return devSysConfigRepository.count();
    }

    @Override
    public SysConfigs findById(Integer id) {
        return devSysConfigRepository.findOne(id);
    }

    @Override
    public Page<SysConfigs> findAll(int page, int pageSize) {
        Pageable pageable = new PageRequest(page, pageSize);

        return devSysConfigRepository.findAll(pageable);
    }

    @Override
    public SysConfigs findByDevId(Long devId) {
        return devSysConfigRepository.findByDevId(devId);
    }


    @Override
    public SysConfigs findByDevIdAndState(Long devId, Integer state) {
        return devSysConfigRepository.findByDevIdAndState(devId, state);
    }

    @Override
    public boolean existByDevIdAndState(Long devId, Integer state) {
        SysConfigs sysConfigs = findByDevIdAndState(devId, state);
        if (sysConfigs == null) {
            return true;
        }
        return false;
    }

    @Override
    public int saveBySysConfig(DevSysConfig sysConfig) {
        DeviceEntity byImei = deviceService.findByImei(sysConfig.getImei());
        if (byImei == null) {
            return 1;
        }
        //通过devId和机型查询是否存在数据
        SysConfigs byDevId = findByDevIdAndState(byImei.getId(), sysConfig.getState());
        if (byDevId != null) {
            sysConfig.setId(byDevId.getId());
        }
        sysConfig.setDevId(byImei.getId());
        sysConfig.setReportTime(new Date());
        SysConfigs sysConfigs = new SysConfigs();
        BeanUtils.copyProperties(sysConfig, sysConfigs);
        save(sysConfigs);
        deviceService.updateModel(sysConfigs.getDevId(), sysConfigs.getState());
        return 0;
    }


}


