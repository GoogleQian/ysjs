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
 * @since 2018-11-12
 */

@Entity
@Table(name="tb_schema_detail")
public class SchemaDetail implements Serializable {

    private static final long serialVersionUID = 1L;

  	@Id
    @JsonProperty(value = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 方案ID
     */
    @JsonProperty(value = "schema_id")
    private Integer schemaId;

    /**
     * 标注名称
     */
    @JsonProperty(value = "name")
    private String name;
    /**
     * 水量 单位ml
     */
    @JsonProperty(value = "water_amount")
    private Integer waterAmount;


   public Integer getId() {
      return id;
   }

   public void setId(Integer id) {
      this.id = id;
   }

   public String getName() {
      return name;
   }

   public void setName(String name) {
      this.name = name;
   }

   public Integer getWaterAmount() {
      return waterAmount;
   }

   public void setWaterAmount(Integer waterAmount) {
      this.waterAmount = waterAmount;
   }


    public Integer getSchemaId() {
        return schemaId;
    }

    public void setSchemaId(Integer schemaId) {
        this.schemaId = schemaId;
    }
}