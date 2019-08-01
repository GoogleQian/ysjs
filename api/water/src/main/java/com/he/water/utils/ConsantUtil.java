package com.he.water.utils;


/**
 * 静态常量以及方法
 */
public interface ConsantUtil {
    /**
     * 待支付
     */
    int PAY_WAIT_PAY = 1;
    /**
     * 已付款
     */
    int PAY_SUCCESS = 2;
    /**
     * 已退款
     */
    int PAY_REFUND = 3;
    /**
     * 0.订单已取消
     */
    int SELL_CANCEL = 0;
    /**
     * 1.已应答，售卖成功 （已出水)
     */
    int SELL_SUCCESS = 1;
    /**
     * 2.已下发出水指令，等待应答 (待出水)
     */
    int SELL_WAIT_OUT = 2;
    /**
     * 3.售水失败
     */
    int SELL_FILE = 3;
    /**
     * 1、锁定（抢到没使用）
     */
    int COUPUN_GET = 1;
    /**
     * 2、使用（支付使用）
     */
    int COUPUN_USEING = 2;
    /**
     * 3、失效（未支付未使用）
     */
    int COUPUN_CANCEL = 3;
    /**
     * 沸水温度
     */
    int BOILING_TEMPERATURE = 100;

    String FAIL = "FAIL";
    String ERROR = "ERROR";
    String SUCCESS = "SUCCESS";
    String OK = "OK";
    /**
     * response的setContentType("");
     */
    String CONTENT_TYPE = "text/xml";
    /**
     * 退款交易号前缀
     */
    String OUT_REFUND_NO = "rn";
    /**
     * 下订单交易号前缀
     */
    String OUT_TRADE_NO = "he";

    /**
     * 交易币种
     */
    String CNY = "CNY";
}
