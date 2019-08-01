package com.xiaohe.ysjspt.modules.wxconfig.jpa;

import com.xiaohe.ysjspt.modules.wxconfig.entity.WxConfig;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

/**
 * jpa 接口
 *
 * @author hzh
 * @since 2018-11-20
 */

@Transactional(rollbackFor = Exception.class)
public interface WxConfigRepository extends JpaRepository<WxConfig, Integer> {
    /**
     * 更新配置
     *
     * @param wxConfig
     * @return
     */
//    @Modifying
//    @Transactional(rollbackFor = Exception.class)
//    @Query("update WxConfig wc set " +
//            "wc.merchantId = CASE WHEN :#{#wxConfig.merchantId} IS NULL THEN wc.merchantId ELSE :#{#wxConfig.merchantId} END ," +
//            "wc.aapId = CASE WHEN :#{#wxConfig.aapId} IS NULL THEN wc.aapId ELSE :#{#wxConfig.aapId} END ," +
//            "wc.appSecret = CASE WHEN :#{#wxConfig.appSecret} IS NULL THEN wc.appSecret ELSE :#{#wxConfig.appSecret} END ," +
//            "wc.mchId = CASE WHEN :#{#wxConfig.mchId} IS NULL THEN wc.mchId ELSE :#{#wxConfig.mchId} END ," +
//            "wc.secret = CASE WHEN :#{#wxConfig.secret} IS NULL THEN wc.secret ELSE :#{#wxConfig.secret} END ," +
//            "wc.cert = CASE WHEN :#{#wxConfig.cert} IS NULL THEN wc.cert ELSE :#{#wxConfig.cert} END ," +
//            "wc.modifyTime =  CASE WHEN :#{#wxConfig.modifyTime} IS NULL THEN wc.modifyTime ELSE :#{#wxConfig.modifyTime} END " +
//            "where wc.id = :#{#wxConfig.id}")
//    int update(WxConfig wxConfig);


    /**
     * 通过商户id  获取微信配置
     *
     * @param merchantId 商户id
     * @return 商户微信配置
     */
    WxConfig findByMerchantId(Integer merchantId);

    /**
     * 通过商户id删除微信配置
     *
     * @param merchantId 商户id
     */
    void deleteByMerchantId(Integer merchantId);
}