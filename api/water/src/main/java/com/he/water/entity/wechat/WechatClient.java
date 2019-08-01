package com.he.water.entity.wechat;


import com.alibaba.fastjson.JSONObject;
import com.he.water.entity.wechat.entity.*;
import com.he.water.utils.wechat.SignUtil;
import com.he.water.utils.wechat.WebUtils;
import com.he.water.utils.wechat.XmlUtil;
import org.apache.commons.codec.binary.Hex;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.InputStreamEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.CertPath;
import java.util.TreeMap;

/**
 * 微信交易客户端
 *
 * @author hzh
 * @date 2018/7/20
 */
public class WechatClient {
    private String app_id;
    private String mch_id;
    private String app_secret;
    private String trade_type;
    private String cert;
    private String secret;
    private static Logger logger = LoggerFactory.getLogger(WechatClient.class);

    public WechatClient(String app_id, String mch_id, String app_secret, String trade_type, String cert, String secret) {
        this.app_id = app_id;
        this.mch_id = mch_id;
        this.app_secret = app_secret;
        this.trade_type = trade_type;
        this.cert = cert;
        this.secret = secret;
    }


    /**
     * 统一下单,会自动签名和补上noce_str
     *
     * @return 下单后返回信息
     */
    public WechatUnifiedOrder.Response unifiedOrder(WechatUnifiedOrder unifiedOrder) {
        unifiedOrder.setAppid(this.app_id);
        unifiedOrder.setMch_id(this.mch_id);
        unifiedOrder.setTrade_type(this.trade_type);
        unifiedOrder.setNotify_url(WechatConfig.NOTIFY_PAY);
        unifiedOrder.setNonce_str(nonce_str(16));
        unifiedOrder.setSign(sign(SignUtil.bean2TreeMap(unifiedOrder)).toUpperCase());
        String tempXmlStr = XmlUtil.beanToXml(new ByteArrayOutputStream(), unifiedOrder);
        String requestXml = tempXmlStr != null ? tempXmlStr.substring(55) : "";
        logger.debug("xml转义后内容:" + requestXml);
        try {
            InputStreamEntity inputStreamEntity = new InputStreamEntity(
                    new ByteArrayInputStream(requestXml.getBytes("UTF-8")), ContentType.APPLICATION_XML);
            String resultXml = WebUtils.post(WechatConfig.UNIFIEDORDER_URL, inputStreamEntity);
            logger.debug("微信返回内容:" + resultXml);
            return resultXml != null
                    ? XmlUtil.xmlToBean(resultXml, WechatUnifiedOrder.Response.class)
                    : new WechatUnifiedOrder.Response("FAIL");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return new WechatUnifiedOrder.Response("FAIL");
    }

    /**
     * 订单查询
     *
     * @return 查询订单后返回信息
     */
    public WechatOrderQuery.Response orderQuery(WechatOrderQuery orderQuery) {
        orderQuery.setAppid(this.app_id);
        orderQuery.setMch_id(this.mch_id);
        orderQuery.setNonce_str(nonce_str(16));
        orderQuery.setSign(sign(SignUtil.bean2TreeMap(orderQuery)).toUpperCase());
        String tempXmlStr = XmlUtil.beanToXml(new ByteArrayOutputStream(), orderQuery);
        String requestXml = tempXmlStr != null ? tempXmlStr.substring(55) : "";
        logger.debug("xml转义后内容:" + requestXml);
        try {
            InputStreamEntity inputStreamEntity = new InputStreamEntity(
                    new ByteArrayInputStream(requestXml.getBytes("UTF-8")), ContentType.APPLICATION_XML);
            String resultXml = WebUtils.post(WechatConfig.ORDER_QUERY_URL, inputStreamEntity);
            logger.debug("微信返回内容:" + resultXml);
            return resultXml != null
                    ? XmlUtil.xmlToBean(resultXml, WechatOrderQuery.Response.class)
                    : new WechatOrderQuery.Response("FAIL");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return new WechatOrderQuery.Response("FAIL");
    }


    /**
     * 微信退货请求
     *
     * @param wechatRefund 退货请求参数
     * @return 请求返回内容
     */
    public WechatRefund.Response refund(WechatRefund wechatRefund) {
        logger.debug("进入方法refund");
        wechatRefund.setAppid(this.app_id);
        wechatRefund.setMch_id(this.mch_id);
        wechatRefund.setNonce_str(nonce_str(16));
        wechatRefund.setSign(sign(SignUtil.bean2TreeMap(wechatRefund)).toUpperCase());
        logger.debug("退货请求参数:" + JSONObject.toJSON(wechatRefund));
        String tempXmlStr = XmlUtil.beanToXml(new ByteArrayOutputStream(), wechatRefund);
        logger.info("tempXmlStr的值:" + tempXmlStr);
        String requestXml = tempXmlStr != null ? tempXmlStr.substring(55) : "";
        logger.info("requestXml的值:" + requestXml);
        try {
            InputStreamEntity inputStreamEntity = new InputStreamEntity(
                    new ByteArrayInputStream(requestXml.getBytes("UTF-8")), ContentType.APPLICATION_XML);
            logger.info("准备发送请求");
            String resultXml = WebUtils.post(this.cert, this.mch_id, WechatConfig.REFUND_URL, inputStreamEntity);
            logger.debug("微信退货返回内容:" + resultXml);
            return resultXml != null
                    ? XmlUtil.xmlToBean(resultXml, WechatRefund.Response.class)
                    : new WechatRefund.Response("FAIL");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new WechatRefund.Response("FAIL");
    }

    /**
     * 微信退款查询
     *
     * @param wechatRefundQuery 要查询的请求
     * @return 查询结果
     */
    public WechatRefundQuery.Response refundQuery(WechatRefundQuery wechatRefundQuery) {
        wechatRefundQuery.setAppid(this.app_id);
        wechatRefundQuery.setMch_id(this.mch_id);
        wechatRefundQuery.setNonce_str(nonce_str(16));
        wechatRefundQuery.setSign(sign(SignUtil.bean2TreeMap(wechatRefundQuery)).toUpperCase());
        String tempXmlStr = XmlUtil.beanToXml(new ByteArrayOutputStream(), wechatRefundQuery);
        String requestXml = tempXmlStr != null ? tempXmlStr.substring(55) : "";
        try {
            InputStreamEntity inputStreamEntity = new InputStreamEntity(
                    new ByteArrayInputStream(requestXml.getBytes("UTF-8")), ContentType.APPLICATION_XML);
            String resultXml = WebUtils.post(WechatConfig.REFUND_QUERY, inputStreamEntity);
            logger.debug("微信退货查询内容:" + resultXml);
            return resultXml != null
                    ? XmlUtil.xmlToBean(resultXml, WechatRefundQuery.Response.class)
                    : new WechatRefundQuery.Response("FAIL");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new WechatRefundQuery.Response("FAIL");
    }

    /**
     * 微信企业转账
     *
     * @param wechatTransfer 要查询的请求
     * @return 转账结果
     */
    public WechatTransfer.Response transfers(WechatTransfer wechatTransfer) {
        wechatTransfer.setMch_appid(this.app_id);
        wechatTransfer.setMchid(this.mch_id);
        wechatTransfer.setNonce_str(nonce_str(16));
        wechatTransfer.setSign(sign(SignUtil.bean2TreeMap(wechatTransfer)).toUpperCase());
        String tempXmlStr = XmlUtil.beanToXml(new ByteArrayOutputStream(), wechatTransfer);
        String requestXml = tempXmlStr != null ? tempXmlStr.substring(55) : "";
        try {
            InputStreamEntity inputStreamEntity = new InputStreamEntity(
                    new ByteArrayInputStream(requestXml.getBytes("UTF-8")), ContentType.APPLICATION_XML);
            String resultXml = WebUtils.post(this.cert, this.mch_id, WechatConfig.TRANSFER_RUL, inputStreamEntity);
            logger.info("微信转账内容:" + resultXml);
            return resultXml != null
                    ? XmlUtil.xmlToBean(resultXml, WechatTransfer.Response.class)
                    : new WechatTransfer.Response("FAIL");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new WechatTransfer.Response("FAIL");

    }

    /**
     * 对请求进行签名
     *
     * @param param 要签名的参数
     * @return
     */
    public String sign(TreeMap<String, ?> param) {
        String paramUrl = SignUtil.joinKeyValue(new TreeMap<String, Object>(param), null, "&key=" + this.app_secret, "&", true, "sign_type", "sign");
        logger.info("微信待签名串:" + paramUrl);
        try {
            // 得到一个信息摘要器
            MessageDigest digest = MessageDigest.getInstance("md5");
            byte[] result = digest.digest(paramUrl.getBytes());
            StringBuffer buffer = new StringBuffer();
            // 把每一个byte 做一个与运算 0xff;
            for (byte b : result) {
                // 与运算
                int number = b & 0xff;// 加盐
                String str = Integer.toHexString(number);
                if (str.length() == 1) {
                    buffer.append("0");
                }
                buffer.append(str);
            }

            logger.info("签名结果:" + buffer.toString());
            // 标准的md5加密后的结果
            return buffer.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return "";
        }
    }

    /**
     * 对回调函数进行签名
     *
     * @param param 要签名的参数
     * @return
     */

    public static String signReturn(TreeMap<String, ?> param, String appSecret) {
        String paramUrl = SignUtil.joinKeyValue(new TreeMap<String, Object>(param), null, "&key=" + appSecret, "&", true, "sign_type", "sign");
        logger.info("微信待签名串:" + paramUrl);
        try {
            // 得到一个信息摘要器
            MessageDigest digest = MessageDigest.getInstance("md5");
            byte[] result = digest.digest(paramUrl.getBytes());
            StringBuffer buffer = new StringBuffer();
            // 把每一个byte 做一个与运算 0xff;
            for (byte b : result) {
                // 与运算
                // 加盐
                int number = b & 0xff;
                String str = Integer.toHexString(number);
                if (str.length() == 1) {
                    buffer.append("0");
                }
                buffer.append(str);
            }

            logger.info("签名结果:" + buffer.toString());
            // 标准的md5加密后的结果
            return buffer.toString().toUpperCase();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return "";
        }
    }

    /**
     * 微信nonce_str生成算法
     *
     * @param bits 生成位数,选择64bit
     * @return 生成后的nonce_str
     */
    public String nonce_str(int bits) {
        final byte[] bytes;
        try {
            SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");
            if ((bits % 8) != 0) {
                throw new IllegalArgumentException("Size is not divisible by 8!");
            }
            bytes = new byte[bits / 8];
            secureRandom.nextBytes(bytes);
            return Hex.encodeHexString(bytes);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return System.currentTimeMillis() + "";
    }


//    public static String readRequestStr(HttpServletRequest request) {
//        BufferedReader reader = null;
//        StringBuilder sb = new StringBuilder();
//        try {
//            reader = new BufferedReader(new InputStreamReader(request.getInputStream(), "utf-8"));
//            String line = null;
//            while ((line = reader.readLine()) != null) {
//                sb.append(line);
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        } finally {
//            try {
//                if (null != reader) {
//                    reader.close();
//                }
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//        return sb.toString();
//    }


}
