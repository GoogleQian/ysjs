package com.xiaohe.ysjspt.modules.wxconfig.service;

import com.xiaohe.ysjspt.modules.wxconfig.entity.WxConfig;
import com.xiaohe.ysjspt.service.IBaseService;

import java.util.List;

/**
 * <p>
 * WxConfig接口
 * </p>
 *
 * @author hzh
 * @since 2018-11-20
 */

public interface WxConfigService extends IBaseService<WxConfig, Integer> {

    /**
     * 更新微信商户配置
     *
     * @param wxConfig
     * @return
     */
    int update(WxConfig wxConfig);

    /**
     * 通过商户id  获取微信配置
     *
     * @param merchantId
     * @return
     */
    WxConfig findByMerchantId(Integer merchantId);

    /**
     * 更新或保存微信配置
     *
     * @param userId
     * @param wxConfig
     */
    void saveOrUpdate(Integer userId, WxConfig wxConfig);

    /**
     * 删除微信配置
     *
     * @param ids
     */
    void deleteByMerchantId(List<Long> ids);
}


