package com.he.water.entity.wxconfig.jpa;

import com.he.water.entity.wxconfig.entity.WxConfig;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * jpa 接口
 *
 * @author hzh
 * @since 2018-11-20
 */

@Transactional(rollbackFor = Exception.class)
public interface WxConfigRepository extends JpaRepository<WxConfig, Integer> {

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