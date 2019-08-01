package com.xiaohe.v2v.bean;

import com.xiaohe.hservice.WaterBaseData;
import com.xiaohe.v2v.mapper.WaterDataDao;
import com.xiaohe.v2v.utility.ByteUtility;
import com.xiaohe.v2v.utility.Log4jUtil;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

public class ControlMachineWater extends WaterBaseData{
	
	public void parseData(byte[] array) {
		ByteBuf byteBuf = Unpooled.buffer();
		byteBuf.writeBytes(array);
		//协议头
		byteBuf.skipBytes(1);	
		//协议版本号
		byteBuf.skipBytes(1);	
		//数据包长度
		byteBuf.skipBytes(1);	
		//命令字
		int ctr = byteBuf.readByte()& 0xff;
		//控制结果
		int status = byteBuf.readByte()& 0xff;
		Log4jUtil.info("机器应答状态："+status);
		if(status == 0){
			this.setTurnOnWaterStatus(1);
		}
		else {
			this.setTurnOnWaterStatus(2);
		}
		//控制ID
		int ctrId = byteBuf.readInt();		
		this.setCtrId(ctrId);
		//校验和
		byteBuf.skipBytes(1);			
	}
}
