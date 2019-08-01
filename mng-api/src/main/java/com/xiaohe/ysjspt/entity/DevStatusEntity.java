package com.xiaohe.ysjspt.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.xiaohe.ysjspt.utils.DateUtil;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
/**
 * @author
 * Administrator
 */
@Entity
@Table(name="tb_dev_status")
public class DevStatusEntity implements Serializable {

    private static final long serialVersionUID = 2446493944464368L;

    /**id*/
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JsonProperty(value="id")
    private Long id;

    /**设备号*/
    @JsonProperty("device_id")
    private String deviceId;

    /**状态*/
    @JsonProperty("status")
    private String status;

    /**记录时间*/
    @JsonProperty("update_time")
    @JsonFormat(pattern = DateUtil.DATE_TIME_FORMAT, timezone = "GMT+8")
    private Date updateTime;



    public Long getId() {
        return id;
    }
    public void setId(Long id) {
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
