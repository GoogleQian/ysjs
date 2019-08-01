package com.he.water.entity.wxconfig.service;

import com.he.water.entity.wxconfig.entity.WxConfig;
import com.he.water.service.base.IBaseService;

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
     * 通过商户查询相关信息
     *
     * @param merchantId 商户id
     * @return 商户微信配置
     */
    WxConfig findByMerchantId(int merchantId);

    /**
     * 通过微信appId 查询
     *
     * @param appId 商户appId
     * @return 商户微信配置
     */
    List<WxConfig> findByAppId(String appId);
}


