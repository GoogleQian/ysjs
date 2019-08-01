package com.xiaohe.hservice;

import java.io.Serializable;

public class WaterBaseData implements Serializable {
	public String devMac;// mac地址
	public String IMEI;
	public Integer cmd;// 命令字

	public Integer turnOnWaterStatus;// 售水结果
	public Integer setSwitchTimeStatus;// 设置开关机时间结果

	public Integer ctrId;// 控制ID（订单号）
	public Integer tem;// 温度
	public Integer sellAmount;// 售水量

	public double inTds;// 入水TDS
	public double outTds;// 出水TDS
	public double inTurbidity;// 入水浊度
	public double outTurbidity;// 出水浊度
	public double inChroma;// 入水色度
	public double outChroma;// 出水色度
	public double reportAmount;// 水量

	public double amountStatus;//机器上报状态水量
	public int changeWaterStatus;//换水状态
	public int rinseStatus;//冲洗状态
	public int disinfectionStatus;//消毒状态
	public int childLockStatus;//童锁状态
	public int tempStatus;//机器上报状态水温
	public int waterLevelStatus;//水位
	public int inWaterStatus;//进水
	public int outPassOne;//出水通道1
	public int outPassTwo;//出水通道2
	public int outPassThird;//出水通道3
	public int outPassFour;//出水通道4	
	public int outWaterType;//控制命令出水类型
	public double outWaterAmount;//控制命令出水量
	public int heatingStatus;//加热
	public int refrigerationStatus;//制冷
	public int waterQualityStatus;//水质类型
	public double waterQualityTds;//tds
	public int erroCode;//故障代码
	public int modelStrength;//信号强度
	public int timingState;//时控
	public int machineStatus;//机器开关状态
	
	public int getMachineStatus() {
		return machineStatus;
	}

	public void setMachineStatus(int machineStatus) {
		this.machineStatus = machineStatus;
	}

	public int showTemp;//显示温度
	public int getOutPassOne() {
		return outPassOne;
	}

	public void setOutPassOne(int outPassOne) {
		this.outPassOne = outPassOne;
	}

	public int getOutPassTwo() {
		return outPassTwo;
	}

	public void setOutPassTwo(int outPassTwo) {
		this.outPassTwo = outPassTwo;
	}

	public int getOutPassThird() {
		return outPassThird;
	}

	public void setOutPassThird(int outPassThird) {
		this.outPassThird = outPassThird;
	}

	public int getOutPassFour() {
		return outPassFour;
	}

	public void setOutPassFour(int outPassFour) {
		this.outPassFour = outPassFour;
	}

	
	
	public int getShowTemp() {
		return showTemp;
	}

	public void setShowTemp(int showTemp) {
		this.showTemp = showTemp;
	}

	public int getTimingState() {
		return timingState;
	}

	public void setTimingState(int timingState) {
		this.timingState = timingState;
	}

	public int getErroCode() {
		return erroCode;
	}

	public void setErroCode(int erroCode) {
		this.erroCode = erroCode;
	}

	public int getModelStrength() {
		return modelStrength;
	}

	public void setModelStrength(int modelStrength) {
		this.modelStrength = modelStrength;
	}

	public double getAmountStatus() {
		return amountStatus;
	}

	public void setAmountStatus(double amountStatus) {
		this.amountStatus = amountStatus;
	}

	public int getChangeWaterStatus() {
		return changeWaterStatus;
	}

	public void setChangeWaterStatus(int changeWaterStatus) {
		this.changeWaterStatus = changeWaterStatus;
	}

	public int getRinseStatus() {
		return rinseStatus;
	}

	public void setRinseStatus(int rinseStatus) {
		this.rinseStatus = rinseStatus;
	}

	public int getDisinfectionStatus() {
		return disinfectionStatus;
	}

	public void setDisinfectionStatus(int disinfectionStatus) {
		this.disinfectionStatus = disinfectionStatus;
	}

	public int getChildLockStatus() {
		return childLockStatus;
	}

	public void setChildLockStatus(int childLockStatus) {
		this.childLockStatus = childLockStatus;
	}

	public int getTempStatus() {
		return tempStatus;
	}

	public void setTempStatus(int tempStatus) {
		this.tempStatus = tempStatus;
	}

	public int getWaterLevelStatus() {
		return waterLevelStatus;
	}

	public void setWaterLevelStatus(int waterLevelStatus) {
		this.waterLevelStatus = waterLevelStatus;
	}

	public int getInWaterStatus() {
		return inWaterStatus;
	}

	public void setInWaterStatus(int inWaterStatus) {
		this.inWaterStatus = inWaterStatus;
	}

	public int getOutWaterType() {
		return outWaterType;
	}

	public void setOutWaterType(int outWaterType) {
		this.outWaterType = outWaterType;
	}

	public double getOutWaterAmount() {
		return outWaterAmount;
	}

	public void setOutWaterAmount(double outWaterAmount) {
		this.outWaterAmount = outWaterAmount;
	}

	public int getHeatingStatus() {
		return heatingStatus;
	}

	public void setHeatingStatus(int heatingStatus) {
		this.heatingStatus = heatingStatus;
	}

	public int getRefrigerationStatus() {
		return refrigerationStatus;
	}

	public void setRefrigerationStatus(int refrigerationStatus) {
		this.refrigerationStatus = refrigerationStatus;
	}

	public int getWaterQualityStatus() {
		return waterQualityStatus;
	}

	public void setWaterQualityStatus(int waterQualityStatus) {
		this.waterQualityStatus = waterQualityStatus;
	}

	public double getWaterQualityTds() {
		return waterQualityTds;
	}

	public void setWaterQualityTds(double waterQualityTds) {
		this.waterQualityTds = waterQualityTds;
	}

	public String getDevMac() {
		return devMac;
	}

	public void setDevMac(String devMac) {
		this.devMac = devMac;
	}
	
	public String getIMEI() {
		return IMEI;
	}

	public void setIMEI(String IMEI) {
		this.IMEI = IMEI;
	}

	public Integer getCtrId() {
		return ctrId;
	}

	public void setCtrId(Integer ctrId) {
		this.ctrId = ctrId;
	}

	public Integer getCmd() {
		return cmd;
	}

	public void setCmd(Integer cmd) {
		this.cmd = cmd;
	}

	public Integer getTurnOnWaterStatus() {
		return turnOnWaterStatus;
	}

	public void setTurnOnWaterStatus(Integer turnOnWaterStatus) {
		this.turnOnWaterStatus = turnOnWaterStatus;
	}

	public Integer getSetSwitchTimeStatus() {
		return setSwitchTimeStatus;
	}

	public void setSetSwitchTimeStatus(Integer setSwitchTimeStatus) {
		this.setSwitchTimeStatus = setSwitchTimeStatus;
	}

	public Integer getTem() {
		return tem;
	}

	public void setTem(Integer tem) {
		this.tem = tem;
	}

	public Integer getSellAmount() {
		return sellAmount;
	}

	public void setSellAmount(Integer sellAmount) {
		this.sellAmount = sellAmount;
	}

	public double getInTds() {
		return inTds;
	}

	public void setInTds(double inTds) {
		this.inTds = inTds;
	}

	public double getOutTds() {
		return outTds;
	}

	public void setOutTds(double outTds) {
		this.outTds = outTds;
	}

	public double getInTurbidity() {
		return inTurbidity;
	}

	public void setInTurbidity(double inTurbidity) {
		this.inTurbidity = inTurbidity;
	}

	public double getOutTurbidity() {
		return outTurbidity;
	}

	public void setOutTurbidity(double outTurbidity) {
		this.outTurbidity = outTurbidity;
	}

	public double getInChroma() {
		return inChroma;
	}

	public void setInChroma(double inChroma) {
		this.inChroma = inChroma;
	}

	public double getOutChroma() {
		return outChroma;
	}

	public void setOutChroma(double outChroma) {
		this.outChroma = outChroma;
	}

	public double getReportAmount() {
		return reportAmount;
	}

	public void setReportAmount(double reportAmount) {
		this.reportAmount = reportAmount;
	}

}
