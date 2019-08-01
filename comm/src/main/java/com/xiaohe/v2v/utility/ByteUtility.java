package com.xiaohe.v2v.utility;

import java.nio.ByteBuffer;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Stack;

public class ByteUtility {

	/**
	 * Hex字符串转byte
	 * 
	 * @param inHex
	 *            待转换的Hex字符串
	 * @return 转换后的byte
	 */
	public static byte hexToByte(String inHex) {
		return (byte) Integer.parseInt(inHex, 16);
	}

	/**
	 * hex字符串转byte数组
	 * 
	 * @param inHex
	 *            待转换的Hex字符串
	 * @return 转换后的byte数组结果
	 */
	public static byte[] hexToByteArray(String inHex) {
		int hexlen = inHex.length();
		byte[] result;
		if (hexlen % 2 == 1) {
			// 奇数
			hexlen++;
			result = new byte[(hexlen / 2)];
			inHex = "0" + inHex;
		} else {
			// 偶数
			result = new byte[(hexlen / 2)];
		}
		int j = 0;
		for (int i = 0; i < hexlen; i += 2) {
			result[j] = hexToByte(inHex.substring(i, i + 2));
			j++;
		}
		return result;
	}

	/**
	 * int转byte数组
	 * 
	 * @param bytes
	 * @return
	 */
	public static byte[] IntToByte(int num) {
		byte[] bytes = new byte[4];
		bytes[0] = (byte) ((num >> 24) & 0xff);
		bytes[1] = (byte) ((num >> 16) & 0xff);
		bytes[2] = (byte) ((num >> 8) & 0xff);
		bytes[3] = (byte) (num & 0xff);
		return bytes;
	}

	public static int check(byte[] msg) {
		byte a[] = msg;
		int i, sum = 0;
		for (i = 0; i < msg.length; i++)
			sum += a[i];// 将每个数相加
		if (sum > 0xff) {
			sum = ~sum;
			sum += 1;
		}
		sum = sum & 0xff;
		return sum;
	}

	/**
	 * 校验和
	 * 
	 * @param msg
	 *            需要计算校验和的byte数组
	 * @param length
	 *            校验和位数
	 * @return 计算出的校验和数组
	 */
	public static byte[] SumCheck(byte[] msg, int length) {
		long mSum = 0;
		byte[] mByte = new byte[length];

		/** 逐Byte添加位数和 */
		for (byte byteMsg : msg) {
			long mNum = ((long) byteMsg >= 0) ? (long) byteMsg : ((long) byteMsg + 256);
			mSum += mNum;
		} /** end of for (byte byteMsg : msg) */

		/** 位数和转化为Byte数组 */
		for (int liv_Count = 0; liv_Count < length; liv_Count++) {
			mByte[length - liv_Count - 1] = (byte) (mSum >> (liv_Count * 8) & 0xff);
		} /** end of for (int liv_Count = 0; liv_Count < length; liv_Count++) */

		return mByte;
	}

	public static String convert2HexBlank(byte[] array) {

		if (array == null) {
			return null;
		}

		StringBuffer buffer = new StringBuffer();

		for (int i = 0; i < array.length; i++) {
			buffer.append(String.format("%02x ", array[i] & 0xff));
		}
		return buffer.toString();

	};

	public static String toHexStr(byte[] array) {

		if (array == null) {
			return null;
		}

		StringBuffer buffer = new StringBuffer();

		for (int i = 0; i < array.length; i++) {
			buffer.append(String.format("%02x", array[i] & 0xff));
		}
		return buffer.toString();

	};

	public static String strTo16(String s) {
		String str = "";
		for (int i = 0; i < s.length(); i++) {
			int ch = (int) s.charAt(i);
			String s4 = Integer.toHexString(ch);
			str = str + s4;
		}
		return str;
	}

	public static String toHexStr1(byte array) {
		StringBuffer buffer = new StringBuffer();
		buffer.append(String.format("%02x", array & 0xff));
		return buffer.toString();
	};

	// 合并两个byte数组
	public static byte[] byteMerger(byte[] byte_1, byte[] byte_2) {
		byte[] byte_3 = new byte[byte_1.length + byte_2.length];
		System.arraycopy(byte_1, 0, byte_3, 0, byte_1.length);
		System.arraycopy(byte_2, 0, byte_3, byte_1.length, byte_2.length);
		return byte_3;
	}

	public static byte[] getBytes(int data) {
		byte[] bytes = new byte[4];
		bytes[0] = (byte) (data & 0xff);
		bytes[1] = (byte) ((data & 0xff00) >> 8);
		bytes[2] = (byte) ((data & 0xff0000) >> 16);
		bytes[3] = (byte) ((data & 0xff000000) >> 24);
		return bytes;
	}

	public static byte[] subBytes(byte[] src, int begin, int count) {
		byte[] bs = new byte[count];
		System.arraycopy(src, begin, bs, 0, count);
		return bs;
	}

	public static char byteToChar(byte[] b) {
		char c = (char) (((b[0] & 0xFF) << 8) | (b[1] & 0xFF));
		return c;
	}

	/**
	 * 将byte转换为一个长度为8的byte数组，数组每个值代表bit
	 */
	public static byte[] getBitArray(byte b) {
		byte[] array = new byte[8];
		for (int i = 7; i >= 0; i--) {
			array[i] = (byte) (b & 1);
			b = (byte) (b >> 1);
		}
		return array;
	}

	public static double bytes2Double(byte[] arr) {
		double value = 0;
		for (int i = 0; i < 4; i++) {
			if (i == 0)
				value += (arr[i] & 0x7f) << 24;
			else
				value += (arr[i] & 0xff) << (8 * (3 - i));
		}
		return value;
	}

	public static byte[] double2Bytes1(double d) {
		long l = 115767085;// 115767085
		byte[] byteRet = new byte[4];
		for (int i = 0; i < byteRet.length; i++) {
			byteRet[i] = (byte) ((l >> (8 * (3 - i)) & 0xff));
		}
		return byteRet;
	}

	public static double bytes2Double1(byte[] arr) {
		long value = 0;
		for (int i = 0; i < 8; i++) {
			value |= ((long) (arr[i] & 0xff)) << (8 * i);
		}
		return Double.longBitsToDouble(value);
	}

	static public String decodeWeirdDate(final byte a[]) {
		StringBuffer buf = new StringBuffer();
		for (byte b : a)
			buf.append(String.format("%02X", b));
		return buf.toString();
	}

	/**
	 * 通过byte数组取到short
	 *
	 * @param b
	 * @param index
	 *            第几位开始取
	 * @return
	 */
	public static short getShort(byte[] b, int index) {
		return (short) (((b[index + 1] << 8) & (0xff) | b[index + 0] & (0xff)));
	}

	public static String byte2string(byte[] array) {
		String str = String.format("%02d%02d%02d%02d%02d%02d", array[0], array[1], array[2], array[3], array[4],
				array[5]);
		String str2 = "20" + str;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		Calendar calendar = Calendar.getInstance();
		Date date = null;
		try {
			date = sdf.parse(str2);
			// System.out.println(entranceYear);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		calendar.setTime(date);
		// System.out.println(sdf.format(calendar.getTime()));
		calendar.add(Calendar.HOUR_OF_DAY, +8);
		// System.out.println(sdf.format(calendar.getTime()));
		return sdf.format(calendar.getTime());
	}

	/**
	 * 将byte[2]转换成short
	 * 
	 * @param b
	 * @param offset
	 * @return
	 */
	public static short byte2Short(byte[] b, int offset) {
		return (short) (((b[offset] & 0xff) << 8) | (b[offset + 1] & 0xff));
	}

	/**
	 * 注释：字节数组到int的转换！
	 * 
	 * @param b
	 * @return
	 */
	public static int byteToInt(byte[] b) {
		int s = 0;
		int s0 = b[0] & 0xff;// 最低位
		int s1 = b[1] & 0xff;
		int s2 = b[2] & 0xff;
		int s3 = b[3] & 0xff;
		s3 <<= 24;
		s2 <<= 16;
		s1 <<= 8;
		s = s0 | s1 | s2 | s3;
		return s;
	}

	/**
	 * 4位字节数组转换为整型
	 * 
	 * @param b
	 * @return
	 */
	public static int byte2Int(byte[] b) {
		int intValue = 0;
		for (int i = 0; i < b.length; i++) {
			intValue += (b[i] & 0xFF) << (8 * (3 - i));
		}
		return intValue;
	}

	/**
	 * add by jack
	 * 
	 * @param array
	 * @return
	 */
	public static byte[] convert(byte[] array) {

		if (array == null || (int) (array[0] & 0xff) != 0xe7) {
			return null;
		}

		Stack<Byte> st = new Stack<Byte>();

		st.push(new Byte((byte) 0xe7));

		for (int i = 1; i < array.length - 1; i++) {

			int value = (int) (array[i] & 0xff);

			switch (value) {
			case 0xe7:
				st.push(new Byte((byte) 0xe6));
				st.push(new Byte((byte) 0x02));
				break;
			case 0xe6:
				st.push(new Byte((byte) 0xe6));
				st.push(new Byte((byte) 0x01));
				break;
			default:
				st.push(new Byte(array[i]));
				break;
			}
		}

		st.push(new Byte((byte) 0xe7));
		int size = st.size();

		byte[] datas = new byte[size];
		for (int i = 0; i < size; i++) {
			datas[i] = st.get(i);
		}

		return datas;

	}

	/**
	 * 还原数据
	 * 
	 * @param array
	 * @return
	 */
	public static byte[] restore(byte[] array) {

		if (array == null || (int) (array[0] & 0xff) != 0xe7) {
			return null;
		}

		Stack<Byte> st = new Stack<Byte>();

		int arrayLength = array.length;

		for (int i = 0; i < arrayLength - 1; i++) {

			byte cur = array[i];
			byte next = array[i + 1];

			if (cur == (byte) 0xe6) {
				if (next == (byte) 0x02) {
					st.push((byte) 0xe7);
					i++; // 跳过0x02
				} else if (next == (byte) 0x01) {
					st.push((byte) 0xe6);
					i++; // 跳过0x01
				} else {
					st.push(array[i]);
				}
			} else {
				st.push(array[i]);
			}

		}

		// 最后一个是e7
		st.push((byte) array[arrayLength - 1]);

		int size = st.size();

		byte[] datas = new byte[size];
		for (int i = 0; i < size; i++) {
			datas[i] = st.get(i);
		}

		return datas;

	}

	public static byte[] toBytes(String str) {
		if (str == null || str.trim().equals("")) {
			return new byte[0];
		}

		byte[] bytes = new byte[str.length() / 2];
		for (int i = 0; i < str.length() / 2; i++) {
			String subStr = str.substring(i * 2, i * 2 + 2);
			bytes[i] = (byte) Integer.parseInt(subStr, 16);
		}

		return bytes;
	}

	public static byte calcCrc(byte[] array) {
		if (array == null || (int) (array[0] & 0xff) != 0xfe) {
			return 0;
		}
		int crc = 0;
		for (int i = 0; i < array.length - 3; i++) {
			crc ^= (int) array[i];
		}

		return (byte) crc;
	}

	public static byte reciveCalcCrc(byte[] array) {
		if (array == null || (int) (array[0] & 0xff) != 0xfe) {
			return 0;
		}
		int crc = 0;
		for (int i = 0; i < array.length - 1; i++) {
			crc ^= (int) array[i];
		}

		return (byte) crc;
	}
	
	public static String getTimeStr(byte[] by) {		
			String fir=String.format("%02x",by[0] & 0xff);
			String sec=String.format("%02x",by[1] & 0xff);
			String startTir=fir+sec;
			StringBuffer stringBuffer = new StringBuffer(startTir);
			return stringBuffer.insert(2,":").toString();	
		
	}
}
