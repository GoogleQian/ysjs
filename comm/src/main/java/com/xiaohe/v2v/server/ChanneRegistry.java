package com.xiaohe.v2v.server;

import io.netty.channel.Channel;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ChanneRegistry {
	private static Map<String, Channel> map = new ConcurrentHashMap<String, Channel>();
	private static Map<Channel, String> channelTboxId = new ConcurrentHashMap<Channel, String>();
	private static Map<String, String> versionInfo = new ConcurrentHashMap<String, String>();

	public static void add(String imei, Channel socketChannel, String version) {
		map.put(imei, socketChannel);
		versionInfo.put(imei, version);
		channelTboxId.put(socketChannel, imei);
	}

	public static Channel get(String imei) {
		return map.get(imei);
	}

	public static String getVersion(String imei) {
		return versionInfo.get(imei);
	}

	public static void remove(Channel socketChannel) {
		channelTboxId.remove(socketChannel);
		for (Map.Entry entry : map.entrySet()) {
			if (entry.getValue() == socketChannel) {
				String code = (String) entry.getKey();
				map.remove(entry.getKey());
			}
		}
	}

	public static void write(long branchId, byte[] data) {
		Channel channel = map.get(branchId);
		channel.writeAndFlush(data);
	}

}
