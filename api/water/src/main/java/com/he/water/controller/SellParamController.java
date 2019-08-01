package com.he.water.controller;

import com.alibaba.fastjson.JSONObject;
import com.he.water.config.ThreadPool;
import com.he.water.entity.*;
import com.he.water.entity.ad.AdvertisingEntity;
import com.he.water.entity.complaints.Complaints;
import com.he.water.entity.coupon.Coupon;
import com.he.water.entity.coupon.CouponUsage;
import com.he.water.entity.schema.SchemaDetail;
import com.he.water.entity.schema.WaterSchema;
import com.he.water.entity.wechat.WechatTrade;
import com.he.water.entity.wechat.entity.WechatRefund;
import com.he.water.entity.wechat.entity.WechatUnifiedOrder;
import com.he.water.entity.wechat.entity.WeixinOauth2Token;
import com.he.water.entity.wxconfig.entity.WxConfig;
import com.he.water.entity.wxconfig.service.WxConfigService;
import com.he.water.service.DeviceService;
import com.he.water.service.OrdersService;
import com.he.water.service.PassageService;
import com.he.water.service.ad.AdvertisingService;
import com.he.water.service.complaints.ComplaintsService;
import com.he.water.service.coupon.CouponService;
import com.he.water.service.coupon.CouponUsageService;
import com.he.water.service.schame.SchemaDetailService;
import com.he.water.service.schame.WaterSchemaService;
import com.he.water.utils.ConsantUtil;
import com.he.water.utils.ObjUtil;
import com.he.water.utils.wechat.AdvancedUtil;
import com.he.water.utils.wechat.RefundUtil;
import com.he.water.utils.wechat.WebUtils;
import com.xiaohe.hservice.HService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.*;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;


/**
 * 售水参数设置
 *
 * @author hzh
 * @date 2018/11/12
 */
@RestController
@RequestMapping(value = "/home")
public class SellParamController {
    private static Logger log = LoggerFactory.getLogger(SellParamController.class);

    @Autowired
    private DeviceService deviceService;
    @Autowired
    private PassageService passageService;
    @Autowired
    private HService hService;
    @Autowired
    private OrdersService ordersService;
    @Autowired
    private SchemaDetailService schemaDetailService;
    @Autowired
    private WaterSchemaService waterSchemaService;
    @Autowired
    private CouponService couponService;
    @Autowired
    private CouponUsageService couponUsageService;
    @Autowired
    private AdvertisingService advertisingService;
    @Autowired
    private WxConfigService wxConfigService;
    @Autowired
    private ComplaintsService complaintsService;
    @Resource
    private HttpServletRequest request;


    /**
     * 获取首页数据展示
     *
     * @return 售水方案列表
     */
    @RequestMapping(value = "/lists")
    public Result findTypeList(String devId) {
        log.info("进入获取商品列表：" + devId);
        CommonResult result = new CommonResult();
        DeviceEntity byDeviceId = deviceService.findByDeviceId(devId);
        if (byDeviceId == null) {
            byDeviceId = deviceService.findByImei(devId);
            if (byDeviceId == null) {
                return result.error(1002, "设备不存在");
            }
        }
        List<PassageEntity> passageEntityList = passageService.findByDevId(byDeviceId.getId());
        if (CollectionUtils.isEmpty(passageEntityList)) {
            return result.error(1004, "该设备没有售水通道");
        }
        //查水量方案
        Integer schemaId = byDeviceId.getSchemaId();
        if (schemaId == null) {
            return result.error(1003, "暂无售水方案");
        }
        WaterSchema waterSchema = waterSchemaService.findById(schemaId);
        if (waterSchema == null) {
            return result.error(1003, "暂无售水方案");
        }
        for (PassageEntity passageEntity : passageEntityList) {
            List<PassageAmount> passageAmounts = new ArrayList<>();
            String name;
            Integer passageType = passageEntity.getPassageType();
            switch (passageType) {
                case 1:
                    name = "开水";
                    break;
                case 2:
                    name = "温水";
                    break;
                case 3:
                    name = "直饮水";
                    break;
                case 4:
                    name = "冰水";
                    break;
                default:
                    name = "未知";
                    break;
            }
            //查水量详细
            List<SchemaDetail> bySchemaId = schemaDetailService.findBySchemaId(waterSchema.getId());
            for (SchemaDetail schemaDetail : bySchemaId) {
                passageAmounts.add(new PassageAmount(schemaDetail.getWaterAmount(), passageEntity.getPrice() * schemaDetail.getWaterAmount() / 100, name + schemaDetail.getName(), passageType, passageEntity.getPassageNo()));
            }
            passageEntity.setAmountList(passageAmounts);
        }
        log.info("详细情况：" + JSONObject.toJSONString(passageEntityList));
        result.ok();
        result.setDatas(passageEntityList);
        return result;

    }

    /**
     * 设备是否在线
     *
     * @param devId 设备编号或者imei
     * @return 在线结果
     */
    @PostMapping(value = "/devOnline")
    public Result devOnline(@RequestParam("devId") String devId) {
        CommonResult commonResult = new CommonResult();
        DeviceEntity byDeviceId = deviceService.findByDeviceId(devId);
        log.info("进入设备是否在线" + JSONObject.toJSONString(byDeviceId));
        if (byDeviceId == null) {
            byDeviceId = deviceService.findByImei(devId);
            if (byDeviceId == null) {
                return commonResult.error(1002, "设备不存在");
            }
        }
        if (StringUtils.isNotBlank(byDeviceId.getImei())) {
            log.info("imei：" + byDeviceId.getImei());
            int status = hService.selectDevOnline(byDeviceId.getImei());
            if (status != 0) {
                commonResult.error(1, "此设备不在线");
                return commonResult;
            }
            WxConfig wxConfig = wxConfigService.findByMerchantId(byDeviceId.getUserId().intValue());
            commonResult.setDatas(wxConfig);
            commonResult.ok();
        } else {
            commonResult.error(2, "设备的IMEI为空");
        }
        log.info("进入设备返回对象" + JSONObject.toJSONString(commonResult));
        return commonResult;
    }

    /**
     * 得到用户openId
     *
     * @return 返回获取的openid
     */
    @RequestMapping("/getAppId")
    public Result getAppId(String code, String appId, String secret) {
        log.info("code：" + code + "@@@@@@appId" + appId + "###########secret" + secret);

        // 获取网页授权access_token
        WeixinOauth2Token weixinOauth2Token = AdvancedUtil.getOauth2AccessToken(appId, secret, code);
        log.info("openId：" + JSONObject.toJSONString(weixinOauth2Token));
        CommonResult commonResult = new CommonResult();
        commonResult.ok();
        Map<String, String> map = new HashMap<>(1);
        map.put("openId", weixinOauth2Token.getOpenId());
        commonResult.setDatas(map);
        return commonResult;
    }

    /**
     * 向微信下订单
     *
     * @param passageAmount 通道对象
     * @return 返回下单操作结果
     */
    @PostMapping("/unifiedOrder1")
    public CommonResult unifiedOrder(@Validated @RequestBody PassageAmount passageAmount, HttpServletRequest request) {
        CommonResult com = new CommonResult();
        WechatUnifiedOrder unifiedOrder = new WechatUnifiedOrder();
        Orders orders = new Orders();

        log.info("向微信下订单" + JSONObject.toJSONString(passageAmount));
        //设置本地订单数据
        DeviceEntity byDeviceId = deviceService.findByDeviceId(passageAmount.getDevId());
        if (byDeviceId == null) {
            byDeviceId = deviceService.findByImei(passageAmount.getDevId());
        }
        com = ObjUtil.validateParam(passageAmount, com, byDeviceId);
        if (com.getRet() != null && com.getMsg() != null) {
            return com;
        }
        orders.setDevId(byDeviceId.getDeviceId());
        orders.setDevMac(byDeviceId.getImei());
        //保存用户券使用信息
        orders.setCouponUsageId(passageAmount.getCouponUsageId());
        //水量转脉冲
        PassageEntity passageEntity = passageService.findByDevIdAndPassageNo(byDeviceId.getId(), passageAmount.getPassageNo());
        int pulse = (passageAmount.getAmount() * passageEntity.getPulseNum()) / 100;
        //脉冲数
        orders.setPulse(pulse);
        //脉冲通道
        orders.setPassageNo(passageAmount.getPassageNo());
        //售水量
        orders.setWaterAmount(passageAmount.getAmount());
        //售水金额
        orders.setMoneyAmount(passageAmount.getPrice());
        //售水类型
        orders.setSellType(passageAmount.getPassageNo());
        //售水类型
        orders.setWaterTemp(passageAmount.getPassageType());
        //优惠金额
        orders.setCouponPrice(passageAmount.getCouponPrice());
        log.info("向微信下订单:PassageNo=" + orders.getSellType() + ",pulse=" + orders.getWaterAmount());
        //构造微信订单数据
        log.info("商品名：" + passageAmount.getName() + "，售水量为:" + passageAmount.getAmount());
        unifiedOrder.setBody(passageAmount.getName());
        int totalFee = passageAmount.getPrice() - passageAmount.getCouponPrice();

        unifiedOrder.setTotal_fee(totalFee);
        unifiedOrder.setDetail("商品名：" + passageAmount.getName() + "，售水量为:" + passageAmount.getAmount() + "，金额为:" + passageAmount.getPrice());
        unifiedOrder.setTime_start(System.currentTimeMillis() + "");
        unifiedOrder.setGoods_tag("愉升出品");
        unifiedOrder.setOut_trade_no(ConsantUtil.OUT_TRADE_NO + System.currentTimeMillis());
        unifiedOrder.setFee_type(ConsantUtil.CNY);
        unifiedOrder.setSpbill_create_ip(WebUtils.getIpAddr(request));
        unifiedOrder.setOpenid(passageAmount.getOpenId());
        //下订单
        if (totalFee == 0) {
            orders.setOpenId(passageAmount.getOpenId());
            orders.setCreateTime(new Date());
            orders.setOrderNo(unifiedOrder.getOut_trade_no());
            orders.setPayNotifyTime(new Date());
            orders.setPayStatus(ConsantUtil.PAY_SUCCESS);
            orders.setSellStatus(ConsantUtil.SELL_WAIT_OUT);
            ordersService.save(orders);
            //下发出水
            hService.turnOnWater(orders.getId(), orders.getDevMac(), orders.getSellType(), orders.getPulse());
            // 保存优惠券使用情况
            if (orders.getCouponUsageId() != null) {
                log.info(orders.getCouponUsageId() + "保存优惠券使用情况" + ConsantUtil.COUPUN_USEING);
                couponUsageService.updateStatus(orders.getCouponUsageId(), ConsantUtil.COUPUN_USEING);
            }
            Map<String, Object> map = new HashMap<>(1);
            map.put("totalFee", totalFee);
            com.setDatas(map);
            com.ok();
            return com;
        }
        com = WechatTrade.unifiedOrderRequest(unifiedOrder, com, passageAmount.getMchInfo());
        if (com.getMsg() != null && ConsantUtil.SUCCESS.toLowerCase().equals(com.getMsg())) {
            //保存自己的订单到数据库
            saveOrder(unifiedOrder, orders);
        }
        return com;
    }


    /**
     * 保存订单
     *
     * @param unifiedOrder WechatUnifiedOrder微信对象
     * @param orders       自己的订单对象
     */
    private void saveOrder(WechatUnifiedOrder unifiedOrder, Orders orders) {
        orders.setOpenId(unifiedOrder.getOpenid());
        orders.setCreateTime(new Date(Long.valueOf(unifiedOrder.getTime_start())));
        orders.setOrderNo(unifiedOrder.getOut_trade_no());
        orders.setPayStatus(ConsantUtil.PAY_WAIT_PAY);
        orders.setSellStatus(ConsantUtil.SELL_CANCEL);
        ordersService.save(orders);
    }

    /**
     * 获取优惠券
     *
     * @param deviceId 设备编码
     * @return 优惠券详情
     */
    @GetMapping("/coupons")
    public Result getCoupons(String deviceId, int price) {
        log.info("获取优惠券" + deviceId);
        CommonResult result = new CommonResult();
        DeviceEntity deviceEntity = deviceService.findByDeviceId(deviceId);
        log.info("获取优惠券实体byDevId" + JSONObject.toJSONString(deviceEntity));
        if (deviceEntity == null) {
            deviceEntity = deviceService.findByImei(deviceId);
            log.info("获取优惠券实体byImei" + JSONObject.toJSONString(deviceEntity));
            if (deviceEntity == null) {
                result.setDatas(new CouponUsage());
                return result.error(1002, "设备不存在");
            }
        }
        Long id = deviceEntity.getId();
        Callable callable = new Callable() {
            @Override
            public Object call() throws Exception {
                //初始优惠券数,用来判断优惠券的个数
                int num = 1;
                synchronized ("") {
                    try {
                        //查看优惠券数据，还有开始时间和结束时间
                        Coupon coupon = couponService.findByDevIdAndStartTimeBeforeAndEndTimeAfter(id, new Date(), new Date());
                        log.info("查询结构：" + JSONObject.toJSONString(coupon));
                        if (coupon == null) {
                            result.setDatas(new CouponUsage());
                            return result.error(604, "没有优惠券");
                        }
                        if (coupon.getPrice() > price) {
                            result.setDatas(new CouponUsage());
                            return result.error(601, "优惠券金额错误");
                        }
                        Integer couponNum = coupon.getNum();
                        log.info("couponNum为：{}", couponNum);
                        // 任何线程获取"线程锁"以后都要先判断是否还有优惠券,防止等待的线程抢统一优惠券
                        if (num > couponNum) {
                            result.setDatas(new CouponUsage());
                            return result.error(602, "优惠券库存为零");
                        }
                        // 获取当前线程名字
                        log.info("当前线程名：{}，优惠券数：{}", Thread.currentThread().getName(), coupon.getNum());
                        //没有被使用的优惠券
                        int noUsedNum = couponUsageService.findByNotUsedNumByCouponId(coupon.getId());
                        //更新优惠券剩余数
                        coupon.setNum(coupon.getNum() - 1 + noUsedNum);
                        couponService.updateCouponNum(coupon);
                        //插入数据优惠券使用表，锁定该优惠券
                        CouponUsage couponUsage = couponUsageService.saveCouponUsage(coupon);
                        coupon.setCouponUsageId(couponUsage.getId());
                        result.ok();
                        result.setDatas(coupon);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                return result;
            }
        };
        Object object = null;
        // 增加isShutdown()判断
        Future submit = null;
        if (!ThreadPool.getThreadPool().isShutdown()) {
            //执行线程（主线程会有阻塞），并能返回对象
            submit = ThreadPool.getThreadPool().submit(callable);
        }
        try {
            object = submit.get();
            log.info("线程返回对象：" + JSONObject.toJSONString(object));
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        if (object == null) {
            result.setDatas(new CouponUsage());
            log.info("优惠券信息" + JSONObject.toJSONString(result));
            return result.error(601, "没有优惠券");
        }
        log.info("优惠券信息" + JSONObject.toJSONString(object));
        //获取优惠价格，优惠券id
        return (Result) object;
    }

    /**
     * 获取设备的广告
     *
     * @param devId
     * @return
     */
    @RequestMapping(value = "/advs/{devId}")
    public Result getAds(@PathVariable("devId") String devId) {
        CommonResult result = new CommonResult();
        DeviceEntity byDeviceId = deviceService.findByDeviceId(devId);
        if (byDeviceId == null) {
            byDeviceId = deviceService.findByImei(devId);
            if (byDeviceId == null) {
                return result.error(7004, "设备不存在");
            }
        }

        List<AdvertisingEntity> ads = advertisingService.findByUserId(byDeviceId.getUserId().intValue());
        result.setDatas(ads);
        result.ok();
        return result;

    }

    /**
     * 保存投诉上报信息
     *
     * @param complaints 上报信息
     * @return 保存结果
     */
    @PostMapping(value = "/complaints")
    public Result saveComplaint(@Valid @RequestBody Complaints complaints) {
        log.info("进入上报信息");
        Result result = new Result();
        Date date = new Date();
        complaints.setUpdateTime(date);
        complaints.setReportTime(date);
        complaints.setStatus(0);
        complaintsService.save(complaints);
        return result.ok();
    }


//    @RequestMapping(value = "/refund")
//    public Result refund(@RequestBody Orders orders) {
//        CommonResult result = new CommonResult();
//        if (orders.getTradeNo() == null) {
//            return new Result().error(101, "订单号为空");
//        }
//        WxConfig wxConfig = new WxConfig(appId, appSecret, secret, mchId, cert, tradeType);
//        result = RefundUtil.goRefund(orders.getTradeNo(), wxConfig);
//        if (501 != result.getRet()) {
//            orders.setPayStatus(ConsantUtil.PAY_REFUND);
//            ordersService.save(orders);
//        } else {
//            return result;
//        }
//        return result;
//    }
}