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
 * 用户参数
 *
 * @author hzh
 * @date 2018/9/26
 */
@Entity
@Table(name = "dev_user_config")
@DynamicInsert
@DynamicUpdate
public class UserConfigs implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull(message = "设备id不能为空")
    private Long devId;

    /**
     * 机器模型
     */
    private Integer state;

    /**
     * 当前日 最小值：1 默认值：/ 最大值：7
     */
    @Min(value = 1, message = "当前日最小值为1")
    @Max(value = 7, message = "当前日最大值为7")
    private int day;
    /**
     * 当前小时 最小值：0 默认值：/ 最大值：23
     */
    @Min(value = 0, message = "当前小时最小值为0")
    @Max(value = 23, message = "当前小时最大值为23")
    private int hours;
    /**
     * 当前分钟 最小值：0 默认值：/ 最大值：59
     */
    @Min(value = 0, message = "当前分钟最小值为0分钟")
    @Max(value = 59, message = "当前分钟最大值为29分钟")
    private int minutes;
    /**
     * 开机日 最小值：1 默认值：/ 最大值：7
     */
    @Min(value = 1, message = "开机日最小值1")
    @Max(value = 7, message = "开机日最大值为7")
    private int startDay;
    /**
     * 关机日 最小值：1 默认值：/ 最大值：7
     */
    @Min(value = 1, message = "关机日最小值1")
    @Max(value = 7, message = "关机日最大值为7")
    private int stopDay;
    /**
     * 定时开机1时间 最小值：00:00 默认值：/ 最大值：23:50
     */
    private String startOne;
    /**
     * 定时开机2时间
     */
    private String startTwo;
    /**
     * 定时开机3时间
     */
    private String startThr;
    /**
     * 定时关机1时间 最小值：00:00 默认值：/ 最大值：23:50
     */
    private String stopOne;
    /**
     * 定时关机2时间
     */
    private String stopTwo;
    /**
     * 定时关机3时间
     */
    private String stopThr;
    /**
     * 消毒1时间 最小值：00:00 默认值：/ 最大值：23:50
     */
    private String cleanOne;
    /**
     * 消毒2时间
     */
    private String cleanTwo;
    /**
     * 消毒3时间
     */
    private String cleanThr;
    /**
     * 换水时间 最小值：00:00 默认值：/ 最大值：23:50
     */
    private String changeTime;
    /**
     * 换水时长(秒) 最小值：30  最大值：300
     */
    @Min(value = 30, message = "换水时长最小值30秒")
    @Max(value = 300, message = "换水时长最大值为300秒")
    private int hangeTime;
    /**
     * 换水周期(天) 最小值：1 默认值：/ 最大值：30
     */
    @Min(value = 1, message = "换水周期(天)最小值1")
    @Max(value = 30, message = "换水周期(天)最大值为30")
    private int changeCycle;
    /**
     * 滤芯寿命（m³） 最小值：0 默认值：/ 最大值：40
     */
    @Min(value = 0, message = "滤芯寿命（m³）最小值0")
    @Max(value = 40, message = "滤芯寿命（m³）最大值为40")
    private int filterLife;
    @Transient
    private String imei;
    @Transient
    private String type;

    /**
     * 显示温度 最小值：0 最大值：100
     */
    @Min(value = 0, message = "显示温度最小值1℃")
    @Max(value = 100, message = "显示温度最大值为100℃")
    private int showTemp;

    /**
     * 实际温度 最小值：0 最大值：100
     */
    @Min(value = 0, message = "实际温度最小值1℃")
    @Max(value = 100, message = "实际温度最大值为100℃")
    private int actualTemp;

    /**
     * 冲洗时间 最小值：00:00 默认值：/ 最大值：23:50
     */
    private String flushTime;

    /**
     * 冲洗时长(分钟) 最小值：1 默认值：5 最大值：10
     */
    @Min(value = 1, message = "冲洗时长最小值1分钟")
    @Max(value = 10, message = "冲洗时长最大值为10分钟")
    private short flushDuration;

    /**
     * 消毒时长(秒) 最小值：30  最大值：300
     */
    @Min(value = 30, message = "消毒时长最小值30秒")
    @Max(value = 300, message = "消毒时长最大值为300秒")
    private short cleanDuration;

    /**
     * 下发时间
     */
    private Date sendTime;

    /**
     * 上报时间
     */
    private Date reportTime;

    public int getShowTemp() {
        return showTemp;
    }

    public void setShowTemp(int showTemp) {
        this.showTemp = showTemp;
    }

    public int getActualTemp() {
        return actualTemp;
    }

    public void setActualTemp(int actualTemp) {
        this.actualTemp = actualTemp;
    }

    public String getFlushTime() {
        return flushTime;
    }

    public void setFlushTime(String flushTime) {
        this.flushTime = flushTime;
    }

    public short getFlushDuration() {
        return flushDuration;
    }

    public void setFlushDuration(short flushDuration) {
        this.flushDuration = flushDuration;
    }

    public short getCleanDuration() {
        return cleanDuration;
    }

    public void setCleanDuration(short cleanDuration) {
        this.cleanDuration = cleanDuration;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public int getHours() {
        return hours;
    }

    public void setHours(int hours) {
        this.hours = hours;
    }

    public int getMinutes() {
        return minutes;
    }

    public void setMinutes(int minutes) {
        this.minutes = minutes;
    }

    public int getStartDay() {
        return startDay;
    }

    public void setStartDay(int startDay) {
        this.startDay = startDay;
    }

    public int getStopDay() {
        return stopDay;
    }

    public void setStopDay(int stopDay) {
        this.stopDay = stopDay;
    }

    public String getStartOne() {
        return startOne;
    }

    public void setStartOne(String startOne) {
        this.startOne = startOne;
    }

    public String getStartTwo() {
        return startTwo;
    }

    public void setStartTwo(String startTwo) {
        this.startTwo = startTwo;
    }

    public String getStartThr() {
        return startThr;
    }

    public void setStartThr(String startThr) {
        this.startThr = startThr;
    }

    public String getStopOne() {
        return stopOne;
    }

    public void setStopOne(String stopOne) {
        this.stopOne = stopOne;
    }

    public String getStopTwo() {
        return stopTwo;
    }

    public void setStopTwo(String stopTwo) {
        this.stopTwo = stopTwo;
    }

    public String getStopThr() {
        return stopThr;
    }

    public void setStopThr(String stopThr) {
        this.stopThr = stopThr;
    }

    public String getCleanOne() {
        return cleanOne;
    }

    public void setCleanOne(String cleanOne) {
        this.cleanOne = cleanOne;
    }

    public String getCleanTwo() {
        return cleanTwo;
    }

    public void setCleanTwo(String cleanTwo) {
        this.cleanTwo = cleanTwo;
    }

    public String getCleanThr() {
        return cleanThr;
    }

    public void setCleanThr(String cleanThr) {
        this.cleanThr = cleanThr;
    }

    public String getChangeTime() {
        return changeTime;
    }

    public void setChangeTime(String changeTime) {
        this.changeTime = changeTime;
    }

    public int getHangeTime() {
        return hangeTime;
    }

    public void setHangeTime(int hangeTime) {
        this.hangeTime = hangeTime;
    }

    public int getChangeCycle() {
        return changeCycle;
    }

    public void setChangeCycle(int changeCycle) {
        this.changeCycle = changeCycle;
    }

    public int getFilterLife() {
        return filterLife;
    }

    public void setFilterLife(int filterLife) {
        this.filterLife = filterLife;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

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
}
