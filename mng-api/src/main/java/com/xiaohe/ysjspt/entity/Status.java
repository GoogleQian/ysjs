package com.xiaohe.ysjspt.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.xiaohe.ysjspt.utils.DateUtil;

/**
 * <p>
 * 设备状态表
 * </p>
 *
 * @author gmq
 * @since 2018-11-26
 */

@Entity
@Table(name="tb_status")
public class Status implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 水量
     */
    @Id
    @JsonProperty(value = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @JsonProperty(value = "imei")
    private String imei;
    /**
     * 机器上报状态水量(ml)
     */
    @JsonProperty(value = "amount_status")
    private Double amountStatus;
    /**
     * 换水状态：0x00 ，0x01 (正在换水)


     */
    @JsonProperty(value = "change_water_status")
    private Integer changeWaterStatus;
    /**
     * 冲洗状态：0x00 ，0x01(正在冲洗)


     */
    @JsonProperty(value = "rinse_status")
    private Integer rinseStatus;
    /**
     * 消毒状态：0x00 ，0x01 (正在消毒)


     */
    @JsonProperty(value = "disinfection_status")
    private Integer disinfectionStatus;
    /**
     * 童锁状态:0x00,0x01(童锁开)

     */
    @JsonProperty(value = "child_lock_status")
    private Integer childLockStatus;
    /**
     * 水温
     */
    @JsonProperty(value = "temp")
    private Integer temp;
    /**
     * 水位(0:没水位，1:低水位，2:中水位，3:高水位)
     */
    @JsonProperty(value = "water_level_status")
    private Integer waterLevelStatus;
    /**
     * 进水(1:进水，0:关进水)
     */
    @JsonProperty(value = "in_water_status")
    private Integer inWaterStatus;
    /**
     * 出水1(通道1):0x00(关),0x01(开)
     */
    @JsonProperty(value = "pass_one")
    private Integer passOne;
    /**
     * 出水2(通道2):0x00(关),0x01(开)
     */
    @JsonProperty(value = "pass_two")
    private Integer passTwo;
    /**
     * 出水3(通道3):0x00(关),0x01(开)
     */
    @JsonProperty(value = "pass_third")
    private Integer passThird;
    /**
     * 出水4(通道4):0x00(关),0x01(开)
     */
    @JsonProperty(value = "pass_four")
    private Integer passFour;
    /**
     * 加热状态(1:加热，0:停止加热)
     */
    @JsonProperty(value = "heating_status")
    private Integer heatingStatus;
    /**
     * 制冷状态(1:制冷，0:停止制冷)
     */
    @JsonProperty(value = "refrigeration_status")
    private Integer refrigerationStatus;
    /**
     * 入水tds
     */
    @JsonProperty(value = "in_tds")
    private Integer inTds;
    /**
     * 出水tds
     */
    @JsonProperty(value = "out_tds")
    private Integer outTds;
    /**
     * 故障代码:
     224（滤芯防伪）
     225（超温故障）
     226（顶部故障）
     227（底部故障）
     229（进水故障）
     230（溢出故障）
     231（结垢故障）

     */
    @JsonProperty(value = "error_code")
    private Integer erroCode;
    /**
     * 信号强度
     (0:无,1:低信号强度,2:中信号强度,3:高信号强度）

     */
    @JsonProperty(value = "model_strength")
    private Integer modelStrength;
    /**
     * 时控
     (0设置,1取消)
     */
    @JsonProperty(value = "time_control")
    private Integer timeControl;
    @JsonProperty(value = "create_time")
    @JsonFormat(pattern = DateUtil.DATE_TIME_FORMAT, timezone = "GMT+8")
    private Date createTime;
    @Transient
    @JsonProperty(value = "start_time")
    private String startTime;
    @Transient
    @JsonProperty(value = "end_time")
    private String endTime;

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getImei() {
        return imei;
    }

    public void setImei(String imei) {
        this.imei = imei;
    }

    public Double getAmountStatus() {
        return amountStatus;
    }

    public void setAmountStatus(Double amountStatus) {
        this.amountStatus = amountStatus;
    }

    public Integer getChangeWaterStatus() {
        return changeWaterStatus;
    }

    public void setChangeWaterStatus(Integer changeWaterStatus) {
        this.changeWaterStatus = changeWaterStatus;
    }

    public Integer getRinseStatus() {
        return rinseStatus;
    }

    public void setRinseStatus(Integer rinseStatus) {
        this.rinseStatus = rinseStatus;
    }

    public Integer getDisinfectionStatus() {
        return disinfectionStatus;
    }

    public void setDisinfectionStatus(Integer disinfectionStatus) {
        this.disinfectionStatus = disinfectionStatus;
    }

    public Integer getChildLockStatus() {
        return childLockStatus;
    }

    public void setChildLockStatus(Integer childLockStatus) {
        this.childLockStatus = childLockStatus;
    }

    public Integer getTemp() {
        return temp;
    }

    public void setTemp(Integer temp) {
        this.temp = temp;
    }

    public Integer getWaterLevelStatus() {
        return waterLevelStatus;
    }

    public void setWaterLevelStatus(Integer waterLevelStatus) {
        this.waterLevelStatus = waterLevelStatus;
    }

    public Integer getInWaterStatus() {
        return inWaterStatus;
    }

    public void setInWaterStatus(Integer inWaterStatus) {
        this.inWaterStatus = inWaterStatus;
    }

    public Integer getPassOne() {
        return passOne;
    }

    public void setPassOne(Integer passOne) {
        this.passOne = passOne;
    }

    public Integer getPassTwo() {
        return passTwo;
    }

    public void setPassTwo(Integer passTwo) {
        this.passTwo = passTwo;
    }

    public Integer getPassThird() {
        return passThird;
    }

    public void setPassThird(Integer passThird) {
        this.passThird = passThird;
    }

    public Integer getPassFour() {
        return passFour;
    }

    public void setPassFour(Integer passFour) {
        this.passFour = passFour;
    }

    public Integer getHeatingStatus() {
        return heatingStatus;
    }

    public void setHeatingStatus(Integer heatingStatus) {
        this.heatingStatus = heatingStatus;
    }

    public Integer getRefrigerationStatus() {
        return refrigerationStatus;
    }

    public void setRefrigerationStatus(Integer refrigerationStatus) {
        this.refrigerationStatus = refrigerationStatus;
    }

    public Integer getInTds() {
        return inTds;
    }

    public void setInTds(Integer inTds) {
        this.inTds = inTds;
    }

    public Integer getOutTds() {
        return outTds;
    }

    public void setOutTds(Integer outTds) {
        this.outTds = outTds;
    }

    public Integer getErroCode() {
        return erroCode;
    }

    public void setErroCode(Integer erroCode) {
        this.erroCode = erroCode;
    }

    public Integer getModelStrength() {
        return modelStrength;
    }

    public void setModelStrength(Integer modelStrength) {
        this.modelStrength = modelStrength;
    }

    public Integer getTimeControl() {
        return timeControl;
    }

    public void setTimeControl(Integer timeControl) {
        this.timeControl = timeControl;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }


}