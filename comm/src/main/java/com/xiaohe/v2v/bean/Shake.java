package com.xiaohe.v2v.bean;

import com.xiaohe.hservice.WaterBaseData;
import com.xiaohe.v2v.mapper.WaterDataDao;
import com.xiaohe.v2v.utility.ByteUtility;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

public class Shake extends WaterBaseData{
	
	public void updateCreateTime(WaterDataDao dao){
		//更新数据库中的信息		
		dao.updateCreateTime(this);		
	}
	
	
	public void parseData(byte[] array) {
		ByteBuf byteBuf = Unpooled.buffer();
		byteBuf.writeBytes(array);
		//协议头
		//byteBuf.skipBytes(1);	
		int heard = byteBuf.readByte()& 0xff;
		//协议版本号
//		byteBuf.skipBytes(1);	
		int no = byteBuf.readByte()& 0xff;
		//数据包长度
//		byteBuf.skipBytes(1);	
		int lengh = byteBuf.readByte()& 0xff;
		//命令字
		int cmd = byteBuf.readByte()& 0xff;
		this.setCmd(cmd);
		//内容(MAC地址)
//		byte[] contents = byteBuf.readBytes(6).array();
		//IMEI
		String content =ByteUtility.toHexStr(byteBuf.readBytes(8).array());
//		String content = ByteUtility.toHexStr(contents);
		this.setIMEI(content);
		//showTemp
		int showTemp = byteBuf.readByte()& 0xff;
		this.setShowTemp(showTemp);
		//校验和
		//byteBuf.skipBytes(1);	
		int jyh = byteBuf.readByte()& 0xff;
		int i = 0 ;
		
	}
}
