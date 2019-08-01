package com.he.water.utils.wechat;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.security.SignatureException;
import java.util.*;

/**
 * @author
 */
public class SignUtil {

    /**
     * 连接Map键值对
     *
     * @param map              Map
     * @param prefix           前缀
     * @param suffix           后缀
     * @param separator        连接符
     * @param ignoreEmptyValue 忽略空值
     * @param ignoreKeys       忽略Key
     * @return 字符串
     */
    public static String joinKeyValue(Map<String, Object> map, String prefix, String suffix, String separator,
                                      boolean ignoreEmptyValue, String... ignoreKeys) {
        List<String> list = new ArrayList<String>();
        if (map != null) {
            for (Map.Entry<String, Object> entry : map.entrySet()) {
                String key = entry.getKey();
                String value = String.valueOf(entry.getValue());
                if (StringUtils.isNotEmpty(key) && !ArrayUtils.contains(ignoreKeys, key)
                        && (!ignoreEmptyValue || StringUtils.isNotEmpty(value))) {
                    list.add(key + "=" + (value != null ? value : ""));
                }
            }
        }
        return (prefix != null ? prefix : "") + StringUtils.join(list, separator) + (suffix != null ? suffix : "");
    }

    /**
     * 把request请求参数转换为Map<String,String>
     *
     * @param request 该请求
     * @return Map<String   ,   String>格式的参数
     */
    public static Map<String, String> request2Map(HttpServletRequest request) {
        Enumeration<String> names = request.getParameterNames();
        Map<String, String> resData = new HashMap<String, String>();
        while (names.hasMoreElements()) {
            String name = names.nextElement();
            resData.put(name, request.getParameter(name));
        }
        return resData;
    }

    /**
     * Bean转map
     *
     * @param bean 要转的bean
     * @return 返回一个TreeMap
     */
    public static TreeMap<String, String> bean2TreeMap(Object bean) {
        TreeMap<String, String> requestMap = new TreeMap<String, String>();
        Class<?> cls = bean.getClass();
        Field[] fields = cls.getDeclaredFields();
        try {
            for (int i = 0; i < fields.length; i++) {
                String key = fields[i].getName();
                fields[i].setAccessible(true);
                Object value = fields[i].get(bean);
                if ("sign".equals(key) || value == null || StringUtils.isEmpty(value.toString())) {
                    continue;
                }
                requestMap.put(key, value.toString());
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return requestMap;
    }


    static public String buildSignString(Map<String, String> params) {
        List<String> keys = new ArrayList<String>(params.size());

        for (String key : params.keySet()) {
            if ("sign".equals(key) || "sign_type".equals(key)) {
                continue;
            }
            if (StringUtils.isEmpty(params.get(key))) {
                continue;
            }
            keys.add(key);
        }

        Collections.sort(keys);

        StringBuilder buf = new StringBuilder();

        for (int i = 0; i < keys.size(); i++) {
            String key = keys.get(i);
            String value = params.get(key);

            if (i == keys.size() - 1) {// 拼接时，不包括最后一个&字符
                buf.append(key + "=" + value);
            } else {
                buf.append(key + "=" + value + "&");
            }
        }

        return buf.toString();

    }

    static public String signWithMd5(Map<String, String> params, String md5Key,
                                     String charset) {
        String prestr = buildSignString(params); // 把数组所有元素，按照“参数=参数值”的模式用“&”字符拼接成字符串
        return signWithMd5(prestr, md5Key, charset);
    }

    static public String signWithMd5(String originStr, String md5Key, String charset) {
        String text = originStr + md5Key;
        return DigestUtils.md5Hex(getContentBytes(text, charset)).toUpperCase();
    }

    /**
     * @param content
     * @param charset
     * @return
     * @throws SignatureException
     * @throws UnsupportedEncodingException
     */
    private static byte[] getContentBytes(String content, String charset) {
        if (charset == null || "".equals(charset)) {
            return content.getBytes();
        }
        try {
            return content.getBytes(charset);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("MD5签名过程中出现错误,指定的编码集不对,您目前指定的编码集是:"
                    + charset);
        }
    }

}


