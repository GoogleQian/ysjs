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
 * @since 2018-10-16
 */

@Entity
@Table(name="tb_water_mng")
public class WaterMngEntity implements Serializable {

    private static final long serialVersionUID = 1L;

  	@Id
    @JsonProperty(value = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    /**
     * 商家名称
     */
    @JsonProperty(value = "merchant_name")
    private String merchantName;
    /**
     * 校区名称
     */
    @JsonProperty(value = "region")
    private String region;
    @JsonProperty(value = "create_time")
    private Date createTime;
    /**
     * 最少余额
     */
    @JsonProperty(value = "least_balance")
    private Double leastBalance;
    /**
     * 最大放水
     */
    @JsonProperty(value = "max_release")
    private Double maxRelease;
    /**
     * 最低消费
     */
    @JsonProperty(value = "lowest_consumption")
    private Double lowestConsumption;
    /**
     * 按钮模式(1:按下出水，放手关水,2:按一下出水，再按一下关水)
     */
    @JsonProperty(value = "button_type")
    private Integer buttonType;
    /**
     * 支付模式
     */
    @JsonProperty(value = "pay_type")
    private Integer payType;
    /**
     * 计费模式
     */
    @JsonProperty(value = "billing_type")
    private Integer billingType;


   public Integer getId() {
      return id;
   }

   public void setId(Integer id) {
      this.id = id;
   }

   public String getMerchantName() {
      return merchantName;
   }

   public void setMerchantName(String merchantName) {
      this.merchantName = merchantName;
   }

   public String getRegion() {
      return region;
   }

   public void setRegion(String region) {
      this.region = region;
   }

   public Date getCreateTime() {
      return createTime;
   }

   public void setCreateTime(Date createTime) {
      this.createTime = createTime;
   }

   public Double getLeastBalance() {
      return leastBalance;
   }

   public void setLeastBalance(Double leastBalance) {
      this.leastBalance = leastBalance;
   }

   public Double getMaxRelease() {
      return maxRelease;
   }

   public void setMaxRelease(Double maxRelease) {
      this.maxRelease = maxRelease;
   }

   public Double getLowestConsumption() {
      return lowestConsumption;
   }

   public void setLowestConsumption(Double lowestConsumption) {
      this.lowestConsumption = lowestConsumption;
   }

   public Integer getButtonType() {
      return buttonType;
   }

   public void setButtonType(Integer buttonType) {
      this.buttonType = buttonType;
   }

   public Integer getPayType() {
      return payType;
   }

   public void setPayType(Integer payType) {
      this.payType = payType;
   }

   public Integer getBillingType() {
      return billingType;
   }

   public void setBillingType(Integer billingType) {
      this.billingType = billingType;
   }


}