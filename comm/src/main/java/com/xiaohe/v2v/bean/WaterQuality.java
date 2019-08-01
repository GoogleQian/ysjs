package com.xiaohe.v2v.bean;

import com.xiaohe.hservice.WaterBaseData;
import com.xiaohe.v2v.mapper.WaterDataDao;
import com.xiaohe.v2v.utility.ByteUtility;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

public class WaterQuality extends WaterBaseData {
	public int insertWaterQuality(WaterDataDao dao) {
		return dao.insertWaterQuality(this);
	}

	public void parseData(byte[] array) {
		ByteBuf byteBuf = Unpooled.buffer();
		byteBuf.writeBytes(array);
		// 协议头
		// byteBuf.skipBytes(1);
		int heard = byteBuf.readByte() & 0xff;
		// 协议版本号
		// byteBuf.skipBytes(1);
		int no = byteBuf.readByte() & 0xff;
		// 数据包长度
		// byteBuf.skipBytes(1);
		int lengh = byteBuf.readByte() & 0xff;
		// 命令字
		int cmd = byteBuf.readByte() & 0xff;
		this.setCmd(cmd);
		// MAC地址
//		byte[] contents = byteBuf.readBytes(6).array();
		//IMEI
		byte[] contents = byteBuf.readBytes(8).array();
		String content = ByteUtility.toHexStr(contents);
		this.setDevMac(content);
		// 入水TDS
		int inTdsHeight = (byteBuf.readByte() & 0xff) << 8;
		int inTdsLow = byteBuf.readByte() & 0xff;
		double inTds = inTdsHeight + inTdsLow;
		this.setInTds(inTds);
		// 出水TDS
		int outTdsHeight = (byteBuf.readByte() & 0xff) << 8;
		int outTdsLow = byteBuf.readByte() & 0xff;
		double outTds = outTdsHeight + outTdsLow;
		this.setOutTds(outTds);
		// 入水浊度
		int inTurbidityHeight = (byteBuf.readByte() & 0xff) << 8;
		int inTurbidityLow = byteBuf.readByte() & 0xff;
		double inTurbidity = inTurbidityHeight + inTurbidityLow;
		this.setInTurbidity(inTurbidity);
		// 出水浊度
		int outTurbidityHeight = (byteBuf.readByte() & 0xff) << 8;
		int outTurbidityLow = byteBuf.readByte() & 0xff;
		double outTurbidity = outTurbidityHeight + outTurbidityLow;
		this.setOutTurbidity(outTurbidity);
		// 入水色度
		int inChromaHeight = (byteBuf.readByte() & 0xff) << 8;
		int inChromaLow = byteBuf.readByte() & 0xff;
		double inChroma = inChromaHeight + inChromaLow;
		this.setInChroma(inChroma);
		// 出水色度
		int outChromaHeight = (byteBuf.readByte() & 0xff) << 8;
		int outChromaLow = byteBuf.readByte() & 0xff;
		double outChroma = outChromaHeight + outChromaLow;
		this.setOutChroma(outChroma);
		// 水量
		int amountHeight = (byteBuf.readByte() & 0xff) << 8;
		int amountLow = byteBuf.readByte() & 0xff;
		double amount = amountHeight + amountLow;
		this.setReportAmount(amount);
		// 校验和
		// byteBuf.skipBytes(1);
	}
}
