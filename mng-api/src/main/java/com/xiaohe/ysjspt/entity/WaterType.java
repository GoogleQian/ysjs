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
 * @author gmq
 * @since 2018-11-08
 */

@Entity
@Table(name="tb_water_type")
public class WaterType implements Serializable {

    private static final long serialVersionUID = 1L;

  	@Id
    @JsonProperty(value = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    /**
     * 出水类型 
     */
    @JsonProperty(value = "type")
    private String type;
    /**
     * 出水类型
     */
    @JsonProperty(value = "modify_time")
    private Date modifyTime;


   public Integer getId() {
      return id;
   }

   public void setId(Integer id) {
      this.id = id;
   }

   public String getType() {
      return type;
   }

   public void setType(String type) {
      this.type = type;
   }

    public Date getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }
}