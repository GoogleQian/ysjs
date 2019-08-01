package com.xiaohe.ysjspt.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.validator.constraints.NotBlank;

/**
 * <p>
 * 
 * </p>
 *
 * @author gmq
 * @since 2018-11-07
 */

@Entity
@Table(name="tb_coupon")
public class Coupon implements Serializable {

    private static final long serialVersionUID = 1L;

  	@Id
    @JsonProperty(value = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    /**
     * 设备ID
     */
    @JsonProperty(value = "dev_id")
    @NotNull(message = "设备ID不能为空")
    private Long devId;
    /**
     * 优惠券数量
     */
    @JsonProperty(value = "num")
     @NotNull(message = "数量不能为空")
    @Min(value = 1,message = "必须为数字且大于1")
    private Integer num;

    @JsonProperty(value = "str_price")
    @NotBlank(message = "价格不能为空")
    @Pattern(regexp = "^(?!0+(\\.0+)?$)\\d+(\\.\\d+)?$",message = "价格非法！")
    private String strPrice;
    /**
     * 金额
     */
    @JsonProperty(value = "price")
    private Integer price;
    /**
     * 开始时间
     */
    @JsonProperty(value = "start_time")
    @NotNull(message = "开始时间不能为空")
    @Temporal(value = TemporalType.TIMESTAMP)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date startTime;
    /**
     * 截止时间
     */
    @JsonProperty(value = "end_time")
    @NotNull(message = "截止时间不能为空")
    @Temporal(value = TemporalType.TIMESTAMP)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date endTime;
    /**
     * 创建时间
     */
    @JsonProperty(value = "create_time")
    private Date createTime;
    @Transient
    @JsonProperty(value = "dev_ids")
    private String devIds;
    @Transient
    @JsonProperty(value = "dev_no")
    private String devNo;

    public Coupon() {
    }

    public Coupon(Long devId, Integer num, Integer price, Date startTime, Date endTime) {
        this.devId = devId;
        this.num = num;
        this.price = price;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public Integer getId() {
      return id;
   }

   public void setId(Integer id) {
      this.id = id;
   }

   public Long getDevId() {
      return devId;
   }

   public void setDevId(Long devId) {
      this.devId = devId;
   }

   public Integer getNum() {
      return num;
   }

   public void setNum(Integer num) {
      this.num = num;
   }

   public Integer getPrice() {
      return price;
   }

   public void setPrice(Integer price) {
      this.price = price;
   }

   public Date getStartTime() {
      return startTime;
   }

   public void setStartTime(Date startTime) {
      this.startTime = startTime;
   }

   public Date getEndTime() {
      return endTime;
   }

   public void setEndTime(Date endTime) {
      this.endTime = endTime;
   }

    public String getDevIds() {
        return devIds;
    }

    public void setDevIds(String devIds) {
        this.devIds = devIds;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getDevNo() {
        return devNo;
    }

    public void setDevNo(String devNo) {
        this.devNo = devNo;
    }

    public String getStrPrice() {
        return strPrice;
    }

    public void setStrPrice(String strPrice) {
        this.strPrice = strPrice;
    }
}