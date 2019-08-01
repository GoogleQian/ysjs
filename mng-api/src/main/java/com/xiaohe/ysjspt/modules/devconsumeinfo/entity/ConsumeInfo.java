package com.xiaohe.ysjspt.modules.devconsumeinfo.entity;

import java.io.Serializable;

/**
 * @program: ysjsApi
 * @description: 消费信息
 * @author: Gmq
 * @date: 2018-12-17 11:26
 **/
public class ConsumeInfo implements Serializable {

    private Integer usageCount;

    private String devId;
    /**
     * 水温
     */
    private Integer waterTemp;
    /**
     * 0.订单已取消 2.已下发出水指令，等待应答 (待出水) 1.已应答，售卖成功 （已出水）3.售水失败
     */
    private Integer sellStatus;
    /**
     * 总水量
     */
    private String waterAmount;
    /**
     * 总金额
     */
    private String moneyAmount;
    /**
     * 杯名称
     */
    private String[] cupName;
    /**
     * 杯使用次数
     */
    private Object cup;

    public ConsumeInfo() {
    }

    public String getMoneyAmount() {
        return moneyAmount;
    }

    public void setMoneyAmount(String moneyAmount) {
        this.moneyAmount = moneyAmount;
    }

    public Integer getUsageCount() {
        return usageCount;
    }

    public void setUsageCount(Integer usageCount) {
        this.usageCount = usageCount;
    }

    public String getDevId() {
        return devId;
    }

    public void setDevId(String devId) {
        this.devId = devId;
    }

    public Integer getWaterTemp() {
        return waterTemp;
    }

    public void setWaterTemp(Integer waterTemp) {
        this.waterTemp = waterTemp;
    }

    public String getWaterAmount() {
        return waterAmount;
    }

    public void setWaterAmount(String waterAmount) {
        this.waterAmount = waterAmount;
    }

    public Integer getSellStatus() {
        return sellStatus;
    }

    public void setSellStatus(Integer sellStatus) {
        this.sellStatus = sellStatus;
    }

    public String[] getCupName() {
        return cupName;
    }

    public void setCupName(String[] cupName) {
        this.cupName = cupName;
    }

    public Object getCup() {
        return cup;
    }

    public void setCup(Object cup) {
        this.cup = cup;
    }
}
