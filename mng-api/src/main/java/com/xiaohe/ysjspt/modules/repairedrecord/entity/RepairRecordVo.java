package com.xiaohe.ysjspt.modules.repairedrecord.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 维修详情表
 * </p>
 *
 * @author gmq
 * @since 2018-12-10
 */


public class RepairRecordVo implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer id;
    /**
     * 维修的设备code
     */
    private String devCode;
    /**
     * 维修人员id
     */
    private Integer repairerId;
    /**
     * 维修详情
     */
    private String content;
    /**
     * 维修时间
     */
    @Temporal(value = TemporalType.TIMESTAMP)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date maintenanceTime;

    private Integer page = 1;
    private Integer pageSize = 10;
    private String startTime;
    private String endTime;
    private String repairerName;

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getRepairerName() {
        return repairerName;
    }

    public void setRepairerName(String repairerName) {
        this.repairerName = repairerName;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDevCode() {
        return devCode;
    }

    public void setDevCode(String devCode) {
        this.devCode = devCode;
    }

    public Integer getRepairerId() {
        return repairerId;
    }

    public void setRepairerId(Integer repairerId) {
        this.repairerId = repairerId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getMaintenanceTime() {
        return maintenanceTime;
    }

    public void setMaintenanceTime(Date maintenanceTime) {
        this.maintenanceTime = maintenanceTime;
    }


}