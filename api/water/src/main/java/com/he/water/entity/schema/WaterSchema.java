package com.he.water.entity.schema;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 
 * </p>
 *
 * @author hzh
 * @since 2018-11-13
 */

@Entity
@Table(name="tb_water_schema")
@DynamicInsert
@DynamicUpdate
public class WaterSchema implements Serializable {

    private static final long serialVersionUID = 1L;

  	@Id  	
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    /**
     * 方案名称
     */
    private String proName;
    /**
     * 用户ID
     */
    private Long userId;
    /**
     * 创建时间
     */
 	@Temporal(value = TemporalType.TIMESTAMP)
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;


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


}