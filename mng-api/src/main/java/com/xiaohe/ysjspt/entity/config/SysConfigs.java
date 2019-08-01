package com.xiaohe.ysjspt.entity.config;


import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

/**
 * 系统参数
 *
 * @author hzh
 * @date 2018/9/268:51
 */
@Entity
@Table(name = "dev_sys_config")
@DynamicInsert
@DynamicUpdate
public class SysConfigs implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull(message = "设备id不能为空")
    private Long devId;

    /**
     * 机型 0节能 1步进
     */
    private Integer state;
    /**
     * 定时启动与取消状态  0启动 1取消
     */
    private Integer timingState;
    /**
     * 出水方式 0：OFF 1：ON
     */
    private Integer outType;
    /**
     * 消毒功能 0：OFF 1：ON
     */
    private Short disinfectType;
    /**
     * 换水功能 0：OFF 1：ON
     */
    private Integer changeType;
    /**
     * 停止加热温度 默认93℃  1/2
     */
    private Short stopTemp;
    /**
     * 自冲洗功能 0：OFF 1：ON   1/2
     */
    private Integer flushType;
    /**
     * 滤芯管理功能 0：OFF 1：ON  1/2
     */
    private Integer filterType;
    /**
     * 厂商logo展示功能 0：OFF 1：ON  1/2
     */
    private Integer logoShows;
    /**
     * 进水温差 最小值：0℃默认值：3℃最大值：9℃ 1/2
     */
    @Min(value = 1, message = "进水温差最小值为1")
    @Max(value = 9, message = "进水温差最大值为9")
    private Short inTemp;
    /**
     * 进水回差 最小值：1℃默认值：5℃最大值：9℃
     */
    @Min(value = 1, message = "进水回差最小值为1")
    @Max(value = 9, message = "进水回差最大值为9")
    private Short inHeight;
    /**
     * 水位检测灵敏度（L） 最小值：1默认值：15最大值：25 () 1/2
     */
    @Min(value = 1, message = "水位检测灵敏度最小值为1")
    @Max(value = 25, message = "水位检测灵敏度最大值为25")
    private Short delicacy;
    /**
     * 换水方式 最小值：1默认值：1最大值：3
     */
    @Min(value = 1, message = "换水方式最小值为1")
    @Max(value = 3, message = "换水方式最大值为3")
    private Integer changeMethod;
    /**
     * 水位一直检测 0：OFF 1：ON
     */
    private Integer checkWater;
    /**
     * 排空功能  步进型
     */
    private Integer emptyType;

    /**
     * 保温温差
     */
    @Min(value = 0, message = "保温温差最小值为0")
    @Max(value = 9, message = "保温温差最大值为9")
    private Short keepTemp;
    /**
     * 保温回差
     */
    @Min(value = 2, message = "保温回差最小值为2")
    @Max(value = 9, message = "保温回差最大值为9")
    private Short keepHeight;
    /**
     * 进水速度 最小值：0默认值：8 最大值：80  步进型
     */
    @Min(value = 0, message = "进水速度最小值为0")
    @Max(value = 80, message = "进水速度最大值为80")
    private Integer speed;
    @Transient
    private String imei;

    @Transient
    private String type;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    /**
     * 补水温差
     */
    private Integer addTemp;

    /**
     * 补水量
     */
    private Integer addAmount;

    /**
     * 显示温度
     */
    private Integer showTemp;

    /**
     * 下发时间
     */
    private Date sendTime;

    /**
     * 上报时间
     */
    private Date reportTime;

    public Date getSendTime() {
        return sendTime;
    }

    public void setSendTime(Date sendTime) {
        this.sendTime = sendTime;
    }

    public Date getReportTime() {
        return reportTime;
    }

    public void setReportTime(Date reportTime) {
        this.reportTime = reportTime;
    }

    public Integer getAddTemp() {
        return addTemp;
    }

    public void setAddTemp(Integer addTemp) {
        this.addTemp = addTemp;
    }

    public Integer getAddAmount() {
        return addAmount;
    }

    public void setAddAmount(Integer addAmount) {
        this.addAmount = addAmount;
    }

    public Integer getShowTemp() {
        return showTemp;
    }

    public void setShowTemp(Integer showTemp) {
        this.showTemp = showTemp;
    }


    public String getImei() {
        return imei;
    }

    public void setImei(String imei) {
        this.imei = imei;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getDevId() {
        return devId;
    }

    public void setDevId(Long devId) {
        this.devId = devId;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public Integer getTimingState() {
        return timingState;
    }

    public void setTimingState(Integer timingState) {
        this.timingState = timingState;
    }

    public Integer getOutType() {
        return outType;
    }

    public void setOutType(Integer outType) {
        this.outType = outType;
    }

    public Short getDisinfectType() {
        return disinfectType;
    }

    public void setDisinfectType(Short disinfectType) {
        this.disinfectType = disinfectType;
    }

    public Integer getChangeType() {
        return changeType;
    }

    public void setChangeType(Integer changeType) {
        this.changeType = changeType;
    }

    public Short getStopTemp() {
        return stopTemp;
    }

    public void setStopTemp(Short stopTemp) {
        this.stopTemp = stopTemp;
    }

    public Integer getFlushType() {
        return flushType;
    }

    public void setFlushType(Integer flushType) {
        this.flushType = flushType;
    }

    public Integer getFilterType() {
        return filterType;
    }

    public void setFilterType(Integer filterType) {
        this.filterType = filterType;
    }

    public Integer getLogoShows() {
        return logoShows;
    }

    public void setLogoShows(Integer logoShows) {
        this.logoShows = logoShows;
    }

    public Short getInTemp() {
        return inTemp;
    }

    public void setInTemp(Short inTemp) {
        this.inTemp = inTemp;
    }

    public Short getInHeight() {
        return inHeight;
    }

    public void setInHeight(Short inHeight) {
        this.inHeight = inHeight;
    }

    public Short getKeepTemp() {
        return keepTemp;
    }

    public void setKeepTemp(Short keepTemp) {
        this.keepTemp = keepTemp;
    }

    public Short getKeepHeight() {
        return keepHeight;
    }

    public void setKeepHeight(Short keepHeight) {
        this.keepHeight = keepHeight;
    }

    public Short getDelicacy() {
        return delicacy;
    }

    public void setDelicacy(Short delicacy) {
        this.delicacy = delicacy;
    }

    public Integer getChangeMethod() {
        return changeMethod;
    }

    public void setChangeMethod(Integer changeMethod) {
        this.changeMethod = changeMethod;
    }

    public Integer getCheckWater() {
        return checkWater;
    }

    public void setCheckWater(Integer checkWater) {
        this.checkWater = checkWater;
    }

    public Integer getEmptyType() {
        return emptyType;
    }

    public void setEmptyType(Integer emptyType) {
        this.emptyType = emptyType;
    }

    public Integer getSpeed() {
        return speed;
    }

    public void setSpeed(Integer speed) {
        this.speed = speed;
    }
}
