package com.he.water.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.he.water.entity.*;
import com.he.water.entity.wechat.WechatConfig;
import com.he.water.entity.wechat.entity.WechatPayNotify;
import com.he.water.entity.wechat.entity.WechatRefundNotify;
import com.he.water.entity.wxconfig.entity.WxConfig;
import com.he.water.entity.wxconfig.service.WxConfigService;
import com.he.water.service.DeviceService;
import com.he.water.service.OrdersService;
import com.he.water.service.coupon.CouponUsageService;
import com.he.water.utils.AESUtil;
import com.he.water.utils.ConsantUtil;
import com.he.water.utils.DateUtil;
import com.he.water.utils.wechat.SignUtil;
import com.he.water.utils.wechat.XmlUtil;
import com.xiaohe.hservice.HService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.List;

import static com.he.water.entity.wechat.WechatClient.signReturn;

/**
 * 公众号号 下订单，支付，以及处理回调，退款及退款回调
 *
 * @author
 */

@RestController
@RequestMapping(value = "/sellWater")
public class SellWaterController {
    private static Logger log = LoggerFactory.getLogger(SellWaterController.class);

    @Autowired
    private OrdersService ordersService;
    @Autowired
    private HService hService;
    @Autowired
    private CouponUsageService couponUsageService;
    @Autowired
    private WxConfigService wxConfigService;

//    @Value("${wechat.appId}")
//    private String appId;
//    @Value("${wechat.appSecret}")
//    private String appSecret;
//    @Value("${wechat.secret}")
//    private String secret;
//    @Value("${wechat.mchId}")
//    private String mchId;
//    @Value("${wechat.cert}")
//    private String cert;
//    @Value("${wechat.tradeType}")
//    private String tradeType;


    /**
     * 支付回调
     *
     * @param request  请求
     * @param response 响应
     */
    @RequestMapping("/wechatNotify")
    public void wechatNotify(HttpServletRequest request, HttpServletResponse response) {
        InputStream inputStream;
        response.setContentType(ConsantUtil.CONTENT_TYPE);
        PrintWriter writer = null;
        try {
            inputStream = request.getInputStream();
            writer = response.getWriter();
            WechatPayNotify notice = XmlUtil.xmlToBean(inputStream, WechatPayNotify.class);

            log.info("进入微信回调参数:" + JSON.toJSONString(notice));
            List<WxConfig> wxConfigs = wxConfigService.findByAppId(notice.getAppid());
            log.info("退款成功获取的微信配置" + JSONObject.toJSON(wxConfigs));
            log.info("退款成功解码前的notice" + JSONObject.toJSON(notice));
            WxConfig wxConfig = wxConfigs.get(0);

            String sign = signReturn(SignUtil.bean2TreeMap(notice), wxConfig.getAppSecret());
            String returnXml = null;
            Orders order = null;
            String sign1 = notice.getSign();
            if (sign.equals(sign1)) {
                String outTradeNo = notice.getOut_trade_no();
                log.info(outTradeNo);
                //查询本地订单
                order = ordersService.findByOrderNo(outTradeNo);
                log.info("微信回调成功:" + JSONObject.toJSON(order));
                if (order != null) {
                    log.info("进入微信回调:" + "id=" + order.getId() + ",imei=" + order.getDevMac() + ",type=" + order.getSellType() + ",amount=" + order.getWaterAmount());
                    try {
                        order.setTradeNo(notice.getTransaction_id());
                        order.setPayNotifyTime(DateUtil.strToDate(notice.getTime_end()));
                        order.setPayStatus(ConsantUtil.PAY_SUCCESS);
                        order.setSellStatus(ConsantUtil.SELL_WAIT_OUT);
                        // 保存优惠券使用情况
                        if (order.getCouponUsageId() != null) {
                            log.info(order.getCouponUsageId() + "保存优惠券使用情况" + ConsantUtil.COUPUN_USEING);
                            couponUsageService.updateStatus(order.getCouponUsageId(), ConsantUtil.COUPUN_USEING);
                        }
                        hService.turnOnWater(order.getId(), order.getDevMac(), order.getSellType(), order.getPulse());
                    } catch (Exception e) {
                        log.info("网络连接超时");
                        order.setSellStatus(ConsantUtil.SELL_FILE);
                    } finally {
                        returnXml = XmlUtil.getXml(ConsantUtil.SUCCESS, ConsantUtil.OK);
                        ordersService.save(order);
                    }
                } else {
                    log.info("微信回调查询订单为空");
                }
            } else {
                log.info("微信支付回调参数不成功");
                returnXml = XmlUtil.getXml(ConsantUtil.FAIL, ConsantUtil.ERROR);
            }
            log.info("返回微信参数" + returnXml);
            writer.write(returnXml);
            writer.flush();
            log.info("订单已下,支付：" + order.getOrderNo());
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (writer != null) {
                writer.close();
            }
        }
    }


    /**
     * 退款成功回调
     */
    @PostMapping(value = "/refundNotify")
    public void refundNotify(HttpServletRequest request, HttpServletResponse response) {
        log.info("进入微信退款回调");
        response.reset();
        InputStream inputStream;
        PrintWriter writer = null;
        try {
            inputStream = request.getInputStream();
            response.reset();
            writer = response.getWriter();
            response.setContentType(ConsantUtil.CONTENT_TYPE);
            WechatRefundNotify notice = XmlUtil.xmlToBean(inputStream, WechatRefundNotify.class);

            List<WxConfig> wxConfigs = wxConfigService.findByAppId(notice.getAppid());
            log.info("退款成功获取的微信配置" + JSONObject.toJSON(wxConfigs));
            log.info("退款成功解码前的notice" + JSONObject.toJSON(notice));
            WxConfig wxConfig = wxConfigs.get(0);
            String bStr = null;
            try {
                bStr = AESUtil.decryptData(notice.getReq_info(), wxConfig.getAppSecret()).replaceAll("root", "xml");
            } catch (Exception e) {
                log.info("AESUtil.decryptData解码异常");
            }
            log.info("解码bStr:" + bStr);
            WechatRefundNotify.Response noticeRes = XmlUtil.xmlToBean(bStr, WechatRefundNotify.Response.class);
            log.info("解码后的notice" + JSONObject.toJSON(noticeRes));
            String returnXml;
            if (noticeRes == null) {
                log.info("微信回调失败");
                returnXml = XmlUtil.getXml(ConsantUtil.FAIL, ConsantUtil.ERROR);
            } else {
                if (ConsantUtil.SUCCESS.equals(noticeRes.getRefund_status())) {
                    // 查询订单，修改状态
                    Orders order = ordersService.findByOrderNo(noticeRes.getOut_trade_no());
                    log.info("微信回调成功order:" + JSONObject.toJSON(order));
                    if (order != null) {
                        log.info("微信回调修改:");
                        order.setRefundId(noticeRes.getRefund_id());
                        order.setRefundNo(noticeRes.getOut_refund_no());
                        //退款状态
                        order.setPayStatus(ConsantUtil.PAY_REFUND);
                        //售水失败
                        //todo  售水成功时不改变
                        if (order.getSellStatus() == 2) {
                            order.setSellStatus(ConsantUtil.SELL_FILE);
                        }
                        // 退款修优惠券使用情况
                        if (order.getCouponUsageId() != null) {
                            couponUsageService.updateStatus(order.getCouponUsageId(), ConsantUtil.COUPUN_CANCEL);
                        }
                        ordersService.save(order);
                        returnXml = XmlUtil.getXml(ConsantUtil.SUCCESS, ConsantUtil.OK);
                    } else {
                        log.info("微信回调中查询订单为空");
                        returnXml = XmlUtil.getXml(ConsantUtil.FAIL, ConsantUtil.ERROR);
                    }
                } else {
                    log.info("微信退款回调参数不成功");
                    returnXml = XmlUtil.getXml(ConsantUtil.FAIL, ConsantUtil.ERROR);
                }
            }
            log.info("返回微信参数" + returnXml);
            writer.write(returnXml);
            writer.flush();
        } catch (IOException e) {
            log.error("request和response的IOException异常");
        } finally {
            if (writer != null) {
                writer.close();
            }

        }
    }

    @RequestMapping("/test")
    public Result doTest(@RequestParam("imei") String imei, @RequestParam("passage") int passage, @RequestParam("amount") int amount) {

        log.info("进入test：imei为{}，passage为{}，type为{}", imei, passage, amount);
        int result = hService.controlWater(45, imei, passage, amount);

        CommonResult commonResult = new CommonResult();
        commonResult.ok();
        commonResult.setDatas(result);

        return commonResult;
    }

}
