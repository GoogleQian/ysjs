package com.xiaohe.ysjspt.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.xiaohe.ysjspt.utils.DateUtil;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;


/**
 * 
 * 历史记录实体
 * 
 * @version 
 * <pre>
 * @Author	Version		Date		Changes
 * admin 	1.0  2018年05月03日 Created
 *
 * </pre>
 * @since 1.
 */
@Entity
@Table(name="tb_history")
public class HistoryEntity implements Serializable {

    private static final long serialVersionUID = 327233276140853L;

    /***/
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JsonProperty(value="history_id")
    private Long historyId;
    /**设备ID*/
    @JsonProperty("device_id")
    private String deviceId;
    /***/
    @JsonProperty("device_name")
    private String deviceName;
    /**历史记录时间*/
    @JsonProperty("record_time")
    @JsonFormat(pattern = DateUtil.DATE_TIME_FORMAT, timezone = "GMT+8")
    private Date recordTime;
    /**净化前tds 值*/
    @JsonProperty("tds")
    private Integer tds;

    /**净化前色度*/
    @JsonProperty("color")
    private Double color;
    /**净化前温度*/
    @JsonProperty("trt")
    private Double trt;
    /**净化前水浊度*/
    @JsonProperty("tbdt")
    private Double tbdt;
    /**净化后的tds*/
    @JsonProperty("purify_tds")
    private Integer purifyTds;

    /**净化后的色度*/
    @JsonProperty("purify_color")
    private Double purifyColor;
    /**净化后的温度*/
    @JsonProperty("purify_trt")
    private Double purifyTrt;
    /**净化后的水浊度*/
    @JsonProperty("purify_tbdt")
    private Double purifyTbdt;
    /**水量*/
    @JsonProperty("amount")
    private Double amount;
    /**区域*/
    @JsonProperty("region_id")
    private Integer regionId;
    /**绑定的经销商ID*/
    @JsonProperty("binded_user_id")
    private Integer bindedUserId;
    /**下级代理商ID*/
    @JsonProperty("binded_sub_user_id")
    private Integer bindedSubUserId;


    public Long getHistoryId() {
        return historyId;
    }

    public void setHistoryId(Long historyId) {
        this.historyId = historyId;
    }

    public String getDeviceId() {
        return deviceId;
    }
    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getDeviceName() {
        return deviceName;
    }
    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public Date getRecordTime() {
        return recordTime;
    }
    public void setRecordTime(Date recordTime) {
        this.recordTime = recordTime;
    }

    public Integer getTds() {
        return tds;
    }
    public void setTds(Integer tds) {
        this.tds = tds;
    }

   public Double getColor() {
        return color;
    }

    public void setColor(Double color) {
        this.color = color;
    }

    public Double getTrt() {
        return trt;
    }

    public void setTrt(Double trt) {
        this.trt = trt;
    }

    public Double getTbdt() {
        return tbdt;
    }

    public void setTbdt(Double tbdt) {
        this.tbdt = tbdt;
    }

    public Integer getPurifyTds() {
        return purifyTds;
    }

    public void setPurifyTds(Integer purifyTds) {
        this.purifyTds = purifyTds;
    }


    public Double getPurifyColor() {
        return purifyColor;
    }

    public void setPurifyColor(Double purifyColor) {
        this.purifyColor = purifyColor;
    }

    public Double getPurifyTrt() {
        return purifyTrt;
    }

    public void setPurifyTrt(Double purifyTrt) {
        this.purifyTrt = purifyTrt;
    }

    public Double getPurifyTbdt() {
        return purifyTbdt;
    }

    public void setPurifyTbdt(Double purifyTbdt) {
        this.purifyTbdt = purifyTbdt;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Integer getRegionId() {
        return regionId;
    }

    public void setRegionId(Integer regionId) {
        this.regionId = regionId;
    }

    public Integer getBindedUserId() {
        return bindedUserId;
    }

    public void setBindedUserId(Integer bindedUserId) {
        this.bindedUserId = bindedUserId;
    }

    public Integer getBindedSubUserId() {
        return bindedSubUserId;
    }

    public void setBindedSubUserId(Integer bindedSubUserId) {
        this.bindedSubUserId = bindedSubUserId;
    }

    }