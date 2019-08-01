package com.xiaohe.ysjspt.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.xiaohe.ysjspt.utils.DateUtil;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.validation.annotation.Validated;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;


/**
 * 设备实体
 *
 * @version <pre>
 * @Author Version        Date		Changes
 * admin 	1.0  2018年05月03日 Created
 *
 * </pre>
 * @since 1.
 */
@Entity
@Table(name = "tb_device")
@DynamicInsert
@DynamicUpdate
public class DeviceEntity implements Serializable {

    private static final long serialVersionUID = 2446493944464368L;

    /***/
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JsonProperty(value = "id")
    private Long id;
    /**
     * 0:使用中，1：未使用
     */
    @JsonProperty("status")
    private Integer status;
    /**
     * 该设备对应的代理商（用户）ID,默认值为1是小荷用户
     */
    @JsonProperty("binded_user_id")
    private Integer bindedUserId;
    /**
     * 二级代理商Id
     */
    @JsonProperty("binded_sub_user_id")
    private Integer bindedSubUserId;
    /**
     * 经销商ID
     */
    @JsonProperty("user_id")
    private Long userId;
    /**
     * 方案Id
     */
    @JsonProperty("schema_id")
//    @NotNull(message = "方案ID不能为空")
    private Integer schemaId;

    /***/
    @JsonProperty("device_id")
    private String deviceId;
    /**
     * 备注
     */
    @JsonProperty("remark")
    private String remark;
    /**
     * 记录时间
     */
    @JsonProperty("update_time")
    @JsonFormat(pattern = DateUtil.DATE_TIME_FORMAT, timezone = "GMT+8")
    private Date updateTime;
    /**
     * 净化前tds 值
     */
    @JsonProperty("tds")
    private Integer tds;
    /**
     * 净化前色度
     */
    @JsonProperty("color")
    private Float color;
    /**
     * 净化前温度
     */
    @JsonProperty("trt")
    private Double trt;
    /**
     * 净化前水浊度
     */
    @JsonProperty("tbdt")
    private Double tbdt;
    /**
     * 净化后的tds
     */
    @JsonProperty("purify_tds")
    private Integer purifyTds;
    /**
     * 净化后的色度
     */
    @JsonProperty("purify_color")
    private Double purifyColor;
    /**
     * 净化后的温度
     */
    @JsonProperty("purify_trt")
    private Double purifyTrt;
    /**
     * 净化后的温度
     */
    @JsonProperty("show_temp")
    @Column(nullable = false, columnDefinition = "INT default 100")
    private Integer showTemp;
    /**
     * 净化后的水浊度
     */
    @JsonProperty("purify_tbdt")
    private Double purifyTbdt;
    /**
     * 水量
     */
    @JsonProperty("amount")
    private Double amount;
    /***/
    @JsonProperty("lng")
    private Double lng;
    /***/
    @JsonProperty("lat")
    private Double lat;
    /***/
    @JsonProperty("province")
    private String province;
    /***/
    @JsonProperty("city")
    private String city;

    @JsonProperty("brandname")
    private String brandname;

    @JsonProperty
    private String area;

    @JsonProperty
    private String address;

    @JsonProperty("phone_number")
    private String phoneNumber;

    @JsonProperty("customer")
    private String customer;

    @JsonProperty("model")
    private Integer model;

    @JsonProperty("imei")
    private String imei;

    @JsonProperty("merchant_name")
    private String merchantName;

    @JsonProperty("passage_id")
    private String passageId;
    @JsonProperty("repairer_id")
    private Integer repairerId;

    @Transient
    private Integer isOnline = 0;


    @JsonFormat(pattern = DateUtil.DATE_TIME_FORMAT, timezone = "GMT+8")
    private Date shakeHandTime;

    /**
     *  add by hzj at 2018-11-16 14:01:54
     */
//    @JsonIgnore
    private Integer hardwareVersion = 2;

    /**
     * 水质
     */
    @Transient
    private Integer quality;

    @Transient
    private List<PassageEntity> passList;
    /*水质状态表关联*/
//    @OneToOne(cascade=CascadeType.ALL,optional=true)
//    @JoinColumn(name="imei",insertable=false,updatable=false)

    @JsonProperty("machine_status")
    private int machineStatus;



    @Transient
    private Status waterStatus;

    public Status getWaterStatus() {
        return waterStatus;
    }

    public void setWaterStatus(Status waterStatus) {
        this.waterStatus = waterStatus;
    }

    public List<PassageEntity> getPassList() {
        return passList;
    }

    public void setPassList(List<PassageEntity> passList) {
        this.passList = passList;
    }

    public String getMerchantName() {
        return merchantName;
    }

    public void setMerchantName(String merchantName) {
        this.merchantName = merchantName;
    }

    public String getPassageId() {
        return passageId;
    }

    public void setPassageId(String passageId) {
        this.passageId = passageId;
    }

    public String getImei() {
        return imei;
    }

    public void setImei(String imei) {
        this.imei = imei;
    }

    public Integer getModel() {
        return model;
    }

    public void setModel(Integer model) {
        this.model = model;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Integer getTds() {
        return tds;
    }

    public void setTds(Integer tds) {
        this.tds = tds;
    }

    public Float getColor() {
        return color;
    }

    public void setColor(Float color) {
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

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getBrandname() {
        return brandname;
    }

    public void setBrandname(String brandname) {
        this.brandname = brandname;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Integer getSchemaId() {
        return schemaId;
    }

    public void setSchemaId(Integer schemaId) {
        this.schemaId = schemaId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Double getLng() {
        return lng;
    }

    public void setLng(Double lng) {
        this.lng = lng;
    }

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public Integer getQuality() {
        return quality;
    }

    public void setQuality(Integer quality) {
        this.quality = quality;
    }

    public Integer getShowTemp() {
        return showTemp;
    }

    public Integer getIsOnline() {
        return isOnline;
    }

    public void setIsOnline(Integer isOnline) {
        this.isOnline = isOnline;
    }

    public Date getShakeHandTime() {
        return shakeHandTime;
    }

    public void setShakeHandTime(Date shakeHandTime) {
        this.shakeHandTime = shakeHandTime;
    }

    public void setShowTemp(Integer showTemp) {
        this.showTemp = showTemp;
    }

    public Integer getHardwareVersion() {
        return hardwareVersion;
    }

    public void setHardwareVersion(Integer hardwareVersion) {
        this.hardwareVersion = hardwareVersion;
    }

    public int getMachineStatus() {
        return machineStatus;
    }

    public void setMachineStatus(int machineStatus) {
        this.machineStatus = machineStatus;
    }

    public Integer getRepairerId() {
        return repairerId;
    }

    public void setRepairerId(Integer repairerId) {
        this.repairerId = repairerId;
    }
}