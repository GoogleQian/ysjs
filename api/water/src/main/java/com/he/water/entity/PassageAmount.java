package com.he.water.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.he.water.entity.wxconfig.entity.WxConfig;

import javax.persistence.Transient;
import java.io.Serializable;

/**
 * @author hzh
 * @date 2018/11/12
 */
public class PassageAmount implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 售水量
     */
    private int amount;
    /**
     * 售水量对应价格，单位分
     */
    private int price;

    /**
     * 显示名称
     */
    private String name;
    /**
     * 售水类型
     */
    private Integer passageType;
    /**
     * 售水通道
     */
    private Integer passageNo;
    /**
     *
     */
    private String openId;

    private String devId;
    /**
     * 优惠券id
     */
    private Long couponUsageId;
    /**
     * 优惠金额
     */
    private int couponPrice;

    @Transient
    @JsonProperty(value = "mchInfo")
    private WxConfig mchInfo;

    public PassageAmount() {

    }

    public PassageAmount(int amount, int price, String name, Integer passageType, Integer passageNo) {
        this.amount = amount;
        this.price = price;
        this.name = name;
        this.passageType = passageType;
        this.passageNo = passageNo;
    }

    public WxConfig getMchInfo() {
        return mchInfo;
    }

    public void setMchInfo(WxConfig mchInfo) {
        this.mchInfo = mchInfo;
    }

    public int getCouponPrice() {
        return couponPrice;
    }

    public void setCouponPrice(int couponPrice) {
        this.couponPrice = couponPrice;
    }

    public Integer getPassageType() {
        return passageType;
    }

    public void setPassageType(Integer passageType) {
        this.passageType = passageType;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public String getDevId() {
        return devId;
    }

    public void setDevId(String devId) {
        this.devId = devId;
    }

    public Integer getPassageNo() {
        return passageNo;
    }

    public void setPassageNo(Integer passageNo) {
        this.passageNo = passageNo;
    }

    public Long getCouponUsageId() {
        return couponUsageId;
    }

    public void setCouponUsageId(Long couponUsageId) {
        this.couponUsageId = couponUsageId;
    }
}
