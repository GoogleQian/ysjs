package com.xiaohe.v2v.bean;

import com.xiaohe.entity.DevUserConfig;
import com.xiaohe.v2v.utility.ByteUtility;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

public class GetUserSet extends DevUserConfig{
	public void parseData(byte[] array) {
		ByteBuf byteBuf = Unpooled.buffer();
		byteBuf.writeBytes(array);
		//协议头
		byteBuf.skipBytes(1);
		//版本号
		byteBuf.skipBytes(1);	
		//数据包长度
		byteBuf.skipBytes(1);	
		//命令字
		byteBuf.skipBytes(1);	
		//IMEI
		String imei =ByteUtility.toHexStr(byteBuf.readBytes(8).array());		
		this.setImei(imei);
		//机型
		int state =  byteBuf.readByte() & 0xff;
		this.setState(state);
		//上报类型
		int type =  byteBuf.readByte() & 0xff;
		switch(type) {
		case 1:
			//显示温度
			int showTemp = byteBuf.readByte() & 0xff;
			this.setShowTemp(showTemp);
		}		
	}

}
