package com.he.water.utils.wechat;

import com.alibaba.fastjson.JSONObject;
import com.he.water.controller.SellParamController;
import com.he.water.entity.CommonResult;
import com.he.water.entity.Result;
import com.he.water.entity.wechat.WechatTrade;
import com.he.water.entity.wechat.entity.WechatOrderQuery;
import com.he.water.entity.wechat.entity.WechatPayNotify;
import com.he.water.entity.wechat.entity.WechatRefund;
import com.he.water.entity.wxconfig.entity.WxConfig;
import com.he.water.utils.ConsantUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;

/**
 * @author hzh
 * @date 2018/11/22
 */
public class RefundUtil {

    private static Logger log = LoggerFactory.getLogger(SellParamController.class);


    /**
     * 因为异步请求，所有每个订单都需要通过交易号查询的到金额等信息，在设置到退款的对象中
     *
     * @param tradeNo
     */
    public static CommonResult goRefund(String tradeNo, WxConfig wxConfig) {
        CommonResult commonResult = new CommonResult();
        commonResult.setRet(501);
        // 设置订单查询参数
        WechatOrderQuery orderQuery = new WechatOrderQuery();
        orderQuery.setTransaction_id(tradeNo);
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
                        commonResult.setDatas(refund);
                        commonResult.ok();
                    } else {
                        commonResult.setMsg(orderQueryRequest.getTrade_state_desc());
                    }
                } else {
                    commonResult.setMsg(orderQueryRequest.getErr_code_des());
                }
            } else {
                commonResult.setMsg(orderQueryRequest.getTrade_state_desc());
            }
        } catch (Exception e) {
            log.info("转入退款");
        }
        return commonResult;
    }
}
