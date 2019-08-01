package com.xiaohe.ysjspt.modules.wxconfig.service.impl;

import com.xiaohe.ysjspt.modules.wxconfig.entity.WxConfig;
import com.xiaohe.ysjspt.modules.wxconfig.jpa.WxConfigRepository;
import com.xiaohe.ysjspt.modules.wxconfig.service.WxConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

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
    public WxConfig findByDevId(Long devId) {
        return null;
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

    /**
     * 分页查询
     * id处字符串为需要排序的字段，可以传多个，比如 "id","createTime",...
     *
     * @param page     页面
     * @param pageSize 页面大小
     * @return Page<WxConfig>对象
     */
    @Override
    public Page<WxConfig> findAll(int page, int pageSize) {
        Pageable pageable = new PageRequest(page, pageSize, Sort.Direction.DESC, "id");
        return wxConfigRepository.findAll(pageable);
    }

    @Override
    public int update(WxConfig wxConfig) {
        return 1;
//        return wxConfigRepository.update(wxConfig);
    }

    @Override
    public WxConfig findByMerchantId(Integer merchantId) {
        return wxConfigRepository.findByMerchantId(merchantId);
    }

    @Override
    public void saveOrUpdate(Integer userId, WxConfig wxConfig) {
        wxConfig.setMerchantId(userId);
        save(wxConfig);
    }

    @Override
    public void deleteByMerchantId(List<Long> ids) {
        if (CollectionUtils.isEmpty(ids)) {
            return;
        }
        for (Long id : ids) {
            if (id != null) {
                wxConfigRepository.deleteByMerchantId(id.intValue());
            }
        }
    }
}


