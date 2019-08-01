package com.xiaohe.ysjspt.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.*;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * <p>
 * 
 * </p>
 *
 * @author gmq
 * @since 2018-11-12
 */

@Entity
@Table(name="tb_water_schema")
public class WaterSchema implements Serializable {

    private static final long serialVersionUID = 1L;

  	@Id
    @JsonProperty(value = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    /**
     * 方案名称
     */
    @JsonProperty(value = "pro_name")
    @NotNull(message = "方案名称不能为空")
    private String proName;
    /**
     * 用户ID
     */
    @JsonProperty(value = "user_id")
    private Long userId;
    /**
     * 创建时间
     */
    @JsonProperty(value = "create_time")
    private Date createTime;
    /**
     * 方案明细
     */
    @Transient
    private List<SchemaDetail> schemalist;


   public Integer getId() {
      return id;
   }

   public void setId(Integer id) {
      this.id = id;
   }

    public String getProName() {
        return proName;
    }

    public void setProName(String proName) {
        this.proName = proName;
    }

    public Long getUserId() {
      return userId;
   }

   public void setUserId(Long userId) {
      this.userId = userId;
   }

   public Date getCreateTime() {
      return createTime;
   }

   public void setCreateTime(Date createTime) {
      this.createTime = createTime;
   }


    public List<SchemaDetail> getSchemalist() {
        return schemalist;
    }

    public void setSchemalist(List<SchemaDetail> schemalist) {
        this.schemalist = schemalist;
    }
}