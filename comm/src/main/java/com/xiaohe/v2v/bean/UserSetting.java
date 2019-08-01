package com.xiaohe.v2v.bean;

import com.xiaohe.entity.DevUserConfig;
import com.xiaohe.v2v.utility.ByteUtility;
import com.xiaohe.v2v.utility.Log4jUtil;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

public class UserSetting{
	
	
	public DevUserConfig parseData(byte[] array) {
		DevUserConfig devUser = new DevUserConfig();
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
		devUser.setImei(imei);
		//机型
		Integer state = byteBuf.readByte() & 0xff;
		devUser.setState(state);
		//当前日
		int day = byteBuf.readByte() & 0xff;
		devUser.setDay(day);
		//当前小时
		int hours = byteBuf.readByte() & 0xff;
		devUser.setHours(hours);
		//当前分钟
		int minutes = byteBuf.readByte() & 0xff;
		devUser.setMinutes(minutes);
		//开机日
		int startDay = byteBuf.readByte() & 0xff;
		devUser.setStartDay(startDay);
		//关机日
		int stopDay = byteBuf.readByte() & 0xff;
		devUser.setStopDay(stopDay);
		//定时第一段开时间
		byte[] startOne = byteBuf.readBytes(2).array();
		if(startOne.length != 0) {		
			devUser.setStartOne(ByteUtility.getTimeStr(startOne));
		}
		//定时第一段关时间
		byte[] stopOne = byteBuf.readBytes(2).array(); 
		if(stopOne.length != 0) {		
			devUser.setStopOne(ByteUtility.getTimeStr(stopOne));
		}
		//定时第二段开时间
		byte[] startTwo = byteBuf.readBytes(2).array();
		if(startTwo.length != 0) {		
			devUser.setStartTwo(ByteUtility.getTimeStr(startTwo));
		}
		//定时第二段关时间
		byte[] stopTwo = byteBuf.readBytes(2).array();
		if(stopTwo.length != 0) {		
			devUser.setStopTwo(ByteUtility.getTimeStr(stopTwo));
		}
		//定时第三段开时间		
		byte[] startTir = byteBuf.readBytes(2).array();		
		if(startTir.length != 0) {		
			devUser.setStartThr(ByteUtility.getTimeStr(startTir));
		}
		//定时第三段关时间
		byte[] stopTir = byteBuf.readBytes(2).array();
		if(stopTir.length != 0) {		
			devUser.setStopThr(ByteUtility.getTimeStr(stopTir));
		}
		//定时消毒第一段时间
		byte[] disOne = byteBuf.readBytes(2).array();
		if(disOne.length != 0) {		
			devUser.setCleanOne(ByteUtility.getTimeStr(disOne));
		}
		//定时消毒第二段时间
		byte[] disTwo = byteBuf.readBytes(2).array();
		if(disTwo.length != 0) {		
			devUser.setCleanTwo(ByteUtility.getTimeStr(disTwo));
		}
		//定时消毒第三段时间
		byte[] disTir = byteBuf.readBytes(2).array();
		if(disTir.length != 0) {		
			devUser.setCleanThr(ByteUtility.getTimeStr(disTir));
		}
		//换水时间
		byte[] changeTime = byteBuf.readBytes(2).array();
		if(changeTime.length != 0) {		
			devUser.setChangeTime(ByteUtility.getTimeStr(changeTime));
		}
		//换水时长（min）
		int hangeTime =byteBuf.readShort();
		devUser.setHangeTime(hangeTime);
		//换水周期(day)
		int changeCycle = byteBuf.readByte() & 0xff;
		devUser.setChangeCycle(changeCycle);
		//滤芯寿命（m³）
		int filterLife = byteBuf.readByte() & 0xff;
		devUser.setFilterLife(filterLife);
		//显示温度
		int showTemp = byteBuf.readByte() & 0xff;
		devUser.setShowTemp(showTemp);	
	    //定时冲洗时间
		byte[] flushTime = byteBuf.readBytes(2).array();
		if(changeTime.length != 0) {		
			devUser.setFlushTime(ByteUtility.getTimeStr(flushTime));
		}
	    //冲洗时长
		int flushDuration = byteBuf.readByte() & 0xff;
		devUser.setFlushDuration((short)flushDuration);
	    //消毒时长
		short cleanDuration = byteBuf.readShort();
		devUser.setCleanDuration(cleanDuration);
		
		
		Log4jUtil.info("IMEI：" + imei +",state:"+state+"(1：节能,0：步进),day:"+day+",hour:"+hours+",min:"+minutes+
				",startDay:"+startDay+",stopDay:"+stopDay+",startOne:"+ByteUtility.toHexStr(startOne)+",stopOne:"+
				ByteUtility.toHexStr(stopOne)+",startTwo:"+ByteUtility.toHexStr(startTwo)+",stopTwo:"+ByteUtility.toHexStr(stopTwo)+",startTir:"+ByteUtility.toHexStr(startTir)+
				",stopTir:"+ByteUtility.toHexStr(stopTir)+",disOne:"+ByteUtility.toHexStr(disOne)+",disTwo:"+ByteUtility.toHexStr(disTwo)+",disTir:"+ByteUtility.toHexStr(disTir)+
				",changeTime:"+ByteUtility.toHexStr(changeTime)+",hangeTime:"+hangeTime+",changeCycle:"
				+changeCycle+",filterLife:"+filterLife+",showTemp:"+showTemp+",flushTime:"+ByteUtility.toHexStr(flushTime)+",flushDuration:"+flushDuration+",cleanDuratino:"+cleanDuration);
	
		return devUser;
	}
}
