package com.xiaohe.ysjspt.modules.wxconfig.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 *
 * </p>
 *
 * @author hzh
 * @since 2018-11-20
 */

@Entity
@Table(name = "tb_wx_config")
@DynamicInsert
@DynamicUpdate
public class WxConfig implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    /**
     * 商户id
     */
    @NotNull(message = "请选择商户")
    private Integer merchantId;
    /**
     * 公众账号ID
     */
    @NotNull(message = "公众账号ID不能为空")
    private String appId;
    /**
     * 公众账号身份密钥
     */
    @NotNull(message = "公众账号身份密钥不能为空")
    private String appSecret;
    /**
     * 开发者密码
     */
    @NotNull(message = "开发者密码不能为空")
    private String secret;
    /**
     * 微信商户ID
     */
    @NotNull(message = "微信商户ID不能为空")
    private String mchId;
    /**
     * 证书(路径)
     */
    @NotNull(message = "证书路径不能为空")
    private String cert;
    /**
     * 创建时间
     */
    @Temporal(value = TemporalType.TIMESTAMP)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date modifyTime;

    @Transient
    private String tradeType;

    public WxConfig() {
    }

    public WxConfig(String appId, String appSecret, String secret, String mchId, String cert, String tradeType) {
        this.appId = appId;
        this.appSecret = appSecret;
        this.secret = secret;
        this.mchId = mchId;
        this.cert = cert;
        this.tradeType = tradeType;
    }

    public String getTradeType() {
        return tradeType;
    }

    public void setTradeType(String tradeType) {
        this.tradeType = tradeType;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(Integer merchantId) {
        this.merchantId = merchantId;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getAppSecret() {
        return appSecret;
    }

    public void setAppSecret(String appSecret) {
        this.appSecret = appSecret;
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public String getMchId() {
        return mchId;
    }

    public void setMchId(String mchId) {
        this.mchId = mchId;
    }

    public String getCert() {
        return cert;
    }

    public void setCert(String cert) {
        this.cert = cert;
    }

    public Date getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }
}