package com.xiaohe.v2v.service;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;

import java.sql.Array;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.xiaohe.entity.PassageEntitys;
import com.xiaohe.hservice.HService;
import com.xiaohe.v2v.mapper.WaterDataDao;
import com.xiaohe.v2v.server.ChanneRegistry;
import com.xiaohe.v2v.server.MsgFrame;
import com.xiaohe.v2v.server.TcpServer;
import com.xiaohe.v2v.utility.ByteUtility;
import com.xiaohe.v2v.utility.Log4jUtil;

public class HServiceImpl implements HService {

	public int turnOnWater(int controlId, String imei, int type, int amount) {
		Log4jUtil.info("34 -- 控制机器出水");
		Channel channel = ChanneRegistry.get(imei);

		Log4jUtil.info("controlId=" + controlId + ",devMac=" + imei + ",type=" + type + ",amount=" + amount);
		// 设备未在线
		if (channel == null) {
			Log4jUtil.error("device not online");
			return 1;
		} else {
			Log4jUtil.info("channel:" + channel.toString());
		}
		// 水量
		short amt = (short) amount;
		byte[] amtByte = new byte[2];
		amtByte[0] = (byte) ((amt >> 8) & 0xff);
		amtByte[1] = (byte) (amt & 0xff);
		// 控制命令
		byte[] ctrByte = new byte[4];
		ctrByte = ByteUtility.IntToByte(controlId);

		byte[] reply = { (byte) 0xFE, // 标识
				(byte) 01, // 协议版本号
				0x0b, // 数据包长度
				0x34, // 命令字
				(byte) type, // 出水类型
				amtByte[0], amtByte[1], // 水量
				ctrByte[0], ctrByte[1], ctrByte[2], ctrByte[3], // 控制ID
				0x01, // 校验和
				0x0d, 0x0a };

		byte crc = ByteUtility.calcCrc(reply);
		int arrayLenght = reply.length;
		reply[arrayLenght - 3] = crc;

		ByteBuf byteBufRep = Unpooled.buffer();
		byteBufRep.writeBytes(reply);
		// context.writeAndFlush(byteBuf2);
		channel.writeAndFlush(byteBufRep);

		Log4jUtil.info("send:" + ByteUtility.toHexStr(reply));
		return 0;
	}

	public int controlWater(int controlId, String imei, int passage, int type) {
		Log4jUtil.info("67 -- 控制机器出水（新）");
		Channel channel = ChanneRegistry.get(imei);

		Log4jUtil.info("controlId=" + controlId + ",devMac=" + imei + ",passage=" + passage + ",type=" + type);
		// 设备未在线
		if (channel == null) {
			Log4jUtil.error("device not online");
			return 1;
		} else {
			Log4jUtil.info("channel:" + channel.toString());
		}
		// 控制命令
		byte[] ctrByte = new byte[4];
		ctrByte = ByteUtility.IntToByte(controlId);

		byte[] reply = { (byte) 0xFE, // 标识
				(byte) 01, // 协议版本号
				0x0b, // 数据包长度
				0x67, // 命令字
				(byte) passage, // 通道
				(byte) type, // 类型
				ctrByte[0], ctrByte[1], ctrByte[2], ctrByte[3], // 控制ID
				0x01, // 校验和
				0x0d, 0x0a };

		byte crc = ByteUtility.calcCrc(reply);
		int arrayLenght = reply.length;
		reply[arrayLenght - 3] = crc;

		ByteBuf byteBufRep = Unpooled.buffer();
		byteBufRep.writeBytes(reply);
		// context.writeAndFlush(byteBuf2);
		channel.writeAndFlush(byteBufRep);

		Log4jUtil.info("send:" + ByteUtility.toHexStr(reply));
		return 0;
	}

	@Override
	public int selectDevOnline(String imei) {
		Log4jUtil.info("查询设备是否在线");
		Log4jUtil.info("imei：" + imei);
		Channel channel = ChanneRegistry.get(imei);
		if (channel == null) {
			Log4jUtil.error("device not online");
			return 1;
		}
		Log4jUtil.info("device online");
		return 0;
	}

	public int setSwitchTime(String devMac, String on, String off) {
		Log4jUtil.info("38 -- 设置开关机时间");
		Log4jUtil.info("MAC：" + devMac + ",on:" + on + ",off:" + off);

		Channel channel = ChanneRegistry.get(devMac);
		if (channel == null) {
			Log4jUtil.error("device not online");
			return 1;
		}

		byte[] onByte = new byte[3];
		byte[] offByte = new byte[3];
		String[] onString = on.split(":");
		String[] offString = off.split(":");
		for (int i = 0; i < onString.length; i++) {
			onByte[i] = (byte) Integer.parseInt(onString[i]);
		}
		for (int i = 0; i < offByte.length; i++) {
			offByte[i] = (byte) Integer.parseInt(offString[i]);
		}
		byte[] macByte = ByteUtility.hexToByteArray(devMac);

		byte[] reply = { (byte) 0xFE, // 标识
				(byte) 01, // 协议版本号
				0x0b, // 数据包长度
				0x38, // 命令字
				// 0x00,0x01,0x6C,0x06,(byte)0xA6,0x29,
				macByte[0], macByte[1], macByte[2], macByte[3], macByte[4], macByte[5], // mac
				onByte[0], onByte[1], onByte[2], // 开机时间
				offByte[0], offByte[1], offByte[2], // 关机时间
				// 0x13,0x45,0x00,
				// 0x14,0x30,0x45,
				0x01, // 校验和
				0x0d, 0x0a };
		Log4jUtil.info("device online");

		ByteBuf byteBufRep = Unpooled.buffer();
		byteBufRep.writeBytes(reply);
		// context.writeAndFlush(byteBuf2);
		channel.writeAndFlush(byteBufRep);

		Log4jUtil.info("send:" + ByteUtility.toHexStr(reply));
		return 0;
	}

	public int setSwitchDay(String IMEI, int on, int off) {
		Log4jUtil.info("60 -- 设置机器开关机日");
		Log4jUtil.info("IMEI：" + IMEI + ",on:" + on + "off" + off);
		Channel channel = ChanneRegistry.get(IMEI);
		if (channel == null) {
			Log4jUtil.error("device not online");
			return 1;
		}

		byte[] reply = { (byte) 0xFE, // 标识
				(byte) 01, // 协议版本号
				0x6, // 数据包长度
				0x60, // 命令字
				(byte) on, (byte) off, 0x01, // 校验和
				0x0d, 0x0a };

		byte crc = ByteUtility.calcCrc(reply);
		int arrayLenght = reply.length;
		reply[arrayLenght - 3] = crc;

		ByteBuf byteBufRep = Unpooled.buffer();
		byteBufRep.writeBytes(reply);

		Log4jUtil.info("send:" + ByteUtility.toHexStr(reply));
		channel.writeAndFlush(byteBufRep);// 向客户端发送消息

		return 0;
	}

	public int setModel(String IMEI, int model) {
		Log4jUtil.info("42 -- 机型设置");
		Log4jUtil.info("IMEI：" + IMEI + ",model:" + model + "(1：节能，0：步进)");
		Channel channel = ChanneRegistry.get(IMEI);
		if (channel == null) {
			Log4jUtil.error("device not online");
			return 1;
		}

		byte[] reply = { (byte) 0xFE, // 标识
				(byte) 01, // 协议版本号
				0x5, // 数据包长度
				0x42, // 命令字
				(byte) model, 0x01, // 校验和
				0x0d, 0x0a };

		byte crc = ByteUtility.calcCrc(reply);
		int arrayLenght = reply.length;
		reply[arrayLenght - 3] = crc;

		ByteBuf byteBufRep = Unpooled.buffer();
		byteBufRep.writeBytes(reply);

		Log4jUtil.info("send:" + ByteUtility.toHexStr(reply));
		channel.writeAndFlush(byteBufRep);// 向客户端发送消息

		return 0;
	}

	public int restoreDefaults(String IMEI) {
		Log4jUtil.info("44 -- 恢复默认值");
		Log4jUtil.info("IMEI：" + IMEI);
		Channel channel = ChanneRegistry.get(IMEI);
		if (channel == null) {
			Log4jUtil.error("device not online");
			return 1;
		}

		byte[] reply = { (byte) 0xFE, // 标识
				(byte) 01, // 协议版本号
				0x5, // 数据包长度
				0x44, // 命令字
				0x00, 0x01, // 校验和
				0x0d, 0x0a };

		byte crc = ByteUtility.calcCrc(reply);
		int arrayLenght = reply.length;
		reply[arrayLenght - 3] = crc;

		ByteBuf byteBufRep = Unpooled.buffer();
		byteBufRep.writeBytes(reply);

		Log4jUtil.info("send:" + ByteUtility.toHexStr(reply));
		channel.writeAndFlush(byteBufRep);// 向客户端发送消息

		return 0;
	}

	public int timedStartAndCancel(String IMEI, int type) {
		Log4jUtil.info("46 -- 定时启动和取消");
		Log4jUtil.info("IMEI：" + IMEI + ",type:" + type);
		Channel channel = ChanneRegistry.get(IMEI);
		if (channel == null) {
			Log4jUtil.error("device not online");
			return 1;
		}

		byte[] reply = { (byte) 0xFE, // 标识
				(byte) 01, // 协议版本号
				0x5, // 数据包长度
				0x46, // 命令字
				(byte) type, 0x01, // 校验和
				0x0d, 0x0a };

		byte crc = ByteUtility.calcCrc(reply);
		int arrayLenght = reply.length;
		reply[arrayLenght - 3] = crc;

		ByteBuf byteBufRep = Unpooled.buffer();
		byteBufRep.writeBytes(reply);

		Log4jUtil.info("send:" + ByteUtility.toHexStr(reply));
		channel.writeAndFlush(byteBufRep);// 向客户端发送消息

		return 0;
	}

	public int changeWater(String IMEI, int type) {
		Log4jUtil.info("48 -- 手动换水");
		Log4jUtil.info("IMEI：" + IMEI + ",type:" + type);
		Channel channel = ChanneRegistry.get(IMEI);
		if (channel == null) {
			Log4jUtil.error("device not online");
			return 1;
		}

		byte[] reply = { (byte) 0xFE, // 标识
				(byte) 01, // 协议版本号
				0x5, // 数据包长度
				0x48, // 命令字
				(byte) type, 0x01, // 校验和
				0x0d, 0x0a };

		byte crc = ByteUtility.calcCrc(reply);
		int arrayLenght = reply.length;
		reply[arrayLenght - 3] = crc;

		ByteBuf byteBufRep = Unpooled.buffer();
		byteBufRep.writeBytes(reply);

		Log4jUtil.info("send:" + ByteUtility.toHexStr(reply));
		channel.writeAndFlush(byteBufRep);// 向客户端发送消息

		return 0;
	}

	public int disinfection(String IMEI, int type) {
		Log4jUtil.info("50 -- 手动消毒");
		Log4jUtil.info("IMEI：" + IMEI + ",type:" + type);
		Channel channel = ChanneRegistry.get(IMEI);
		if (channel == null) {
			Log4jUtil.error("device not online");
			return 1;
		}

		byte[] reply = { (byte) 0xFE, // 标识
				(byte) 01, // 协议版本号
				0x5, // 数据包长度
				0x50, // 命令字
				(byte) type, 0x01, // 校验和
				0x0d, 0x0a };

		byte crc = ByteUtility.calcCrc(reply);
		int arrayLenght = reply.length;
		reply[arrayLenght - 3] = crc;

		ByteBuf byteBufRep = Unpooled.buffer();
		byteBufRep.writeBytes(reply);

		Log4jUtil.info("send:" + ByteUtility.toHexStr(reply));
		channel.writeAndFlush(byteBufRep);// 向客户端发送消息

		return 0;
	}

	public int flushing(String IMEI, int type) {
		Log4jUtil.info("52 -- 手动冲洗");
		Log4jUtil.info("IMEI：" + IMEI + ",type:" + type);
		Channel channel = ChanneRegistry.get(IMEI);
		if (channel == null) {
			Log4jUtil.error("device not online");
			return 1;
		}

		byte[] reply = { (byte) 0xFE, // 标识
				(byte) 01, // 协议版本号
				0x5, // 数据包长度
				0x52, // 命令字
				(byte) type, 0x01, // 校验和
				0x0d, 0x0a };

		byte crc = ByteUtility.calcCrc(reply);
		int arrayLenght = reply.length;
		reply[arrayLenght - 3] = crc;

		ByteBuf byteBufRep = Unpooled.buffer();
		byteBufRep.writeBytes(reply);

		Log4jUtil.info("send:" + ByteUtility.toHexStr(reply));
		channel.writeAndFlush(byteBufRep);// 向客户端发送消息

		return 0;
	}

	public int resetWaterVolume(String IMEI) {
		Log4jUtil.info("56 -- 手动复位净水量");
		Log4jUtil.info("IMEI：" + IMEI);
		Channel channel = ChanneRegistry.get(IMEI);
		if (channel == null) {
			Log4jUtil.error("device not online");
			return 1;
		}

		byte[] reply = { (byte) 0xFE, // 标识
				(byte) 01, // 协议版本号
				0x5, // 数据包长度
				0x56, // 命令字
				0x00, 0x01, // 校验和
				0x0d, 0x0a };

		byte crc = ByteUtility.calcCrc(reply);
		int arrayLenght = reply.length;
		reply[arrayLenght - 3] = crc;

		ByteBuf byteBufRep = Unpooled.buffer();
		byteBufRep.writeBytes(reply);

		Log4jUtil.info("send:" + ByteUtility.toHexStr(reply));
		channel.writeAndFlush(byteBufRep);// 向客户端发送消息

		return 0;
	}

	public int setSysEnergy(String type, String IMEI, short stopTemp, int outType, int disinfectType, int changeType,
			int flushType, int filterType, int logoShows, short inTemp, short inHeight, int changeMethod,
			int checkWater, short delicacy) {
		Log4jUtil.info("58 -- 系统参数设置（节能型）");
		Log4jUtil.info("type:" + type + ",IMEI:" + IMEI + ",停止加热温度:" + stopTemp + ",出水方式:" + outType + ",消毒功能："
				+ disinfectType + ",换水功能:" + changeType + ",冲洗功能:" + flushType + ",滤芯管理功能：" + filterType + ",是否显示LOGO："
				+ logoShows + ",进水温差:" + inTemp + ",进水回差:" + inHeight + ",换水方式:" + changeMethod + ",水位一直检测："
				+ checkWater + ",水位检测灵敏度:" + delicacy);

		Channel channel = ChanneRegistry.get(IMEI);
		if (channel == null) {
			Log4jUtil.error("device not online");
			return 1;
		}
		if (!type.isEmpty()) {
			String[] strs = type.split(",");

			for (int i = 0; i < strs.length; i++) {
				byte[] re = null;
				if ("stopTemp".equals(strs[i])) {
					byte[] reply = { (byte) 0xFE, // 标识
							(byte) 01, // 协议版本号
							(byte) 6, // 数据包长度
							0x58, // 命令字
							0x01, (byte) stopTemp, // 停止加热温度
							0x01, // 校验和
							0x0d, 0x0a };
					re = reply;
				}
				if ("outType".equals(strs[i])) {
					byte[] reply = { (byte) 0xFE, // 标识
							(byte) 01, // 协议版本号
							(byte) 6, // 数据包长度
							0x58, // 命令字
							0x02, (byte) outType, // 出水方式
							0x01, // 校验和
							0x0d, 0x0a };
					re = reply;
				}
				if ("disinfectType".equals(strs[i])) {
					byte[] reply = { (byte) 0xFE, // 标识
							(byte) 01, // 协议版本号
							(byte) 6, // 数据包长度
							0x58, // 命令字
							0x03, (byte) disinfectType, // 消毒功能
							0x01, // 校验和
							0x0d, 0x0a };
					re = reply;
				}
				if ("changeType".equals(strs[i])) {
					byte[] reply = { (byte) 0xFE, // 标识
							(byte) 01, // 协议版本号
							(byte) 6, // 数据包长度
							0x58, // 命令字
							0x04, (byte) changeType, // 换水功能
							0x01, // 校验和
							0x0d, 0x0a };
					re = reply;
				}
				if ("flushType".equals(strs[i])) {
					byte[] reply = { (byte) 0xFE, // 标识
							(byte) 01, // 协议版本号
							(byte) 6, // 数据包长度
							0x58, // 命令字
							0x05, (byte) flushType, // 自冲洗功能
							0x01, // 校验和
							0x0d, 0x0a };
					re = reply;
				}
				if ("filterType".equals(strs[i])) {
					byte[] reply = { (byte) 0xFE, // 标识
							(byte) 01, // 协议版本号
							(byte) 6, // 数据包长度
							0x58, // 命令字
							0x06, (byte) filterType, // 滤芯管理功能
							0x01, // 校验和
							0x0d, 0x0a };
					re = reply;
				}
				if ("logoShows".equals(strs[i])) {
					byte[] reply = { (byte) 0xFE, // 标识
							(byte) 01, // 协议版本号
							(byte) 6, // 数据包长度
							0x58, // 命令字
							0x07, (byte) logoShows, // 厂商LOGO是否展示
							0x01, // 校验和
							0x0d, 0x0a };
					re = reply;
				}
				if ("inTemp".equals(strs[i])) {
					byte[] reply = { (byte) 0xFE, // 标识
							(byte) 01, // 协议版本号
							(byte) 6, // 数据包长度
							0x58, // 命令字
							0x08, (byte) inTemp, // 进水温差
							0x01, // 校验和
							0x0d, 0x0a };
					re = reply;
				}
				if ("inHeight".equals(strs[i])) {
					byte[] reply = { (byte) 0xFE, // 标识
							(byte) 01, // 协议版本号
							(byte) 6, // 数据包长度
							0x58, // 命令字
							0x09, (byte) inHeight, // 进水回差
							0x01, // 校验和
							0x0d, 0x0a };
					re = reply;
				}
				if ("changeMethod".equals(strs[i])) {
					byte[] reply = { (byte) 0xFE, // 标识
							(byte) 01, // 协议版本号
							(byte) 6, // 数据包长度
							0x58, // 命令字
							0x0a, (byte) changeMethod, // 换水方式
							0x01, // 校验和
							0x0d, 0x0a };
					re = reply;
				}
				if ("checkWater".equals(strs[i])) {
					byte[] reply = { (byte) 0xFE, // 标识
							(byte) 01, // 协议版本号
							(byte) 6, // 数据包长度
							0x58, // 命令字
							0x0b, (byte) checkWater, // 水位一直检测
							0x01, // 校验和
							0x0d, 0x0a };
					re = reply;
				}
				if ("delicacy".equals(strs[i])) {
					byte[] reply = { (byte) 0xFE, // 标识
							(byte) 01, // 协议版本号
							(byte) 6, // 数据包长度
							0x58, // 命令字
							0x0c, (byte) delicacy, // 水位检测灵敏度（L）
							0x01, // 校验和
							0x0d, 0x0a };
					re = reply;
				}

				byte crc = ByteUtility.calcCrc(re);
				int arrayLenght = re.length;
				re[arrayLenght - 3] = crc;

				ByteBuf byteBufRep = Unpooled.buffer();
				byteBufRep.writeBytes(re);

				Log4jUtil.info("send:" + ByteUtility.toHexStr(re));
				channel.writeAndFlush(byteBufRep);// 向客户端发送消息

				try {
					Log4jUtil.info("休眠200毫秒");
					Thread.sleep(200);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}

		}

		// byte[] reply = { (byte) 0xFE, // 标识
		// (byte) 01, // 协议版本号
		// (byte)28, // 数据包长度
		// 0x58, // 命令字
		// 0x01,(byte)stopTemp,
		// 0x02,(byte)outWaterType,
		// 0x03,(byte)disinfectionFUN,
		// 0x04,(byte)changeWaterFUN,
		// 0x05,(byte)flushFUN,
		// 0x06,(byte)filterMng,
		// 0x07,(byte)logo,
		// 0x08,(byte)inWaterTempDiff,
		// 0x09,(byte)inWaterReturnDiff,
		// 0x0a,(byte)changeType,
		// 0x0b,(byte)waterLevelDetected,
		// 0x0c,(byte)waterLevelSensitivity,
		// 0x01, // 校验和
		// 0x0d, 0x0a };
		//
		// byte crc = ByteUtility.calcCrc(reply);
		// int arrayLenght = reply.length;
		// reply[arrayLenght - 3] = crc;
		//
		// ByteBuf byteBufRep = Unpooled.buffer();
		// byteBufRep.writeBytes(reply);
		//
		// Log4jUtil.info("send:" + ByteUtility.toHexStr(reply));
		// channel.writeAndFlush(byteBufRep);// 向客户端发送消息
		return 0;
	}

	public int setSysStep(String type, String IMEI, short stopTemp, int emptyType, int flushType, int filterType,
			int logoShows, short inTemp, short keepTemp, short keepHeight, int speed, short delicacy) {
		Log4jUtil.info("60 -- 系统参数设置（步进型）");

		Log4jUtil.info("type:" + type + ",IMEI：" + IMEI + ",停止加热温度:" + stopTemp + ",排空功能：" + emptyType + ",冲洗功能："
				+ flushType + ",滤芯管理功能:" + filterType + ",是否显示LOGO：" + logoShows + ",进水温差:" + inTemp + ",保温温差："
				+ keepTemp + ",保温回差：" + keepHeight + ",进水速度:" + speed + ",水位检测灵敏度：" + delicacy);
		Channel channel = ChanneRegistry.get(IMEI);
		if (channel == null) {
			Log4jUtil.error("device not online");
			return 1;
		}
		if (!type.isEmpty()) {
			String[] strs = type.split(",");

			for (int i = 0; i < strs.length; i++) {
				byte[] re = null;
				if ("stopTemp".equals(strs[i])) {
					byte[] reply = { (byte) 0xFE, // 标识
							(byte) 01, // 协议版本号
							(byte) 6, // 数据包长度
							0x60, // 命令字
							0x01, (byte) stopTemp, // 停止加热温度
							0x01, // 校验和
							0x0d, 0x0a };
					re = reply;
				}
				if ("emptyType".equals(strs[i])) {
					byte[] reply = { (byte) 0xFE, // 标识
							(byte) 01, // 协议版本号
							(byte) 6, // 数据包长度
							0x60, // 命令字
							0x02, (byte) emptyType, // 排空功能
							0x01, // 校验和
							0x0d, 0x0a };
					re = reply;
				}
				if ("flushType".equals(strs[i])) {
					byte[] reply = { (byte) 0xFE, // 标识
							(byte) 01, // 协议版本号
							(byte) 6, // 数据包长度
							0x60, // 命令字
							0x03, (byte) flushType, // 自冲洗功能
							0x01, // 校验和
							0x0d, 0x0a };
					re = reply;
				}
				if ("filterType".equals(strs[i])) {
					byte[] reply = { (byte) 0xFE, // 标识
							(byte) 01, // 协议版本号
							(byte) 6, // 数据包长度
							0x60, // 命令字
							0x04, (byte) filterType, // 滤芯管理功能
							0x01, // 校验和
							0x0d, 0x0a };
					re = reply;
				}
				if ("logoShows".equals(strs[i])) {
					byte[] reply = { (byte) 0xFE, // 标识
							(byte) 01, // 协议版本号
							(byte) 6, // 数据包长度
							0x60, // 命令字
							0x05, (byte) logoShows, // 厂商LOGO是否展示
							0x01, // 校验和
							0x0d, 0x0a };
					re = reply;
				}
				if ("inTemp".equals(strs[i])) {
					byte[] reply = { (byte) 0xFE, // 标识
							(byte) 01, // 协议版本号
							(byte) 6, // 数据包长度
							0x60, // 命令字
							0x06, (byte) inTemp, // 进水温差
							0x01, // 校验和
							0x0d, 0x0a };
					re = reply;
				}
				if ("keepTemp".equals(strs[i])) {
					byte[] reply = { (byte) 0xFE, // 标识
							(byte) 01, // 协议版本号
							(byte) 6, // 数据包长度
							0x60, // 命令字
							0x07, (byte) keepTemp, // 保温温差（步进）
							0x01, // 校验和
							0x0d, 0x0a };
					re = reply;
				}
				if ("keepHeight".equals(strs[i])) {
					byte[] reply = { (byte) 0xFE, // 标识
							(byte) 01, // 协议版本号
							(byte) 6, // 数据包长度
							0x60, // 命令字
							0x08, (byte) keepHeight, // 保温回差（步进）
							0x01, // 校验和
							0x0d, 0x0a };
					re = reply;
				}
				if ("speed".equals(strs[i])) {
					byte[] reply = { (byte) 0xFE, // 标识
							(byte) 01, // 协议版本号
							(byte) 6, // 数据包长度
							0x60, // 命令字
							0x09, (byte) speed, // 进水速度（步进）
							0x01, // 校验和
							0x0d, 0x0a };
					re = reply;
				}
				if ("delicacy".equals(strs[i])) {
					byte[] reply = { (byte) 0xFE, // 标识
							(byte) 01, // 协议版本号
							(byte) 6, // 数据包长度
							0x60, // 命令字
							0x0a, (byte) delicacy, // 水位检测灵敏度（L）
							0x01, // 校验和
							0x0d, 0x0a };
					re = reply;
				}
				byte crc = ByteUtility.calcCrc(re);
				int arrayLenght = re.length;
				re[arrayLenght - 3] = crc;

				ByteBuf byteBufRep = Unpooled.buffer();
				byteBufRep.writeBytes(re);

				Log4jUtil.info("send:" + ByteUtility.toHexStr(re));
				channel.writeAndFlush(byteBufRep);// 向客户端发送消息
				try {
					Log4jUtil.info("休眠200毫秒");
					Thread.sleep(200);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}

			// byte[] reply = { (byte) 0xFE, // 标识
			// (byte) 01, // 协议版本号
			// (byte)24, // 数据包长度
			// 0x60, // 命令字
			// 0x01,(byte)stopTemp,
			// 0x02,(byte)emptyingFUN,
			// 0x03,(byte)flushFUN,
			// 0x04,(byte)filterMng,
			// 0x05,(byte)logo,
			// 0x06,(byte)inWaterTempDiff,
			// 0x07,(byte)insulationTempDiff,
			// 0x08,(byte)insulationReturnDiff,
			// 0x09,(byte)waterInletSpeed,
			// (byte)10,(byte)waterLevelSensitivity,
			// 0x01, // 校验和
			// 0x0d, 0x0a };

		}
		return 0;
	}

	public int setUserSetting(String type, String IMEI, int day, int hour, int min, int startDay, int stopDay,
			String startOne, String stopOne, String startTwo, String stopTwo, String startThr, String stopThr,
			String cleanOne, String cleanTwo, String cleanThr, String changeTime, int hangeTime, int changeCycle,
			int filterLife, String flushTime, int flushDuration, int cleanDuration) {
		Log4jUtil.info("62 -- 用户参数设置");

		Log4jUtil.info("type:" + type + ",IMEI：" + IMEI + ",当前日:" + day + ",当前时:" + hour + ",当前分:" + min + ",开机日:"
				+ startDay + ",关机日:" + stopDay + ",定时开机1时间:" + startOne + ",定时关机1时间:" + stopOne + ",定时开机2时间:" + startTwo
				+ ",定时关机2时间:" + stopTwo + ",定时开机3时间:" + startThr + ",定时关机3时间:" + stopThr + ",消毒1时间:" + cleanOne
				+ ",消毒2时间:" + cleanTwo + ",消毒3时间:" + cleanThr + ",换水时间:" + changeTime + ",换水时长:" + hangeTime + ",换水周期:"
				+ changeCycle + ",滤芯寿命:" + filterLife + ",定时冲洗时间" + flushTime + ",冲洗时长" + flushDuration + ",消毒时长"
				+ cleanDuration);
		Channel channel = ChanneRegistry.get(IMEI);
		if (channel == null) {
			Log4jUtil.error("device not online");
			return 1;
		}
		if (!type.isEmpty()) {
			String[] strs = type.split(",");

			byte[] firOpenTimeBytes = getTimeBytes(startOne);
			byte[] firCloseTimeBytes = getTimeBytes(stopOne);
			byte[] secOpenTimeBytes = getTimeBytes(startTwo);
			byte[] secCloseTimeBytes = getTimeBytes(stopTwo);
			byte[] tirOpenTimeBytes = getTimeBytes(startThr);
			byte[] tirCloseTimeBytes = getTimeBytes(stopThr);
			byte[] firDisTimeBytes = getTimeBytes(cleanOne);
			byte[] secDisTimeBytes = getTimeBytes(cleanTwo);
			byte[] tirDisTimeBytes = getTimeBytes(cleanThr);
			byte[] changeWaterTimeBytes = getTimeBytes(changeTime);
			byte[] flushTimeTimeBytes = getTimeBytes(flushTime);

			byte[] cleanDurBytes = new byte[2];
			cleanDurBytes[0] = (byte) ((cleanDuration >> 8) & 0xff);
			cleanDurBytes[1] = (byte) (cleanDuration & 0xff);

			byte[] hangeTimeBytes = new byte[2];
			hangeTimeBytes[0] = (byte) ((hangeTime >> 8) & 0xff);
			hangeTimeBytes[1] = (byte) (hangeTime & 0xff);

			for (int i = 0; i < strs.length; i++) {
				byte[] re = null;
				if ("day".equals(strs[i])) {
					byte[] reply = { (byte) 0xFE, // 标识
							(byte) 01, // 协议版本号
							(byte) 6, // 数据包长度
							0x62, // 命令字
							0x01, (byte) day, 0x01, // 校验和
							0x0d, 0x0a };
					re = reply;
				}
				if ("hours".equals(strs[i])) {
					byte[] reply = { (byte) 0xFE, // 标识
							(byte) 01, // 协议版本号
							(byte) 6, // 数据包长度
							0x62, // 命令字
							0x02, (byte) hour, 0x01, // 校验和
							0x0d, 0x0a };
					re = reply;
				}
				if ("minutes".equals(strs[i])) {
					byte[] reply = { (byte) 0xFE, // 标识
							(byte) 01, // 协议版本号
							(byte) 6, // 数据包长度
							0x62, // 命令字
							0x03, (byte) min, 0x01, // 校验和
							0x0d, 0x0a };
					re = reply;
				}
				if ("startDay".equals(strs[i])) {
					byte[] reply = { (byte) 0xFE, // 标识
							(byte) 01, // 协议版本号
							(byte) 6, // 数据包长度
							0x62, // 命令字
							0x04, (byte) startDay, // 开机日
							0x01, // 校验和
							0x0d, 0x0a };
					re = reply;
				}
				if ("stopDay".equals(strs[i])) {
					byte[] reply = { (byte) 0xFE, // 标识
							(byte) 01, // 协议版本号
							(byte) 6, // 数据包长度
							0x62, // 命令字
							0x05, (byte) stopDay, // 关机日
							0x01, // 校验和
							0x0d, 0x0a };
					re = reply;
				}
				if ("startOne".equals(strs[i])) {
					byte[] reply = { (byte) 0xFE, // 标识
							(byte) 01, // 协议版本号
							(byte) 7, // 数据包长度
							0x62, // 命令字
							0x06, firOpenTimeBytes[0], firOpenTimeBytes[1], // 定时第一段开时间
							0x01, // 校验和
							0x0d, 0x0a };
					re = reply;
				}
				if ("startTwo".equals(strs[i])) {
					byte[] reply = { (byte) 0xFE, // 标识
							(byte) 01, // 协议版本号
							(byte) 7, // 数据包长度
							0x62, // 命令字
							0x08, secOpenTimeBytes[0], secOpenTimeBytes[1], // 定时第二段开时间
							0x01, // 校验和
							0x0d, 0x0a };
					re = reply;
				}
				if ("startThr".equals(strs[i])) {
					byte[] reply = { (byte) 0xFE, // 标识
							(byte) 01, // 协议版本号
							(byte) 7, // 数据包长度
							0x62, // 命令字
							(byte) 10, tirOpenTimeBytes[0], tirOpenTimeBytes[1], // 第三次开机时间
							0x01, // 校验和
							0x0d, 0x0a };
					re = reply;
				}
				if ("stopOne".equals(strs[i])) {
					byte[] reply = { (byte) 0xFE, // 标识
							(byte) 01, // 协议版本号
							(byte) 7, // 数据包长度
							0x62, // 命令字
							0x07, firCloseTimeBytes[0], firCloseTimeBytes[1], // 第一次关机时间
							0x01, // 校验和
							0x0d, 0x0a };
					re = reply;
				}
				if ("stopTwo".equals(strs[i])) {
					byte[] reply = { (byte) 0xFE, // 标识
							(byte) 01, // 协议版本号
							(byte) 7, // 数据包长度
							0x62, // 命令字
							0x09, secCloseTimeBytes[0], secCloseTimeBytes[1], // 第二次关机时间
							0x01, // 校验和
							0x0d, 0x0a };
					re = reply;
				}
				if ("stopThr".equals(strs[i])) {
					byte[] reply = { (byte) 0xFE, // 标识
							(byte) 01, // 协议版本号
							(byte) 7, // 数据包长度
							0x62, // 命令字
							(byte) 11, tirCloseTimeBytes[0], tirCloseTimeBytes[1], // 第三次关机时间
							0x01, // 校验和
							0x0d, 0x0a };
					re = reply;
				}
				if ("cleanOne".equals(strs[i])) {
					byte[] reply = { (byte) 0xFE, // 标识
							(byte) 01, // 协议版本号
							(byte) 7, // 数据包长度
							0x62, // 命令字
							(byte) 12, firDisTimeBytes[0], firDisTimeBytes[1], // 第一次消毒时间
							0x01, // 校验和
							0x0d, 0x0a };
					re = reply;
				}
				if ("cleanTwo".equals(strs[i])) {
					byte[] reply = { (byte) 0xFE, // 标识
							(byte) 01, // 协议版本号
							(byte) 7, // 数据包长度
							0x62, // 命令字
							(byte) 13, secDisTimeBytes[0], secDisTimeBytes[1], // 第二次消毒时间
							0x01, // 校验和
							0x0d, 0x0a };
					re = reply;
				}
				if ("cleanThr".equals(strs[i])) {
					byte[] reply = { (byte) 0xFE, // 标识
							(byte) 01, // 协议版本号
							(byte) 7, // 数据包长度
							0x62, // 命令字
							(byte) 14, tirDisTimeBytes[0], tirDisTimeBytes[1], // 第三次消毒时间
							0x01, // 校验和
							0x0d, 0x0a };
					re = reply;
				}
				if ("changeTime".equals(strs[i])) {
					byte[] reply = { (byte) 0xFE, // 标识
							(byte) 01, // 协议版本号
							(byte) 7, // 数据包长度
							0x62, // 命令字
							(byte) 15, changeWaterTimeBytes[0], changeWaterTimeBytes[1], // 换水时间
							0x01, // 校验和
							0x0d, 0x0a };
					re = reply;
				}
				if ("hangeTime".equals(strs[i])) {
					byte[] reply = { (byte) 0xFE, // 标识
							(byte) 01, // 协议版本号
							(byte) 7, // 数据包长度
							0x62, // 命令字
							(byte) 16, hangeTimeBytes[0], hangeTimeBytes[1], // 换水时长
							0x01, // 校验和
							0x0d, 0x0a };
					re = reply;
				}
				if ("changeCycle".equals(strs[i])) {
					byte[] reply = { (byte) 0xFE, // 标识
							(byte) 01, // 协议版本号
							(byte) 6, // 数据包长度
							0x62, // 命令字
							(byte) 17, (byte) changeCycle, // 换水周期
							0x01, // 校验和
							0x0d, 0x0a };
					re = reply;
				}
				if ("filterLife".equals(strs[i])) {
					byte[] reply = { (byte) 0xFE, // 标识
							(byte) 01, // 协议版本号
							(byte) 6, // 数据包长度
							0x62, // 命令字
							(byte) 18, (byte) filterLife, // 滤芯寿命
							0x01, // 校验和
							0x0d, 0x0a };
					re = reply;
				}
				if ("flushTime".equals(strs[i])) {
					byte[] reply = { (byte) 0xFE, // 标识
							(byte) 01, // 协议版本号
							(byte) 7, // 数据包长度
							0x62, // 命令字
							(byte) 19, flushTimeTimeBytes[0], flushTimeTimeBytes[1], // 定时冲洗时间
							0x01, // 校验和
							0x0d, 0x0a };
					re = reply;
				}

				if ("flushDuration".equals(strs[i])) {
					byte[] reply = { (byte) 0xFE, // 标识
							(byte) 01, // 协议版本号
							(byte) 6, // 数据包长度
							0x62, // 命令字
							(byte) 20, (byte) flushDuration, // 冲洗时长
							0x01, // 校验和
							0x0d, 0x0a };
					re = reply;
				}
				if ("cleanDuration".equals(strs[i])) {
					byte[] reply = { (byte) 0xFE, // 标识
							(byte) 01, // 协议版本号
							(byte) 7, // 数据包长度
							0x62, // 命令字
							(byte) 21, cleanDurBytes[0], cleanDurBytes[1], // 消毒时长
							0x01, // 校验和
							0x0d, 0x0a };
					re = reply;
				}
				if (re == null) {
					return 1;
				}
				byte crc = ByteUtility.calcCrc(re);
				int arrayLenght = re.length;
				re[arrayLenght - 3] = crc;

				ByteBuf byteBufRep = Unpooled.buffer();
				byteBufRep.writeBytes(re);

				Log4jUtil.info("send:" + ByteUtility.toHexStr(re));
				channel.writeAndFlush(byteBufRep);// 向客户端发送消息
				try {
					Log4jUtil.info("休眠200毫秒");
					Thread.sleep(200);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}

			// byte[] reply = { (byte) 0xFE, // 标识
			// (byte) 01, // 协议版本号
			// (byte)50, // 数据包长度
			// 0x62, // 命令字
			// 0x01,(byte)day,0x02,(byte)hour,0x03,(byte)min,//当前日、时、分
			// 0x04,(byte)startDay,0x05,(byte)stopDay,//开关机日
			// 0x06,firOpenTimeBytes[0],firOpenTimeBytes[1],//第一次开机时间
			// 0x07,firCloseTimeBytes[0],firCloseTimeBytes[1],//第一次关机时间
			// 0x08,secOpenTimeBytes[0],secOpenTimeBytes[1],//第二次开机时间
			// 0x09,secCloseTimeBytes[0],secCloseTimeBytes[1],//第二次关机时间
			// (byte)10,tirOpenTimeBytes[0],tirOpenTimeBytes[1],//第三次开机时间
			// (byte)11,tirCloseTimeBytes[0],tirCloseTimeBytes[1],//第三次关机时间
			// (byte)12,firDisTimeBytes[0],firDisTimeBytes[1],//第一次消毒时间
			// (byte)13,secDisTimeBytes[0],secDisTimeBytes[1],//第二次消毒时间
			// (byte)14,tirDisTimeBytes[0],tirDisTimeBytes[1],//第三次消毒时间
			// (byte)15,changeWaterTimeBytes[0],changeWaterTimeBytes[1],//换水时间
			// (byte)16,(byte)hangeTime,0x17,(byte)changeCycle,//换水时长和周期
			// (byte)18,(byte)filterLife,//滤芯寿命
			// 0x01, // 校验和
			// 0x0d, 0x0a
			// };
			//
		}

		return 0;
	}

	public int getUserSetting(int type, String imei) {
		Log4jUtil.info("68 -- 获取用户参数");
		Log4jUtil.info("IMEI:" + imei + ",type:" + type);
		Channel channel = ChanneRegistry.get(imei);
		if (channel == null) {
			Log4jUtil.error("device not online");
			return 1;
		}

		byte[] reply = { (byte) 0xFE, // 标识
				(byte) 01, // 协议版本号
				(byte) 5, // 数据包长度
				// 0x68, // 命令字
				0x67, (byte) type, 0x01, // 校验和
				0x0d, 0x0a };

		byte crc = ByteUtility.calcCrc(reply);
		int arrayLenght = reply.length;
		reply[arrayLenght - 3] = crc;

		ByteBuf byteBufRep = Unpooled.buffer();
		byteBufRep.writeBytes(reply);

		Log4jUtil.info("send:" + ByteUtility.toHexStr(reply));
		channel.writeAndFlush(byteBufRep);// 向客户端发送消息

		return 0;
	}

	public byte[] getTimeBytes(String str) {
		byte[] bytes = new byte[3];
		String[] strs = str.split(":");
		for (int i = 0; i < strs.length; i++) {
			bytes[i] = (byte) Integer.parseInt(strs[i]);
		}
		return bytes;
	}

	public int setPassageInfo(String imei, List<PassageEntitys> listInfo) {
		Log4jUtil.info("74 -- 下发通道信息");

		WaterDataDao waterDataDao = TcpServer.getWaterDataDao();
		String version = waterDataDao.selectVersionByImei(imei);
		Log4jUtil.info("imei:" + imei + ",version:" + version);

		Channel channel = ChanneRegistry.get(imei);
		if (channel == null) {
			Log4jUtil.error("device not online");
			return 1;
		}

		Map<Integer, Integer> psTypeList = new ConcurrentHashMap<Integer, Integer>();
		psTypeList.put(1, 0);
		psTypeList.put(2, 0);
		psTypeList.put(3, 0);
		psTypeList.put(4, 0);
		Map<Integer, Integer> psPulseNumList = new ConcurrentHashMap<Integer, Integer>();
		psPulseNumList.put(1, 0);
		psPulseNumList.put(2, 0);
		psPulseNumList.put(3, 0);
		psPulseNumList.put(4, 0);

		for (PassageEntitys ps : listInfo) {
			psTypeList.put(ps.getPassageNo(), ps.getPassageNo());
			psPulseNumList.put(ps.getPassageNo(), ps.getPulseNum());
		}
		if ("2".equals(version)) {
			byte[] reply = { (byte) 0xFE, // 标识
					(byte) 01, // 协议版本号
					(byte) 0x16, // 数据包长度
					0x74,
					// 通道1类型脉冲
					(byte) (int) psTypeList.get(1), (byte) ((psPulseNumList.get(1) >> 8) & 0xff),
					(byte) (psPulseNumList.get(1) & 0xff),
					// 通道2类型脉冲
					(byte) (int) psTypeList.get(2), (byte) ((psPulseNumList.get(2) >> 8) & 0xff),
					(byte) (psPulseNumList.get(2) & 0xff),
					// 通道3类型脉冲
					(byte) (int) psTypeList.get(3), (byte) ((psPulseNumList.get(3) >> 8) & 0xff),
					(byte) (psPulseNumList.get(3) & 0xff),
					// 通道4类型脉冲
					(byte) (int) psTypeList.get(4), (byte) ((psPulseNumList.get(4) >> 8) & 0xff),
					(byte) (psPulseNumList.get(4) & 0xff), 0x01, // 校验和
					0x0d, 0x0a };
			byte crc = ByteUtility.calcCrc(reply);
			int arrayLenght = reply.length;
			reply[arrayLenght - 3] = crc;

			ByteBuf byteBufRep = Unpooled.buffer();
			byteBufRep.writeBytes(reply);

			Log4jUtil.info("send:" + ByteUtility.toHexStr(reply));
			channel.writeAndFlush(byteBufRep);// 向客户端发送消息
		} else {
			byte[] reply = { (byte) 0xFE, // 标识
					(byte) 01, // 协议版本号
					(byte) 0x16, // 数据包长度
					0x74,
					// 通道1类型脉冲
					(byte) (int) psTypeList.get(1),
					// 通道2类型脉冲
					(byte) (int) psTypeList.get(2),
					// 通道3类型脉冲
					(byte) (int) psTypeList.get(3),
					// 通道4类型脉冲
					(byte) (int) psTypeList.get(4), 0x01, // 校验和
					0x0d, 0x0a };
			byte crc = ByteUtility.calcCrc(reply);
			int arrayLenght = reply.length;
			reply[arrayLenght - 3] = crc;

			ByteBuf byteBufRep = Unpooled.buffer();
			byteBufRep.writeBytes(reply);

			Log4jUtil.info("send:" + ByteUtility.toHexStr(reply));
			channel.writeAndFlush(byteBufRep);// 向客户端发送消息
		}

		return 0;
	}

	public int setCtlMachineStatus(String imei, int type) {
		Log4jUtil.info("76 -- 手动开关机");

		WaterDataDao waterDataDao = TcpServer.getWaterDataDao();
		String version = waterDataDao.selectVersionByImei(imei);
		Log4jUtil.info("imei:" + imei + ",type" + type + ",version:" + version);

		Channel channel = ChanneRegistry.get(imei);
		if (channel == null) {
			Log4jUtil.error("device not online");
			return 1;
		}
		
		if ("2".equals(version)) {
			byte[] reply = { (byte) 0xFE, // 标识
					(byte) 01, // 协议版本号
					(byte) 0x05, // 数据包长度
					0x76,
					(byte)type,
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
		

		return 0;
	}

}
