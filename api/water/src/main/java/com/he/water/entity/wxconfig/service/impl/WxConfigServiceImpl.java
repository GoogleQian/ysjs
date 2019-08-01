package com.he.water.entity.wxconfig.service.impl;

import com.he.water.entity.wxconfig.entity.WxConfig;
import com.he.water.entity.wxconfig.jpa.WxConfigRepository;
import com.he.water.entity.wxconfig.service.WxConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * WxConfig服务类
 * </p>
 *
 * @author hzh
 * @since 2018-11-20
 */

@Service
public class WxConfigServiceImpl implements WxConfigService {

    @Autowired
    private WxConfigRepository wxConfigRepository;

    @Override
    public WxConfig save(WxConfig wxConfig) {
        return wxConfigRepository.save(wxConfig);
    }


    @Override
    public void delete(Integer id) {
        wxConfigRepository.delete(id);
    }

    @Override
    public boolean exists(Integer id) {
        return wxConfigRepository.exists(id);
    }

    @Override
    public long count() {
        return 0;
    }

    /**
     * 通过id查询
     *
     * @param id id
     * @return WxConfig对象
     */
    @Override
    public WxConfig findById(Integer id) {
        return wxConfigRepository.findOne(id);
    }


    @Override
    public WxConfig findByMerchantId(int merchantId) {
        return wxConfigRepository.findByMerchantId(merchantId);
    }

    @Override
    public List<WxConfig> findByAppId(String appId) {
        return wxConfigRepository.findByAppId(appId);
    }

    @Override
    public Page<WxConfig> findAll(int page, int pageSize) {
        return null;
    }
}


