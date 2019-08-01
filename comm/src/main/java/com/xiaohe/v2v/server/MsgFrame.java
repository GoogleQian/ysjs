package com.xiaohe.v2v.server;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.Stack;
import java.util.function.Consumer;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import com.xiaohe.entity.DevSysConfig;
import com.xiaohe.entity.DevUserConfig;
import com.xiaohe.hservice.NotifyService;
import com.xiaohe.hservice.WaterBaseData;
import com.xiaohe.v2v.bean.ControlMachineWater;
import com.xiaohe.v2v.bean.GetUserSet;
import com.xiaohe.v2v.bean.MachineState;
import com.xiaohe.v2v.bean.Shake;
import com.xiaohe.v2v.bean.SysSetting;
import com.xiaohe.v2v.bean.UserSetting;
import com.xiaohe.v2v.bean.WaterQuality;
import com.xiaohe.v2v.mapper.WaterDataDao;
import com.xiaohe.v2v.utility.ByteUtility;
import com.xiaohe.v2v.utility.Log4jUtil;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;

public class MsgFrame {
	public static Channel gChannel = null;
	byte[] datas;
	Channel channel;
	private static ValidatorFactory factory = Validation.buildDefaultValidatorFactory();

	public MsgFrame(Channel channel, byte[] data) {
		this.channel = channel;
		datas = data;
	}

	public static <T> List<String> validate(T t) {
		Validator validator = factory.getValidator();
		Set<ConstraintViolation<T>> constraintViolations = validator.validate(t);

		List<String> messageList = new ArrayList<String>();
		for (ConstraintViolation<T> constraintViolation : constraintViolations) {
			messageList.add(constraintViolation.getMessage());
		}
		return messageList;
	}

	public byte[] addQRCode(byte[] reply, byte[] urlByte, byte[] devByte) {
		byte[] replyFir = new byte[reply.length - 3];
		byte[] replySed = new byte[3];
		byte[] splitter = new byte[1];
		splitter[0] = 0x2c;
		byte[] result = new byte[reply.length + urlByte.length + 1 + devByte.length];

		System.arraycopy(reply, 0, replyFir, 0, reply.length - 3);
		System.arraycopy(reply, reply.length - 3, replySed, 0, 3);

		System.arraycopy(replyFir, 0, result, 0, replyFir.length);

		System.arraycopy(urlByte, 0, result, replyFir.length, urlByte.length);

		System.arraycopy(splitter, 0, result, replyFir.length + urlByte.length, 1);

		System.arraycopy(devByte, 0, result, replyFir.length + urlByte.length + 1, devByte.length);

		System.arraycopy(replySed, 0, result, result.length - 3, replySed.length);

		return result;
	}

	public boolean hasBeenLogined(String IMEI) {
		Channel memoryChannel = ChanneRegistry.get(IMEI);
		if (memoryChannel == null) {
			Log4jUtil.warn("channel not in memory");
		}
		return memoryChannel != null ? true : false;
	}

	public void reactor() {
		Log4jUtil.info("data:" + ByteUtility.toHexStr(datas));
		int cmd = (datas[3] & 0xff);
		byte reciveCrc = datas[datas.length - 1];
		byte crc = ByteUtility.reciveCalcCrc(datas);
		Log4jUtil.info("reviceCrc:" + reciveCrc + ", crc:" + Math.abs(crc));
		if (Math.abs(reciveCrc) != Math.abs(crc)) {
			Log4jUtil.info("检验和不通过,跳过");
			return;
		}

		if (cmd != 0x20 && cmd != 0x35 && cmd != 0x43 && cmd != 0x45 && cmd != 0x47 
				&& cmd != 0x49 && cmd != 0x51 && cmd != 0x53 && cmd != 0x57 
				&& cmd != 0x59 && cmd != 0x61 && cmd != 0x63 && cmd != 0x75 && cmd != 0x77) {
			byte[] IMEIBytes = new byte[8];
			System.arraycopy(datas, 4, IMEIBytes, 0, 8);
			String IMEI = ByteUtility.toHexStr(IMEIBytes);
			if (!hasBeenLogined(IMEI)) {
				Channel memoryChannel = ChanneRegistry.get(IMEI);
				Log4jUtil.info("disconnect original channel");
				memoryChannel.close();
				ChanneRegistry.remove(memoryChannel);
			}
		}
		switch (cmd) {

		case 0x20:
			// 登录
			login(datas);
			break;
		case 0x30:
			// 握手
			onShakeHandRosponse(datas);
			break;
		case 0x32:
			// 上报机器状态
			onMachineStateRosponse(datas);
			break;
		case 0x35:
			// 出水应答
			onTurnonWaterResponse(datas);
			break;
		case 0x36:
			// 上报水质数据
			onWaterQualityRosponse(datas);
			break;
		case 0x38:
			// 设置开关机时间应答
			onSetSwitchTimeResponse(datas);
			break;
		case 0x43:
			// 机型设置应答
			onModelRosponse(datas);
			break;
		case 0x45:
			// 恢复默认值应答
			onRestoreDefaultsRosponse(datas);
			break;
		case 0x47:
			// 定时启动和取消应答
			onTimedStartAndCancelResponse(datas);
			break;
		case 0x49:
			// 手动换水应答
			onChangeWaterResponse(datas);
			break;
		case 0x51:
			// 手动消毒应答
			onDisinfectionResponse(datas);
			break;
		case 0x53:
			// 手动冲洗应答
			onFlushingResponse(datas);
			break;
		case 0x57:
			// 手动复位净水量应答
			onResetWaterVolumeResponse(datas);
			break;
		case 0x59:
			// 节能型应答
			onSetSysEnergyResponse(datas);
			break;
		case 0x61:
			// 步进型应答
			onSetSysStepResponse(datas);
			break;
		case 0x63:
			// 用户参数的设置应答
			onSetUserSettingResponse(datas);
			break;
		case 0x64:
			// 上报系统参数设置
			onSysSettingRosponse(datas);
			break;
		case 0x66:
			// 上报用户参数设置
			onUseSettingRosponse(datas);
			break;
		// case 0x69:
		case 0x68:
			// 获取用户设置
			onUserSetRosponse(datas);
			break;
		// case 0x71:
		case 0x70:
			// 硬件上报显示温度
			onShowTempRosponse(datas);
			break;
		// case 0x72:
		case 0x71:
			// 硬件上报水量
			onWaterAmountRosponse(datas);
			break;
		case 0x75:
			//下发通道信息应答
			onPassageInfoRosponse(datas);
			break;
		case 0x77:
			//手动开关机应答
			onCtlMichineStatus(datas);
			break;
		}
	}

	public void login(byte[] datas) {
		Log4jUtil.info("20 -- 登录");
		Log4jUtil.info("channel:" + channel.toString());

		ByteBuf byteBuf = Unpooled.buffer();
		byteBuf.writeBytes(datas);
		byteBuf.skipBytes(4);
		// IMEI
		byte[] IMEIBytes = byteBuf.readBytes(8).array();
		String IMEI = ByteUtility.toHexStr(IMEIBytes);
		WaterDataDao waterDataDao = TcpServer.getWaterDataDao();
		String version = waterDataDao.selectVersionByImei(IMEI);
		int devCount = waterDataDao.isInDB(IMEI);
		if (devCount > 0) {
			Log4jUtil.info("IMEI:" + IMEI + ",DB_HARDWARE_VERSION:" + version);
			if (hasBeenLogined(IMEI)) {
				Channel memoryChannel = ChanneRegistry.get(IMEI);
				Log4jUtil.info("disconnect original channel:" + memoryChannel.remoteAddress().toString());
				memoryChannel.close();
				ChanneRegistry.remove(memoryChannel);
			}
			Log4jUtil.info("add to channel map:" + channel.remoteAddress().toString());

			ChanneRegistry.add(IMEI, channel, version);
			loginRosponse(IMEI);
		} else {			
			Log4jUtil.info("IMEI:" + IMEI + " 不存在");
			channel.close();
		}
	}

	public void loginRosponse(String imei) {
		String version = ChanneRegistry.getVersion(imei);
		Date now = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yy:MM:dd:HH:mm");
		SimpleDateFormat dateFm = new SimpleDateFormat("EEEE");
		byte[] dateNowByte = new byte[5];
		String dateNowStr = sdf.format(now);
		String[] str = dateNowStr.split(":");
		for (int i = 0; i < str.length; i++) {
			dateNowByte[i] = (byte) Integer.parseInt(str[i]);
		}

		String currSun = dateFm.format(now);
		Log4jUtil.info("now:" + now);
		Log4jUtil.info("currSun:" + currSun);
		byte week = 0;
		switch (currSun) {
		case "Monday":
			week = 0x01;
			break;
		case "Tuesday":
			week = 0x02;
			break;
		case "Wednesday":
			week = 0x03;
			break;
		case "Thursday":
			week = 0x04;
			break;
		case "Friday":
			week = 0x05;
			break;
		case "Saturday":
			week = 0x06;
			break;
		case "Sunday":
			week = 0x07;
			break;
		}
		WaterDataDao waterDataDao = TcpServer.getWaterDataDao();
		String url = waterDataDao.selectUrl("url");
		String devId = waterDataDao.selectDevIdByIMEI(imei);

		byte[] urlByte = url.getBytes();
		byte[] devIdByte = devId.getBytes();
		byte[] reply = { (byte) 0xFE, // 标识
				(byte) 01, // 协议版本号
				0x0a, // 数据包长度
				0x21, // 命令字
				dateNowByte[0], dateNowByte[1], dateNowByte[2], week, dateNowByte[3], dateNowByte[4], // 内容
				0x01, // 校验和
				0x0d, 0x0a };
		if ("2".equals(version)) {
			Log4jUtil.info("url:" + url);
			Log4jUtil.info("devId:" + devId);
			reply = addQRCode(reply, urlByte, devIdByte);
		}
		byte crc = ByteUtility.calcCrc(reply);
		int arrayLenght = reply.length;
		reply[arrayLenght - 3] = crc;
		reply[2] = (byte)(arrayLenght-3);
		ByteBuf byteBuf2 = Unpooled.buffer(8);
		byteBuf2.writeBytes(reply);
		channel.writeAndFlush(byteBuf2);// 向客户端发送消息

		Log4jUtil.info("send:" + ByteUtility.toHexStr(reply));
	}

	public void onShakeHandRosponse(byte[] datas) {
		Log4jUtil.info("30 -- 握手 ");
		Log4jUtil.info("channel:" + channel.toString());

		Shake shake = new Shake();
		shake.parseData(datas);

		WaterDataDao waterDataDao = TcpServer.getWaterDataDao();
		shake.updateCreateTime(waterDataDao);

		String url = waterDataDao.selectUrl("url");
		String devId = waterDataDao.selectDevIdByIMEI(shake.getIMEI());
		String version = waterDataDao.selectVersionByImei(shake.getIMEI());	

		byte[] urlByte = url.getBytes();
		byte[] devIdByte = devId.getBytes();

		Log4jUtil.info("IMEI:" + shake.getIMEI() + ",showTemp:" + shake.getShowTemp());
		byte[] reply = { (byte) 0xFE, // 标识
				(byte) 01, // 协议版本号
				0x05, // 数据包长度
				0x31, // 命令字
				0x00, // 内容
				0x01, // 校验和
				0x0d, 0x0a };

		if ("2".equals(version)) {
			Log4jUtil.info("url:" + url);
			Log4jUtil.info("devId:" + devId);
			reply = addQRCode(reply, urlByte, devIdByte);
		}		
		byte crc = ByteUtility.calcCrc(reply);
		int arrayLenght = reply.length;
		reply[arrayLenght - 3] = crc;
		reply[2] = (byte)(arrayLenght-3);
		ByteBuf byteBufRep = Unpooled.buffer();
		byteBufRep.writeBytes(reply);

		Log4jUtil.info("send:" + ByteUtility.toHexStr(reply));
		channel.writeAndFlush(byteBufRep);// 向客户端发送消息
	}

	public void onMachineStateRosponse(byte[] datas) {
		Log4jUtil.info("32 -- 上报机器状态 ");
		Log4jUtil.info("channel:" + channel.toString());
		MachineState machineState = new MachineState();
		machineState.parseData(datas);

		WaterDataDao waterDataDao = TcpServer.getWaterDataDao();
		
		int insertResult = machineState.insertStatus(waterDataDao);
		int updateStatus = machineState.updateStatus(waterDataDao);
			
		byte[] reply = { (byte) 0xFE, // 标识
				(byte) 01, // 协议版本号
				0x05, // 数据包长度
				0x33, // 命令字
				0x00, // 内容
				0x01, // 校验和
				0x0d, 0x0a };
		byte crc = ByteUtility.calcCrc(reply);
		int arrayLenght = reply.length;
		reply[arrayLenght - 3] = crc;

		ByteBuf byteBufRep = Unpooled.buffer();
		byteBufRep.writeBytes(reply);

		Log4jUtil.info("send:" + ByteUtility.toHexStr(reply));
		channel.writeAndFlush(byteBufRep);// 向客户端发送消息
	}

	public void onWaterQualityRosponse(byte[] datas) {
		Log4jUtil.info("36 -- 水质数据 ");

		WaterQuality waterQuality = new WaterQuality();
		waterQuality.parseData(datas);

		WaterDataDao waterDataDao = TcpServer.getWaterDataDao();
		int insertResult = waterQuality.insertWaterQuality(waterDataDao);
		byte result;
		if (insertResult == 0) {
			result = 0x00;
		} else {
			result = 0x01;
		}
		byte[] reply = { (byte) 0xFE, // 标识
				(byte) 01, // 协议版本号
				0x05, // 数据包长度
				0x37, // 命令字
				result, // 内容
				0x01, // 校验和
				0x0d, 0x0a };

		byte crc = ByteUtility.calcCrc(reply);
		int arrayLenght = reply.length;
		reply[arrayLenght - 3] = crc;

		ByteBuf byteBufRep = Unpooled.buffer();
		byteBufRep.writeBytes(reply);

		Log4jUtil.info("send:" + ByteUtility.toHexStr(reply));
		channel.writeAndFlush(byteBufRep);// 向客户端发送消息
	}

	public void onTurnonWaterResponse(byte[] datas) {
		Log4jUtil.info("35 -- 出水应答 ");

		NotifyService notifyServer = TcpServer.getNotifyService();
		ControlMachineWater ctrMachineWater = new ControlMachineWater();
		ctrMachineWater.parseData(datas);

		Log4jUtil.info("CtrId:" + ctrMachineWater.getCtrId() + ",status:" + ctrMachineWater.getTurnOnWaterStatus());
		notifyServer.notifyTurnOnStatus(ctrMachineWater.getCtrId(), ctrMachineWater.getTurnOnWaterStatus());
	}

	public void onSetSwitchTimeResponse(byte[] datas) {
		Log4jUtil.info("39 -- 设置开关机时间应答 ");

		NotifyService notifyServer = TcpServer.getNotifyService();
		ControlMachineWater ctrMachineWater = new ControlMachineWater();
		ctrMachineWater.parseData(datas);

		Log4jUtil.info("CtrId:" + ctrMachineWater.getCtrId() + ",status:" + ctrMachineWater.getTurnOnWaterStatus());
		// notifyServer.notifySetSwitchTime(ctrMachineWater.getDevMac(),
		// ctrMachineWater.getTurnOnWaterStatus());
	}

	public void onModelRosponse(byte[] datas) {
		Log4jUtil.info("43 -- 机型设置应答 ");
		NotifyService notifyServer = TcpServer.getNotifyService();

		int result = datas[4];

		notifyServer.notifySetModel(result);
	}

	public void onRestoreDefaultsRosponse(byte[] datas) {
		Log4jUtil.info("45 -- 恢复默认值应答 ");
		NotifyService notifyServer = TcpServer.getNotifyService();

		int result = datas[4];

		notifyServer.notifySetModel(result);
	}

	public void onTimedStartAndCancelResponse(byte[] datas) {
		Log4jUtil.info("47 -- 定时启动和取消应答 ");
		NotifyService notifyServer = TcpServer.getNotifyService();

		int result = datas[4];

		notifyServer.notifyTimedStartAndCancel(result);
	}

	public void onChangeWaterResponse(byte[] datas) {
		Log4jUtil.info("49 -- 手动换水应答 ");
		NotifyService notifyServer = TcpServer.getNotifyService();

		int result = datas[4];

		notifyServer.notifyChangeWater(result);
	}

	public void onDisinfectionResponse(byte[] datas) {
		Log4jUtil.info("51 -- 手动消毒应答 ");
		NotifyService notifyServer = TcpServer.getNotifyService();

		int result = datas[4];

		notifyServer.notifyDisinfection(result);
	}

	public void onFlushingResponse(byte[] datas) {
		Log4jUtil.info("53 -- 手动冲洗应答 ");
		NotifyService notifyServer = TcpServer.getNotifyService();

		int result = datas[4];

		notifyServer.notifyFlushing(result);
	}

	public void onResetWaterVolumeResponse(byte[] datas) {
		Log4jUtil.info("55 -- 手动复位净水量应答");
		NotifyService notifyServer = TcpServer.getNotifyService();

		int result = datas[4];

		notifyServer.notifyResetWaterVolume(result);
	}

	public void onSetSysEnergyResponse(byte[] datas) {
		Log4jUtil.info("59 -- 系统参数设置（节能型）应答");
		NotifyService notifyServer = TcpServer.getNotifyService();

		int result = datas[4];

		notifyServer.notifySetSysEnergy(result);
	}

	public void onSetSysStepResponse(byte[] datas) {
		Log4jUtil.info("61 -- 系统参数设置（步进型）应答");
		NotifyService notifyServer = TcpServer.getNotifyService();

		int result = datas[4];

		notifyServer.notifySetSysStep(result);
	}

	public void onSetUserSettingResponse(byte[] datas) {
		Log4jUtil.info("63 -- 用户参数设置应答");
		NotifyService notifyServer = TcpServer.getNotifyService();

		int result = datas[4];

		notifyServer.notifySetUserSetting(result);
	}

	public void onSysSettingRosponse(byte[] datas) {
		Log4jUtil.info("64 -- 上报系统参数设置");

		NotifyService notifyServer = TcpServer.getNotifyService();
		byte ret = 0x00;
		SysSetting sysSetting = new SysSetting();
		DevSysConfig devSysConfig = sysSetting.parseData(datas);
		List<String> validate = validate(devSysConfig);
		if (validate.size() > 0) {
			ret = 0x01;
			for (String validateStr : validate) {
				Log4jUtil.info(validateStr);
			}
		}

		byte[] reply = { (byte) 0xFE, // 标识
				(byte) 01, // 协议版本号
				0x05, // 数据包长度
				0x65, // 命令字
				0x00, // 内容
				0x01, // 校验和
				0x0d, 0x0a };

		byte crc = ByteUtility.calcCrc(reply);
		int arrayLenght = reply.length;
		reply[arrayLenght - 3] = crc;

		// DevSysConfig devSysConfig = sysSetting;

		if (ret == 0x00) {
			int result = notifyServer.notifySetSysConfig(devSysConfig);
		}

		ByteBuf byteBufRep = Unpooled.buffer();
		byteBufRep.writeBytes(reply);

		Log4jUtil.info("send:" + ByteUtility.toHexStr(reply));
		channel.writeAndFlush(byteBufRep);// 向客户端发送消息
	}

	public void onUseSettingRosponse(byte[] datas) {
		Log4jUtil.info("66 -- 上报用户参数设置");
		Log4jUtil.info("channel:" + channel.toString());

		NotifyService notifyServer = TcpServer.getNotifyService();
		byte ret = 0x00;
		UserSetting userSetting = new UserSetting();
		DevUserConfig devUserConfig = userSetting.parseData(datas);
		List<String> validate = validate(devUserConfig);
		if (validate.size() > 0) {
			ret = 0x01;
			for (String validateStr : validate) {
				Log4jUtil.info(validateStr);
			}
		}

		byte[] reply = { (byte) 0xFE, // 标识
				(byte) 01, // 协议版本号
				0x05, // 数据包长度
				0x67, // 命令字
				0x00, // 内容
				0x01, // 校验和
				0x0d, 0x0a };

		byte crc = ByteUtility.calcCrc(reply);
		int arrayLenght = reply.length;
		reply[arrayLenght - 3] = crc;

		if (ret == 0x00) {
			int result = notifyServer.notifySetUserConfig(devUserConfig);
		}
		ByteBuf byteBufRep = Unpooled.buffer();
		byteBufRep.writeBytes(reply);

		Log4jUtil.info("send:" + ByteUtility.toHexStr(reply));
		channel.writeAndFlush(byteBufRep);// 向客户端发送消息
	}

	public void onUserSetRosponse(byte[] datas) {
		Log4jUtil.info("69 -- 获取用户参数");
		Log4jUtil.info("channel:" + channel.toString());
		GetUserSet getUserSet = new GetUserSet();
		getUserSet.parseData(datas);

		NotifyService notifyServer = TcpServer.getNotifyService();
		int result = notifyServer.notifyGetUserSetting(getUserSet);
	}

	public void onShowTempRosponse(byte[] datas) {
		Log4jUtil.info("70 -- 硬件上报显示温度");
		ByteBuf byteBuf = Unpooled.buffer();
		NotifyService notifyServer = TcpServer.getNotifyService();
		DevUserConfig userConfig = new DevUserConfig();

		byteBuf.writeBytes(datas);
		byteBuf.skipBytes(4);
		int showTemp = byteBuf.readByte() & 0xff;
		userConfig.setShowTemp(showTemp);
		String imei = ByteUtility.toHexStr(byteBuf.readBytes(8).array());
		userConfig.setImei(imei);
		int state = byteBuf.readByte() & 0xff;
		userConfig.setState(state);

		Log4jUtil.info("imei:" + imei + ",显示温度 :" + showTemp + ",机型(0:步进，1:节能):" + state);

		byte[] reply = { (byte) 0xFE, // 标识
				(byte) 01, // 协议版本号
				0x05, // 数据包长度
				// 0x71, // 命令字
				0x70, 0x00, // 内容
				0x01, // 校验和
				0x0d, 0x0a };

		byte crc = ByteUtility.calcCrc(reply);
		int arrayLenght = reply.length;
		reply[arrayLenght - 3] = crc;

		int result = notifyServer.notifyGetShowTemp(userConfig);

		ByteBuf byteBufRep = Unpooled.buffer();
		byteBufRep.writeBytes(reply);

		Log4jUtil.info("send:" + ByteUtility.toHexStr(reply));
		channel.writeAndFlush(byteBufRep);// 向客户端发送消息
	}

	public void onWaterAmountRosponse(byte[] datas) {
		Log4jUtil.info("72 -- 硬件上报水量");
		ByteBuf byteBuf = Unpooled.buffer();
		NotifyService notifyServer = TcpServer.getNotifyService();
		WaterBaseData water = new WaterBaseData();

		byteBuf.writeBytes(datas);
		byteBuf.skipBytes(4);
		int amount = byteBuf.readInt();
		water.setReportAmount(amount);
		String imei = ByteUtility.toHexStr(byteBuf.readBytes(8).array());
		water.setIMEI(imei);
		int state = byteBuf.readByte() & 0xff;

		Log4jUtil.info("imei:" + imei + ",水量 :" + amount + ",机型(0:步进，1：节能):");

		byte[] reply = { (byte) 0xFE, // 标识
				(byte) 01, // 协议版本号
				0x05, // 数据包长度
				// 0x73, // 命令字
				0x72, 0x00, // 内容
				0x01, // 校验和
				0x0d, 0x0a };

		byte crc = ByteUtility.calcCrc(reply);
		int arrayLenght = reply.length;
		reply[arrayLenght - 3] = crc;

		int result = notifyServer.notifyGetWaterAmount(water);

		ByteBuf byteBufRep = Unpooled.buffer();
		byteBufRep.writeBytes(reply);

		Log4jUtil.info("send:" + ByteUtility.toHexStr(reply));
		channel.writeAndFlush(byteBufRep);// 向客户端发送消息
	}
	
	public void onPassageInfoRosponse(byte[] datas) {
		Log4jUtil.info("75 -- 下发通道信息应答"+",result:" + datas[4]);
		NotifyService notifyServer = TcpServer.getNotifyService();

		int result = datas[4];
			
		notifyServer.notifySetPassageInfo(result);
	}
	public void onCtlMichineStatus(byte[] datas) {
		Log4jUtil.info("77 -- 手动开关机应答"+",result:" + datas[4]);
		NotifyService notifyServer = TcpServer.getNotifyService();

		int result = datas[4];
			
		notifyServer.notifyCtlMichineStatus(result);
	}
}
