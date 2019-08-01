package com.xiaohe.ysjspt.modules.repairedrecord.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.validator.constraints.NotBlank;

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

@Entity
@Table(name = "tb_repair_record")
@DynamicInsert
@DynamicUpdate
public class RepairRecord implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    /**
     * 维修的设备code
     */
    @NotBlank(message = "请选择设备")
    private String devCode;
    /**
     * 维修人员id
     */
    private Integer repairerId;
    /**
     * 维修详情
     */
    @NotBlank(message = "维修详情不能为空")
    private String content;
    /**
     * 维修时间
     */
    @Temporal(value = TemporalType.TIMESTAMP)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date maintenanceTime;

    @Transient
    private String repairerName;


    public String getRepairerName() {
        return repairerName;
    }

    public void setRepairerName(String repairerName) {
        this.repairerName = repairerName;
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