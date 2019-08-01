package com.xiaohe.ysjspt.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.validation.annotation.Validated;

/**
 * <p>
 * 
 * </p>
 *
 * @author wzq
 * @since 2018-10-15
 */

@Entity
@Table(name="tb_passage")
public class PassageEntity implements Serializable {

    private static final long serialVersionUID = 1L;

  	@Id
    @JsonProperty(value = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    /**
     * 设备ID
     */
    @JsonProperty(value = "dev_id")
    private Long devId;
    /**
     * 商家名称
     */
    @JsonProperty(value = "merchant_name")
    private String merchantName;
    /**
     * 通道类型(1：开水，2：热水，3,：常温水，4：冷水)
     */
    @JsonProperty(value = "passage_type")
    private Integer passageType;
    /**
     * 通道名称
     */
    @JsonProperty(value = "passage_name")
    private String passageName;
    /**
     * 通道号
     */
    @JsonProperty(value = "passage_no")
    private Integer passageNo;
    /**
     * 脉冲数
     */
    @JsonProperty(value = "pulse_num")
    private Integer pulseNum;
    @JsonProperty(value = "solu_id")
    private Integer soluId;
    /**
     * 费用设置（元/L）
     */
    @JsonProperty(value = "price")
    private Integer price;
    /**
     * 费用设置（元/L）
     */
    @JsonProperty(value = "amount")
    private Integer amount;
    @JsonProperty(value = "create_time")
    private Date createTime;
    @JsonProperty(value = "str_price")
    private String strPrice;


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

   public Integer getPassageType() {
      return passageType;
   }

   public void setPassageType(Integer passageType) {
      this.passageType = passageType;
   }

   public String getPassageName() {
      return passageName;
   }

   public void setPassageName(String passageName) {
      this.passageName = passageName;
   }

   public Integer getPassageNo() {
      return passageNo;
   }

   public void setPassageNo(Integer passageNo) {
      this.passageNo = passageNo;
   }

   public Integer getPrice() {
      return price;
   }

   public void setPrice(Integer price) {
      this.price = price;
   }

   public Date getCreateTime() {
      return createTime;
   }

   public void setCreateTime(Date createTime) {
      this.createTime = createTime;
   }


    public Long getDevId() {
        return devId;
    }

    public void setDevId(Long devId) {
        this.devId = devId;
    }

    public Integer getPulseNum() {
        return pulseNum;
    }

    public void setPulseNum(Integer pulseNum) {
        this.pulseNum = pulseNum;
    }

    public Integer getSoluId() {
        return soluId;
    }

    public void setSoluId(Integer soluId) {
        this.soluId = soluId;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public String getStrPrice() {
        return strPrice;
    }

    public void setStrPrice(String strPrice) {
        this.strPrice = strPrice;
    }
}