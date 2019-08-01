package com.xiaohe.ysjspt.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 售水方案
 * </p>
 *
 * @author gmq
 * @since 2018-11-08
 */

@Entity
@Table(name="tb_sell_schema")
public class SellSchema implements Serializable {

    private static final long serialVersionUID = 1L;

  	@Id
    @JsonProperty(value = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    /**
     * 商户ID
     */
    @JsonProperty(value = "user_id")
    private Integer userId;
    /**
     * 水量
     */
    @JsonProperty(value = "water_amount")
    private String waterAmount;
    /**
     * 水量返回
     */
    @Transient
    @JsonProperty(value = "amount")
    private Object amount;
    /**
     * 产品名称
     */
    @JsonProperty(value = "pro_name")
    private String proName;
    /**
     * 创建时间
     */
    @JsonProperty(value = "create_time")
    @Temporal(value = TemporalType.TIMESTAMP)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;


   public Integer getId() {
      return id;
   }

   public void setId(Integer id) {
      this.id = id;
   }

   public Integer getUserId() {
      return userId;
   }

   public void setUserId(Integer userId) {
      this.userId = userId;
   }

   public String getWaterAmount() {
      return waterAmount;
   }

   public void setWaterAmount(String waterAmount) {
      this.waterAmount = waterAmount;
   }

   public String getProName() {
      return proName;
   }

   public void setProName(String proName) {
      this.proName = proName;
   }

   public Date getCreateTime() {
      return createTime;
   }

   public void setCreateTime(Date createTime) {
      this.createTime = createTime;
   }

    public Object getAmount() {
        return amount;
    }

    public void setAmount(Object amount) {
        this.amount = amount;
    }
}