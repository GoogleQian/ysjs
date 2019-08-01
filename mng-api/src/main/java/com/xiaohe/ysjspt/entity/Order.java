package com.xiaohe.ysjspt.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.xiaohe.ysjspt.utils.DateUtil;

/**
 * <p>
 *
 * </p>
 *
 * @author wzq
 * @since 2018-08-27
 */

@Entity
@Table(name = "tb_orders")
public class Order implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @JsonProperty(value = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    /**
     * 订单号
     */
    @JsonProperty(value = "order_no")
    private String orderNo;
    /**
     * 第三方系统交易号
     */
    @JsonProperty(value = "trade_no")
    private String tradeNo;
    /**
     * 设备mac地址
     */
    @JsonProperty(value = "dev_mac")
    private String devMac;
    @JsonProperty(value = "dev_id")
    private String devId;
    /**
     * 订单创建时间
     */
    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(pattern = DateUtil.DATE_TIME_FORMAT, timezone = "GMT+8")
    @JsonProperty(value = "create_time")
    private Date createTime;
    /**
     * 水温
     */
    @JsonProperty(value = "water_temp")
    private Integer waterTemp;
    /**
     * 水量
     */
    @JsonProperty(value = "water_amount")
    private Integer waterAmount;
    /**
     * 微信openId
     */
    @JsonProperty(value = "open_id")
    private String openId;
    /**
     * 微信支付回调时间
     */
    @JsonProperty(value = "pay_notify_time")
    private Date payNotifyTime;
    /**
     * 硬件出水完成，上报时间
     */
    @JsonProperty(value = "hardware_notify_time")
    private Date hardwareNotifyTime;
    /**
     * 支付状态 1. 待支付 2.已付款 3.已退款
     */
    @JsonProperty(value = "pay_status")
    private Integer payStatus;
    /**
     * 0.订单已取消 2.已下发出水指令，等待应答 (待出水) 1.已应答，售卖成功 （已出水）3.售水失败
     */
    @JsonProperty(value = "sell_status")
    private Integer sellStatus;
    /**
     * 商户退款单号
     */
    @JsonProperty(value = "refund_no")
    private String refundNo;
    @JsonProperty(value = "money_amount")
    private Integer moneyAmount;
    /**
     * 微信退款单号
     */
    @JsonProperty(value = "refund_id")
    private String refundId;

    /**
     * 优惠券使用id
     */
    @JsonProperty("coupon_usage_id")
    private Integer couponUsageId;
    /**
     * 脉冲通道
     */
    private int passageNo;

    /**
     * 脉冲
     */
    private int pulse;

    public int getPulse() {
        return pulse;
    }

    public void setPulse(int pulse) {
        this.pulse = pulse;
    }

    public Integer getCouponUsageId() {
        return couponUsageId;
    }

    public void setCouponUsageId(Integer couponUsageId) {
        this.couponUsageId = couponUsageId;
    }

    public int getPassageNo() {
        return passageNo;
    }

    public void setPassageNo(int passageNo) {
        this.passageNo = passageNo;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getTradeNo() {
        return tradeNo;
    }

    public void setTradeNo(String tradeNo) {
        this.tradeNo = tradeNo;
    }

    public String getDevMac() {
        return devMac;
    }

    public void setDevMac(String devMac) {
        this.devMac = devMac;
    }

    public String getDevId() {
        return devId;
    }

    public void setDevId(String devId) {
        this.devId = devId;
    }


    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getWaterTemp() {
        return waterTemp;
    }

    public void setWaterTemp(Integer waterTemp) {
        this.waterTemp = waterTemp;
    }

    public Integer getWaterAmount() {
        return waterAmount;
    }

    public void setWaterAmount(Integer waterAmount) {
        this.waterAmount = waterAmount;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public Date getPayNotifyTime() {
        return payNotifyTime;
    }

    public void setPayNotifyTime(Date payNotifyTime) {
        this.payNotifyTime = payNotifyTime;
    }

    public Date getHardwareNotifyTime() {
        return hardwareNotifyTime;
    }

    public void setHardwareNotifyTime(Date hardwareNotifyTime) {
        this.hardwareNotifyTime = hardwareNotifyTime;
    }

    public Integer getPayStatus() {
        return payStatus;
    }

    public void setPayStatus(Integer payStatus) {
        this.payStatus = payStatus;
    }

    public Integer getSellStatus() {
        return sellStatus;
    }

    public void setSellStatus(Integer sellStatus) {
        this.sellStatus = sellStatus;
    }

    public String getRefundNo() {
        return refundNo;
    }

    public void setRefundNo(String refundNo) {
        this.refundNo = refundNo;
    }

    public Integer getMoneyAmount() {
        return moneyAmount;
    }

    public void setMoneyAmount(Integer moneyAmount) {
        this.moneyAmount = moneyAmount;
    }

    public String getRefundId() {
        return refundId;
    }

    public void setRefundId(String refundId) {
        this.refundId = refundId;
    }


}