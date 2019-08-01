package com.he.water.entity.schema;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author hzh
 * @since 2018-11-13
 */

@Entity
@Table(name="tb_schema_detail")
@DynamicInsert
@DynamicUpdate
public class SchemaDetail implements Serializable {

    private static final long serialVersionUID = 1L;

  	@Id  	
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    /**
     * 方案Id
     */
    private Integer schemaId;
    /**
     * 标注名称
     */
    private String name;
    /**
     * 水量 单位ml
     */
    private Integer waterAmount;


   public Integer getId() {
      return id;
   }

   public void setId(Integer id) {
      this.id = id;
   }

   public Integer getSchemaId() {
      return schemaId;
   }

   public void setSchemaId(Integer schemaId) {
      this.schemaId = schemaId;
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


}