package com.xiaohe.v2v.bean;

import java.util.ArrayList;

import com.xiaohe.hservice.WaterBaseData;
import com.xiaohe.v2v.mapper.WaterDataDao;
import com.xiaohe.v2v.server.TcpServer;
import com.xiaohe.v2v.utility.ByteUtility;
import com.xiaohe.v2v.utility.Log4jUtil;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

public class MachineState extends WaterBaseData {

	public int insertStatus(WaterDataDao dao) {
		return dao.insertStatus(this);
	}
	
	public int updateStatus(WaterDataDao dao) {
		return dao.updateStatus(this);
	}

	public void parseData(byte[] array) {
		ArrayList arrayList = new ArrayList();

		ByteBuf byteBuf = Unpooled.buffer();
		byteBuf.writeBytes(array);
		// 协议头
		byteBuf.skipBytes(1);
		// 协议版本号
		byteBuf.skipBytes(1);
		// 数据包长度
		byteBuf.skipBytes(1);
		// 命令字
		byteBuf.skipBytes(1);
		// IMEI
		byte[] IMEIBytes = byteBuf.readBytes(8).array();
		String IMEI = ByteUtility.toHexStr(IMEIBytes);
		this.setIMEI(IMEI);

		// for(int i=0;i<byteBuf.readableBytes()-1;i=0) {
		// int mark = byteBuf.readByte() & 0xff;
		// switch(mark){
		// case 0x00:
		// //初始状态
		// //byteBuf.skipBytes(18);
		// 净水量
		int amountHeightInitial = (byteBuf.readShort() & 0xff) << 8;
		int amountLowInitial = (byteBuf.readShort() & 0xff);
		double amountInitial = amountHeightInitial + amountLowInitial;
		this.setAmountStatus(amountInitial);
		// 换水
		int changeWaterStatusInitial = byteBuf.readByte() & 0xff;
		this.setChangeWaterStatus(changeWaterStatusInitial);
		// 冲洗
		int rinseStatusInitial = byteBuf.readByte() & 0xff;
		this.setRinseStatus(rinseStatusInitial);
		// 消毒
		int disinfectionStatusInitial = byteBuf.readByte() & 0xff;
		this.setDisinfectionStatus(disinfectionStatusInitial);
		// 童锁
		int childLockStatusInitial = byteBuf.readByte() & 0xff;
		this.setChildLockStatus(childLockStatusInitial);
		// 水温
		int temp = byteBuf.readByte() & 0xff;
		this.setTem(temp);
		// 水位
		int waterLevelStatusInitial = byteBuf.readByte() & 0xff;
		this.setWaterLevelStatus(waterLevelStatusInitial);
		// 进水
		int inWaterStatusInitial = byteBuf.readByte() & 0xff;
		this.setInWaterStatus(inWaterStatusInitial);
		// 出水1
		int outOne = byteBuf.readByte() & 0xff;
		this.setOutPassOne(outOne);
		// 出水2
		int outTwo = byteBuf.readByte() & 0xff;
		this.setOutPassTwo(outTwo);
		// 出水3
		int outThird = byteBuf.readByte() & 0xff;
		this.setOutPassThird(outThird);
		// 出水4
		int outFour = byteBuf.readByte() & 0xff;
		this.setOutPassFour(outFour);
		// 加热
		int heatingStatusInitial = byteBuf.readByte() & 0xff;
		this.setHeatingStatus(heatingStatusInitial);
		// 制冷
		int refrigerationStatusInitial = byteBuf.readByte() & 0xff;
		this.setRefrigerationStatus(refrigerationStatusInitial);
		// 进水TDS
		int inTds = byteBuf.readByte() & 0xff;
		this.setInTds(inTds);
		// 出水TDS
		int outTds = byteBuf.readByte() & 0xff;
		this.setOutTds(outTds);
		// 故障代码
		int erroCodeInitial = byteBuf.readByte() & 0xff;
		this.setErroCode(erroCodeInitial);
		// 信号强度
		int modelStrengthInitial = byteBuf.readByte() & 0xff;
		this.setModelStrength(modelStrengthInitial);
		// 时控
		int timeCtr = byteBuf.readByte() & 0xff;
		this.setTimingState(timeCtr);
		int machineStatus = byteBuf.readByte() & 0xff;
		this.setMachineStatus(machineStatus);
		// break;
		// case 0x01:
		// //净水量
		//// int amountHeight = (byteBuf.readByte() & 0xff) << 8;
		//// int amountLow = (byteBuf.readByte() & 0xff);
		//// double amount = amountHeight + amountLow;
		//
		// double amount = byteBuf.readInt();
		//
		// this.setAmountStatus(amount);
		// break;
		// case 0x02:
		// //换水
		// int changeWaterStatus = byteBuf.readByte() & 0xff;
		// this.setChangeWaterStatus(changeWaterStatus);
		// break;
		// case 0x03:
		// //冲洗
		// int rinseStatus = byteBuf.readByte() & 0xff;
		// this.setRinseStatus(rinseStatus);
		// break;
		// case 0x04:
		// //消毒
		// int disinfectionStatus = byteBuf.readByte() & 0xff;
		// this.setDisinfectionStatus(disinfectionStatus);
		// break;
		// case 0x05:
		// //童锁
		// int childLockStatus = byteBuf.readByte() & 0xff;
		// this.setChildLockStatus(childLockStatus);
		// break;
		// case 0x06:
		// //水温
		// int tempHeight = byteBuf.readByte() & 0xff;
		// double temp = (double) tempHeight;
		// this.setTempStatus(temp);
		// break;
		// case 0x07:
		// //水位
		// int waterLevelStatus = byteBuf.readByte() & 0xff;
		// this.setWaterLevelStatus(waterLevelStatus);
		// break;
		// case 0x08:
		// //进水
		// int inWaterStatus = byteBuf.readByte() & 0xff;
		// this.setInWaterStatus(inWaterStatus);
		// break;
		// case 0x09:
		// //出水类型
		// int outWaterType = byteBuf.readByte() & 0xff;
		// this.setOutWaterType(outWaterType);
		// //出水量
		// int outWaterAmountHeight = (byteBuf.readByte() & 0xff) << 8;
		// int outWaterAmountLow = byteBuf.readByte() & 0xff;
		// double outWaterAmount = outWaterAmountHeight + outWaterAmountLow;
		// this.setOutWaterAmount(outWaterAmount);
		// break;
		// case 0x0a:
		// //加热
		// int heatingStatus = byteBuf.readByte() & 0xff;
		// this.setHeatingStatus(heatingStatus);
		// break;
		// case 0x0b:
		// //制冷
		// int refrigerationStatus = byteBuf.readByte() & 0xff;
		// this.setRefrigerationStatus(refrigerationStatus);
		// break;
		// case 0x0c:
		// //水质类型
		// int waterQualityStatus = byteBuf.readByte() & 0xff;
		// this.setWaterQualityStatus(waterQualityStatus);
		// //tds
		// int waterQualityTdsHeight = (byteBuf.readByte() & 0xff) << 8;
		// int waterQualityTdsLow = byteBuf.readByte() & 0xff;
		// double waterQualityTds = waterQualityTdsHeight + waterQualityTdsLow;
		// this.setWaterQualityTds(waterQualityTds);
		// break;
		// case 0x0d:
		// //故障代码
		// int erroCode = byteBuf.readByte() & 0xff;
		// this.setErroCode(erroCode);
		// break;
		// case 0x0e:
		// //信号强度
		// int modelStrength = byteBuf.readByte() & 0xff;
		// this.setModelStrength(modelStrength);
		// break;
		// case 0x0f:
		// //时控
		// int timeCtr = byteBuf.readByte() & 0xff;
		// this.setTimingState(timeCtr);
		// }
		// }
		Log4jUtil.info("IMEI:" + this.getIMEI() + ",净水量:" + amountInitial + ",换水:" + changeWaterStatusInitial + ",冲洗:"
				+ rinseStatusInitial + ",消毒:" + disinfectionStatusInitial + ",童锁:" + childLockStatusInitial + ",水温:"
				+ temp + ",水位:" + waterLevelStatusInitial + ",进水:" + inWaterStatusInitial + ",出水通道1:" + outOne
				+ ",出水通道2:" + outTwo + ",出水通道3:" + outThird + ",出水通道4:" + outFour + ",加热:" + heatingStatusInitial
				+ ",制冷:" + refrigerationStatusInitial + ",进水TDS:" + inTds + ",出水TDS:" + outTds + ",故障代码:"
				+ erroCodeInitial + ",信号强度:" + modelStrengthInitial + ",时控:" + timeCtr + ",机器开关状态:" + machineStatus);
	}
}
