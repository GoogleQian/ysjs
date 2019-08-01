package com.xiaohe.v2v.bean;



import com.xiaohe.entity.DevSysConfig;
import com.xiaohe.v2v.utility.ByteUtility;
import com.xiaohe.v2v.utility.Log4jUtil;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

public class SysSetting{
	
	public DevSysConfig parseData(byte[] array) {
		DevSysConfig devSys = new DevSysConfig();
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
		//数据内容
		//IMEI
		String imei =ByteUtility.toHexStr(byteBuf.readBytes(8).array());		
		devSys.setImei(imei);
		//空一个字节 
		byteBuf.skipBytes(1);
		//机型
		Integer state = byteBuf.readByte() & 0xff;
		devSys.setState(state);
		
		//节能型
		if(state == 1) {			
			//定时启动或取消
			Integer timingState = byteBuf.readByte() & 0xff;
			devSys.setTimingState(timingState);			
			//停止加热温度
			short stopTemp = (short) (byteBuf.readByte() & 0xff);
			devSys.setStopTemp(stopTemp);
			//出水方式
			Integer outType = byteBuf.readByte() & 0xff;
			devSys.setOutType(outType);
			//消毒功能
			short disinfectType = (short)(byteBuf.readByte() & 0xff);
			devSys.setDisinfectType(disinfectType);
			//换水功能
			Integer changeType = byteBuf.readByte() & 0xff;
			devSys.setChangeType(changeType);
			//冲洗功能
			Integer flushType = byteBuf.readByte() & 0xff;
			devSys.setFlushType(flushType);
			//滤芯管理功能
			Integer filterType = byteBuf.readByte() & 0xff;
			devSys.setFilterType(filterType);
			//是否显示LOGO
			Integer logoType = byteBuf.readByte() & 0Xff;
			devSys.setLogoShows(logoType);
			//进水温差
			short inTemp =(short)( byteBuf.readByte() & 0xff);
			devSys.setInTemp(inTemp);
			//进水回差
			short inHeight = (short)(byteBuf.readByte() & 0xff);
			devSys.setInHeight(inHeight);
			//换水方式
			Integer changeMethod = byteBuf.readByte() & 0xff;
			devSys.setChangeMethod(changeMethod);
			//水位一直检测
			Integer checkWater = byteBuf.readByte() & 0xff;
			devSys.setCheckWater(checkWater);
			//水位检测灵敏度
			Short delicacy =(short)( byteBuf.readByte() & 0xff);
			devSys.setDelicacy(delicacy);		
			
			//补全数据
			//排空功能
			devSys.setEmptyType(0);
			//保温温差
			devSys.setKeepTemp((short)1);
			//保温回差
			devSys.setKeepHeight((short)2);
			//进水速度
			devSys.setSpeed(10);
			
			Log4jUtil.info("节能型--IMEI:"+imei+",定时启动或取消:"+timingState+",停止加热温度:"+
					stopTemp+",出水方式:"+outType+",消毒功能:"+disinfectType+",换水功能:"+
					changeType+",冲洗功能:"+flushType+",滤芯管理功能"+filterType+",是否显示LOGO:"+
					logoType+",进水温差:"+inTemp+",进水回差:"+inHeight+",换水方式:"+changeMethod+
					",水位一直检测:"+checkWater+",水位检测灵敏度:"+delicacy);
		}
		//步进型
		else if(state == 0) {
			//定时启动或取消
			Integer timingState = byteBuf.readByte() & 0xff;
			devSys.setTimingState(timingState);	
			//停止加热温度
			short stopTemp = (short) (byteBuf.readByte() & 0xff);
			devSys.setStopTemp(stopTemp);
			//排空功能
			Integer emptyType = byteBuf.readByte() & 0xff;
			devSys.setEmptyType(emptyType);
			//冲洗功能
			Integer flushType = byteBuf.readByte() & 0xff;
			devSys.setFlushType(flushType);
			//滤芯管理功能
			Integer filterType = byteBuf.readByte() & 0xff;
			devSys.setFilterType(filterType);
			//是否显示LOGO
			Integer logoType = byteBuf.readByte() & 0Xff;
			devSys.setLogoShows(logoType);
			//进水温差
			short inTemp =(short)( byteBuf.readByte() & 0xff);
			devSys.setInTemp(inTemp);
			//保温温差
			short keepTemp = (short)(byteBuf.readByte() & 0xff);
			devSys.setKeepTemp(keepTemp);
			//保温回差
			short keepHeight = (short)(byteBuf.readByte() & 0xff);
			devSys.setKeepHeight(keepHeight);
			//进水速度
			Integer speed = byteBuf.readByte() & 0xff;
			devSys.setSpeed(speed);
			//水位检测灵敏度
			Short delicacy =(short)( byteBuf.readByte() & 0xff);
			devSys.setDelicacy(delicacy);	
			
			//补全数据
			//出水方式
			devSys.setOutType(1);
			//消毒功能
			devSys.setDisinfectType((short)0);
			//换水功能
			devSys.setChangeType(1);
			//进水回差
			devSys.setInHeight((short)1);
			//换水方式
			devSys.setChangeMethod(1);
			//水位一直检测
			devSys.setCheckWater(0);
			
			Log4jUtil.info("步进型--IMEI:"+imei+",定时启动或取消:"+timingState+",停止加热温度:"+
			stopTemp+",排空功能:"+emptyType+",冲洗功能:"+flushType+",滤芯管理功能:"+filterType+
			",是否显示LOGO:"+logoType+",进水温差:"+inTemp+",保温温差:"+keepTemp+",保温回差:"+
			keepHeight+",进水速度:"+speed+",水位检测灵敏度:"+delicacy);
		}
		else {
			Log4jUtil.info("机型不正确");
		}	
		
		//校验和
		byteBuf.skipBytes(1);	
		return devSys;
	}
}
