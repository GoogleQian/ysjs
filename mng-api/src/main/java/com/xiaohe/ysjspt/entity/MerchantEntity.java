package com.xiaohe.ysjspt.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * <p>
 * 
 * </p>
 *
 * @author wzq
 * @since 2018-10-15
 */

@Entity
@Table(name="tb_merchant")
public class MerchantEntity implements Serializable {

    private static final long serialVersionUID = 1L;

  	@Id
    @JsonProperty(value = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    /**
     * 登录账号
     */
    @JsonProperty(value = "account_number")
    private String accountNumber;
    /**
     * 商家名称
     */
    @JsonProperty(value = "merchant_name")
    private String merchantName;
    /**
     * 联系人
     */
    @JsonProperty(value = "contact")
    private String contact;
    /**
     * 手机号码
     */
    @JsonProperty(value = "phone_number")
    private String phoneNumber;
    /**
     * 代理商家(0:愉升）
     */
    @JsonProperty(value = "proxy_merchant")
    private String proxyMerchant;
    /**
     * 厂家手续费
     */
    @JsonProperty(value = "factory_fee")
    private Double factoryFee;
    /**
     * 代理手续费
     */
    @JsonProperty(value = "proxy_fee")
    private Double proxyFee;
    /**
     * 银行手续费
     */
    @JsonProperty(value = "bank_fee")
    private Double bankFee;
    /**
     * 支付通道(0:富友支付）
     */
    @JsonProperty(value = "pay_way")
    private Integer payWay;
    /**
     * 富友商户ID
     */
    @JsonProperty(value = "merchant_id")
    private Integer merchantId;
    /**
     * 业务模式(0:购买设备模式，1:租赁设备模式）
     */
    @JsonProperty(value = "business_type")
    private Integer businessType;
    /**
     * 单个机器月租(租赁模式才显示）
     */
    @JsonProperty(value = "monthly_rent")
    private Double monthlyRent;
    /**
     * 分账比例（租赁模式才显示。如：平台/厂家/机主  填写15/5/85)
     */
    @JsonProperty(value = "sub_account")
    private String subAccount;
    /**
     * 所在区域
     */
    @JsonProperty(value = "region")
    private String region;
    /**
     * 详细地址
     */
    @JsonProperty(value = "address")
    private String address;
    /**
     * 备注
     */
    @JsonProperty(value = "remark")
    private String remark;
    @JsonProperty(value = "create_time")
    private Date createTime;


   public Integer getId() {
      return id;
   }

   public void setId(Integer id) {
      this.id = id;
   }

   public String getAccountNumber() {
      return accountNumber;
   }

   public void setAccountNumber(String accountNumber) {
      this.accountNumber = accountNumber;
   }

   public String getMerchantName() {
      return merchantName;
   }

   public void setMerchantName(String merchantName) {
      this.merchantName = merchantName;
   }

   public String getContact() {
      return contact;
   }

   public void setContact(String contact) {
      this.contact = contact;
   }

   public String getPhoneNumber() {
      return phoneNumber;
   }

   public void setPhoneNumber(String phoneNumber) {
      this.phoneNumber = phoneNumber;
   }

   public String getProxyMerchant() {
      return proxyMerchant;
   }

   public void setProxyMerchant(String proxyMerchant) {
      this.proxyMerchant = proxyMerchant;
   }

   public Double getFactoryFee() {
      return factoryFee;
   }

   public void setFactoryFee(Double factoryFee) {
      this.factoryFee = factoryFee;
   }

   public Double getProxyFee() {
      return proxyFee;
   }

   public void setProxyFee(Double proxyFee) {
      this.proxyFee = proxyFee;
   }

   public Double getBankFee() {
      return bankFee;
   }

   public void setBankFee(Double bankFee) {
      this.bankFee = bankFee;
   }

   public Integer getPayWay() {
      return payWay;
   }

   public void setPayWay(Integer payWay) {
      this.payWay = payWay;
   }

   public Integer getMerchantId() {
      return merchantId;
   }

   public void setMerchantId(Integer merchantId) {
      this.merchantId = merchantId;
   }

   public Integer getBusinessType() {
      return businessType;
   }

   public void setBusinessType(Integer businessType) {
      this.businessType = businessType;
   }

   public Double getMonthlyRent() {
      return monthlyRent;
   }

   public void setMonthlyRent(Double monthlyRent) {
      this.monthlyRent = monthlyRent;
   }

   public String getSubAccount() {
      return subAccount;
   }

   public void setSubAccount(String subAccount) {
      this.subAccount = subAccount;
   }

   public String getRegion() {
      return region;
   }

   public void setRegion(String region) {
      this.region = region;
   }

   public String getAddress() {
      return address;
   }

   public void setAddress(String address) {
      this.address = address;
   }

   public String getRemark() {
      return remark;
   }

   public void setRemark(String remark) {
      this.remark = remark;
   }

   public Date getCreateTime() {
      return createTime;
   }

   public void setCreateTime(Date createTime) {
      this.createTime = createTime;
   }


}