package com.he.water.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.he.water.utils.DateUtil;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "tb_orders")
public class Orders {
    @Id
    @JsonProperty(value = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 订单号
     */
    @JsonProperty("order_no")
    private String orderNo;
    /**
     * 第三方系统交易号
     */
    @JsonProperty("trade_no")
    private String tradeNo;
    /**
     * 设备mac地址
     */
    @JsonProperty("dev_mac")
    private String devMac;
    /**
     * 设备devid
     */
    @JsonProperty("dev_id")
    private String devId;
    /**
     * 订单创建时间
     */
    @JsonProperty("create_time")
    @JsonFormat(pattern = DateUtil.DATE_TIME_FORMAT, timezone = "GMT+8")
    private Date createTime;
    /**
     * 通道
     */
    @JsonProperty("sell_type")
    private Integer sellType;
    /**
     * 水温 1：开水，2：热水，3,：常温水，4：冷水)
     */
    @JsonProperty("water_temp")
    private Integer waterTemp;
    /**
     * 水量
     */
    @JsonProperty("water_amount")
    private Integer waterAmount;
    /**
     * 微信openId
     */
    @JsonProperty("open_id")
    private String openId;
    /**
     * 微信支付回调时间
     */
    @JsonProperty("pay_notify_time")

    @JsonFormat(pattern = DateUtil.DATE_TIME_FORMAT, timezone = "GMT+8")
    private Date payNotifyTime;
    /**
     * '硬件出水完成，上报时间
     */
    @JsonProperty("hardware_notify_time")
    @JsonFormat(pattern = DateUtil.DATE_TIME_FORMAT, timezone = "GMT+8")
    private Date hardwareNotifyTime;
    /**
     * 支付状态 1. 待支付 2.已付款 3.已退款
     */
    @JsonProperty("pay_status")
    private int payStatus;
    /**
     * 0.订单已取消 2.已下发出水指令，等待应答 (待出水) 1.已应答，售卖成功 （已出水）3.售水失败
     */
    @JsonProperty("sell_status")
    private int sellStatus;
    /**
     * 优惠券使用id
     */
    @JsonProperty("coupon_usage_id")
    private Long couponUsageId;
    /**
     *
     */
    @JsonProperty("refund_id")
    private String refundId;
    @JsonProperty("refund_no")
    private String refundNo;
    /**
     * 支付金额 单位分
     */
    @JsonProperty("money_amount")
    private int moneyAmount;
    /**
     * 优惠金额
     */
    @JsonProperty("coupon_price")
    private int couponPrice;
    /**
     * 脉冲通道
     */
    private int passageNo;
    /**
     * 脉冲数
     */
    private int pulse;

    public int getPulse() {
        return pulse;
    }

    public int getCouponPrice() {
        return couponPrice;
    }

    public void setCouponPrice(int couponPrice) {
        this.couponPrice = couponPrice;
    }

    public void setPulse(int pulse) {
        this.pulse = pulse;
    }

    public int getPassageNo() {
        return passageNo;
    }

    public void setPassageNo(int passageNo) {
        this.passageNo = passageNo;
    }

    public Integer getSellType() {
        return sellType;
    }

    public void setSellType(Integer sellType) {
        this.sellType = sellType;
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

    public int getPayStatus() {
        return payStatus;
    }

    public void setPayStatus(int payStatus) {
        this.payStatus = payStatus;
    }

    public int getSellStatus() {
        return sellStatus;
    }

    public void setSellStatus(int sellStatus) {
        this.sellStatus = sellStatus;
    }

    public String getRefundId() {
        return refundId;
    }

    public void setRefundId(String refundId) {
        this.refundId = refundId;
    }

    public String getRefundNo() {
        return refundNo;
    }

    public void setRefundNo(String refundNo) {
        this.refundNo = refundNo;
    }

    public int getMoneyAmount() {
        return moneyAmount;
    }

    public void setMoneyAmount(int moneyAmount) {
        this.moneyAmount = moneyAmount;
    }

    public String getDevId() {
        return devId;
    }

    public void setDevId(String devId) {
        this.devId = devId;
    }

    public Long getCouponUsageId() {
        return couponUsageId;
    }

    public void setCouponUsageId(Long couponUsageId) {
        this.couponUsageId = couponUsageId;
    }
}
