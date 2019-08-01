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
 * @since 2018-08-27
 */

@Entity
@Table(name="tb_dev_status")
public class DevStatus implements Serializable {

    private static final long serialVersionUID = 1L;

  	@Id
    @JsonProperty(value = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    /**
     * 设备ID
     */
    @JsonProperty(value = "device_id")
    private String deviceId;
    /**
     * 设备状态数据
     */
    @JsonProperty(value = "status")
    private String status;
    /**
     * 历史记录时间
     */
    @JsonProperty(value = "update_time")
    private Date updateTime;


   public Integer getId() {
      return id;
   }

   public void setId(Integer id) {
      this.id = id;
   }

   public String getDeviceId() {
      return deviceId;
   }

   public void setDeviceId(String deviceId) {
      this.deviceId = deviceId;
   }

   public String getStatus() {
      return status;
   }

   public void setStatus(String status) {
      this.status = status;
   }

   public Date getUpdateTime() {
      return updateTime;
   }

   public void setUpdateTime(Date updateTime) {
      this.updateTime = updateTime;
   }


}