package com.he.water.service;

import com.alibaba.fastjson.JSONObject;
import com.he.water.entity.DeviceEntity;
import com.he.water.entity.Orders;
import com.he.water.entity.coupon.Coupon;
import com.he.water.entity.coupon.CouponUsage;
import com.he.water.entity.wechat.WechatTrade;
import com.he.water.entity.wechat.entity.WechatOrderQuery;
import com.he.water.entity.wechat.entity.WechatPayNotify;
import com.he.water.entity.wechat.entity.WechatRefund;
import com.he.water.entity.wxconfig.entity.WxConfig;
import com.he.water.entity.wxconfig.service.WxConfigService;
import com.he.water.exception.RefundException;
import com.he.water.service.coupon.CouponService;
import com.he.water.service.coupon.CouponUsageService;
import com.he.water.utils.ConsantUtil;
import com.he.water.utils.ObjUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.util.*;
import java.util.stream.Collectors;

@Component
@EnableScheduling
public class QuartzService {

    private static Logger log = LoggerFactory.getLogger(QuartzService.class);
    @Autowired
    private OrdersService ordersService;
    @Autowired
    private CouponUsageService couponUsageService;
    @Autowired
    private CouponService couponService;
    @Autowired
    private DeviceService deviceService;
    @Autowired
    private WxConfigService wxConfigService;


    /**
     * 自动退款定时任务
     */

    @Scheduled(cron = "0/30 *  * * * ?")
    public void refundQuartz() {
        log.info("进入自动退款定时任务");
        List<Orders> ordersList = ordersService.findByPayNotifyTime();
        if (!CollectionUtils.isEmpty(ordersList)) {
            for (Orders orders : ordersList) {
                log.info("时间差" + (System.currentTimeMillis() - orders.getCreateTime().getTime()));
                if (orders.getTradeNo() != null) {
                    DeviceEntity deviceEntity = deviceService.findByDeviceId(orders.getDevId());
                    WxConfig wxConfig = wxConfigService.findByMerchantId(deviceEntity.getUserId().intValue());
                    WechatRefund.Response response = goRefund(orders, wxConfig);
                    log.info("退款返回对象：" + JSONObject.toJSONString(response));
                    if (response != null) {
                        orders.setPayStatus(ConsantUtil.PAY_REFUND);
                        ordersService.save(orders);
                    }
                } else {
                    orders.setPayStatus(ConsantUtil.PAY_REFUND);
                    ordersService.save(orders);
                }
            }
        }
    }

    /**
     * 售水成功 自动退款定时任务
     */
    @Scheduled(cron = "0/30 * * * * ?")
    public void refundQuartz1() {
        log.info("进入售水成功 自动退款定时任务");
        List<Orders> ordersList = ordersService.findList();
        if (!CollectionUtils.isEmpty(ordersList)) {
            for (Orders orders : ordersList) {
                if (orders.getPayStatus() == 2 && orders.getSellStatus() == 1) {
                    if (orders.getTradeNo() != null) {
                        DeviceEntity deviceEntity = deviceService.findByDeviceId(orders.getDevId());
                        WxConfig wxConfig = wxConfigService.findByMerchantId(deviceEntity.getUserId().intValue());
                        WechatRefund.Response response = goRefund(orders, wxConfig);
                        log.info("退款返回对象：" + JSONObject.toJSONString(response));
                        if (response != null) {
                            orders.setPayStatus(ConsantUtil.PAY_REFUND);
                            ordersService.save(orders);
                        }
                    }
                }
            }
        }
    }

    /**
     * 优惠券定时任务
     */
    @Scheduled(cron = "0/59 * * * * ?")
    public void couponQuartz() {
        log.info("进入优惠券定时任务");
//        File file = new File(filePath);
//        RandomAccessFile randomAccessFile = null;
//        try {
//            if (!file.exists()) {
//                file.createNewFile();
//            }
//            randomAccessFile = new RandomAccessFile(file, "rw");
//        } catch (IOException e) {
//
//        }
//        if (randomAccessFile == null) {
//            log.info("被上锁文件不存在");
//            return;
//        }
//        FileChannel fileChannel = randomAccessFile.getChannel();
//        FileLock fileLock = null;
        //获取所有设备的有效优惠券
        List<Coupon> couponList = couponService.findByStartTimeBeforeAndEndTimeAfter();
        if (CollectionUtils.isEmpty(couponList)) {
            return;
        }
        //查询没有使用的优惠券
        List<CouponUsage> couponUsageList = couponUsageService.findByNotUsedCouponAndCouponIdIn(ObjUtil.getIds(couponList));
        if (CollectionUtils.isEmpty(couponUsageList)) {
            return;
        }
        //分组， 按照字段中CouponId将list分组
        Map<Integer, List<CouponUsage>> map = couponUsageList.stream().collect(Collectors.groupingBy(cu -> cu.getCouponId()));
        Set<Integer> keySet = map.keySet();

        for (Coupon coupon : couponList) {
            if (keySet.contains(coupon.getId())) {
                log.info(coupon.getId() + "保存没有使用数：" + map.get(coupon.getId()).size());
                log.info(coupon.getId() + "数据库原数：" + coupon.getNum());
                int allCouponNum = map.get(coupon.getId()).size() + coupon.getNum();
                coupon.setNum(allCouponNum);
                log.info(coupon.getId() + "保存总数：" + allCouponNum);
                couponService.save(coupon);
            }
        }
        for (CouponUsage couponUsage : couponUsageList) {
            couponUsage.setStatus(ConsantUtil.COUPUN_CANCEL);
            couponUsage.setModifyTime(new Date());
            couponUsageService.save(couponUsage);
        }

    }


    /**
     * 因为异步请求，所有每个订单都需要通过交易号查询的到金额等信息，在设置到退款的对象中
     *
     * @param order
     */
    public WechatRefund.Response goRefund(Orders order, WxConfig wxConfig) {
        // 设置订单查询参数
        WechatOrderQuery orderQuery = new WechatOrderQuery();
        orderQuery.setTransaction_id(order.getTradeNo());
        //发送订单，并得到订单
        WechatOrderQuery.Response orderQueryRequest = WechatTrade.orderQueryRequest(orderQuery, wxConfig);
        log.info("查询订单结果" + JSONObject.toJSONString(orderQueryRequest));
        WechatPayNotify refundNotice = new WechatPayNotify();
        //设置退款参数
        try {
            if (ConsantUtil.SUCCESS.equals(orderQueryRequest.getReturn_code()) && ConsantUtil.OK.equals(orderQueryRequest.getReturn_msg())) {
                if (ConsantUtil.SUCCESS.equals(orderQueryRequest.getResult_code())) {
                    if (ConsantUtil.SUCCESS.equals(orderQueryRequest.getTrade_state())) {
                        refundNotice.setTransaction_id(orderQueryRequest.getTransaction_id());
                        refundNotice.setTotal_fee(orderQueryRequest.getTotal_fee());
                        WechatRefund.Response refund = WechatTrade.refundRequest(refundNotice, wxConfig);
                        log.info("退款返回对象" + JSONObject.toJSON(refund));
                        return refund;
                    } else {
                        log.info(orderQueryRequest.getTrade_state());
                    }
                } else {
                    log.info(orderQueryRequest.getErr_code());
                }
            } else {
                log.info(orderQueryRequest.getReturn_code() + orderQueryRequest.getReturn_msg());
            }
        } catch (Exception e) {
            log.info("转入退款");
        }
        return null;
    }

    /**
     * 得到查询订单后的所存在的状态或错误
     *
     * @param respCode 错误类型
     * @return RefundException
     */
    private static RefundException getException(String respCode) {
        String reqMsg;
        switch (respCode) {
            case "REFUND":
                reqMsg = "转入退款";
                break;
            case "NOTPAY":
                reqMsg = "未支付";
                break;
            case "CLOSED":
                reqMsg = "已关闭";
                break;
            case "REVOKED":
                reqMsg = "已撤销（刷卡支付）";
                break;
            case "USERPAYING":
                reqMsg = "用户支付中";
                break;
            case "PAYERROR":
                reqMsg = "-支付失败";
                break;
            case "ORDERNOTEXIST":
                reqMsg = "此交易订单号不存在";
                break;
            case "SYSTEMERROR":
                reqMsg = "系统错误";
                break;
            default:
                if (respCode.startsWith("FAIL")) {
                    reqMsg = respCode.substring(4);
                } else {
                    reqMsg = "未知错误";
                }
                break;
        }
        return new RefundException(respCode, reqMsg);
    }
}