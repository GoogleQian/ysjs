package com.he.water.entity.wechat;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.he.water.entity.CommonResult;
import com.he.water.entity.wechat.entity.*;
import com.he.water.entity.wxconfig.entity.WxConfig;
import com.he.water.utils.ConsantUtil;
import com.he.water.utils.wechat.SignUtil;
import com.he.water.utils.wechat.XmlUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.InputStream;
import java.util.TreeMap;


/**
 * 微信交易
 *
 * @author hzh
 * @date 2018/7/20
 */
public class WechatTrade {

    private static Logger logger = LoggerFactory.getLogger(WechatTrade.class);

    /**
     * 微信统一下单
     *
     * @param unifiedOrder 要下单的内容
     * @return 返回公众号下单请求需要内容
     */
    public static CommonResult unifiedOrderRequest(WechatUnifiedOrder unifiedOrder, CommonResult com, WxConfig wxConfig) {
        WechatClient instance = WechatConfig.getInstance(wxConfig.getAppId(), wxConfig.getMchId(), wxConfig.getAppSecret(), wxConfig.getTradeType(), wxConfig.getCert(), wxConfig.getSecret());
        WechatUnifiedOrder.Response response = instance.unifiedOrder(unifiedOrder);
        logger.info("微信统一下单:" + JSONObject.toJSONString(response));
        if (response.getResult_code().equals(WechatConfig.SUCCESS_REQUEST)) {
            TreeMap<String, Object> preparePay = new TreeMap<>();
            preparePay.put("appId", wxConfig.getAppId());
            preparePay.put("nonceStr", instance.nonce_str(16));
            preparePay.put("package", "prepay_id=" + response.getPrepay_id());
            preparePay.put("timeStamp", String.valueOf(System.currentTimeMillis() / 1000));
            preparePay.put("signType", "MD5");
            preparePay.put("paySign", instance.sign(preparePay));
            preparePay.put("prepay_id", response.getPrepay_id());
            preparePay.put("mch_id", wxConfig.getMchId());
            preparePay.put("totalFee", unifiedOrder.getTotal_fee());
            System.out.println(JSONObject.toJSON(preparePay));
            com.ok();
            com.setDatas(preparePay);
        } else {
            com.error(1, "下单失败");
        }
        return com;
    }

    /**
     * 微信统一下单
     * ORDER_QUERY_URL  order_query_url
     *
     * @param orderQuery 要查单的内容
     * @return 返回公众号查询订单内容
     */
    public static WechatOrderQuery.Response orderQueryRequest(WechatOrderQuery orderQuery, WxConfig wxConfig) {
        WechatClient instance = WechatConfig.getInstance(wxConfig.getAppId(), wxConfig.getMchId(), wxConfig.getAppSecret(), wxConfig.getTradeType(), wxConfig.getCert(), wxConfig.getSecret());
        WechatOrderQuery.Response response = instance.orderQuery(orderQuery);
        return response;
    }


    /**
     * 微信退款请求
     *
     * @param notice
     * @return 返回参数(同步接口, 直接返回), 只有return_code和result_code都成功则退款成功
     */
    public static WechatRefund.Response refundRequest(WechatPayNotify notice, WxConfig wxConfig) {
        WechatClient instance = WechatConfig.getInstance(wxConfig.getAppId(), wxConfig.getMchId(), wxConfig.getAppSecret(), wxConfig.getTradeType(), wxConfig.getCert(), wxConfig.getSecret());
        WechatRefund refund = new WechatRefund();
        refund.setTransaction_id(notice.getTransaction_id());
        refund.setRefund_fee(notice.getTotal_fee());
        refund.setTotal_fee(notice.getTotal_fee());
        refund.setOut_refund_no(ConsantUtil.OUT_REFUND_NO + System.currentTimeMillis());
        refund.setNotify_url(WechatConfig.REFUND_RUL);
        WechatRefund.Response response = instance.refund(refund);
        return response;
    }

    /**
     * 微信退款查询请求
     *
     * @param refund 退款请求参数
     * @return 返回参数(同步接口, 直接返回), 只有return_code和result_code都成功则查询成功
     */
    public WechatRefundQuery.Response refundQueryRequest(WechatRefundQuery refund, WxConfig wxConfig) {
        WechatClient instance = WechatConfig.getInstance(wxConfig.getAppId(), wxConfig.getMchId(), wxConfig.getAppSecret(), wxConfig.getTradeType(), wxConfig.getCert(), wxConfig.getSecret());

        WechatRefundQuery.Response response = instance.refundQuery(refund);
        return response;
    }

    public static WechatTransfer.Response transfersRequest(WechatTransfer wechatTransfer, WxConfig wxConfig) {
        WechatClient instance = WechatConfig.getInstance(wxConfig.getAppId(), wxConfig.getMchId(), wxConfig.getAppSecret(), wxConfig.getTradeType(), wxConfig.getCert(), wxConfig.getSecret());
        wechatTransfer.setPartner_trade_no("T" + System.currentTimeMillis());
        wechatTransfer.setAmount(1);
        wechatTransfer.setOpenid("o9LTyvv65_EB2k-7QVya5EvCRF5w");
        wechatTransfer.setCheck_name("NO_CHECK");
        wechatTransfer.setRe_user_name(null);
        wechatTransfer.setDesc("测试");
        wechatTransfer.setSpbill_create_ip("127.0.0.1");
        WechatTransfer.Response response = instance.transfers(wechatTransfer);
        return response;
    }

    /**
     * 微信回调验签
     *
     * @param request 回调请求
     * @return true成功
     */
    public static boolean verifyNotify(HttpServletRequest request, WxConfig wxConfig) {
        try {
            InputStream inputStream = request.getInputStream();
            WechatPayNotify notice = XmlUtil.xmlToBean(inputStream, WechatPayNotify.class);
            if (notice == null) {
                return false;
            }
            WechatClient instance = WechatConfig.getInstance(wxConfig.getAppId(), wxConfig.getMchId(), wxConfig.getAppSecret(), wxConfig.getTradeType(), wxConfig.getCert(), wxConfig.getSecret());
            logger.debug("微信回调参数:" + JSON.toJSONString(notice));
            String sign = instance.sign(SignUtil.bean2TreeMap(notice));
            boolean ischeck = sign.equals(notice.getSign());
            logger.debug("微信验签结果:" + ischeck);
            return ischeck;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 退款
     *
     * @param
     * @return
     */
    private static WechatRefund.Response refund(WxConfig wxConfig) {
        WechatClient instance = WechatConfig.getInstance(wxConfig.getAppId(), wxConfig.getMchId(), wxConfig.getAppSecret(), wxConfig.getTradeType(), wxConfig.getCert(), wxConfig.getSecret());
        WechatRefund wechatRefund = new WechatRefund();
        wechatRefund.setTransaction_id("4200000224201811193215055529");
        wechatRefund.setRefund_fee(40);
        wechatRefund.setTotal_fee(40);
        wechatRefund.setOut_refund_no(ConsantUtil.OUT_REFUND_NO + System.currentTimeMillis());
        wechatRefund.setNotify_url(WechatConfig.REFUND_RUL);
        return instance.refund(wechatRefund);
    }

}