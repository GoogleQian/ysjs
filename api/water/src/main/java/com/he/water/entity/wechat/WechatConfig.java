package com.he.water.entity.wechat;


/**
 * 微信支付配置
 *
 * @author hzh
 * @date 2018/7/20
 */

public final class WechatConfig {
    /**
     * tokem
     */
    public static final String TOKEN = "WinXinSPF";

    /**
     * 统一下单地址
     */
    public static final String UNIFIEDORDER_URL = "https://api.mch.weixin.qq.com/pay/unifiedorder";
    /**
     * 查询订单
     */
    public static final String ORDER_QUERY_URL = "https://api.mch.weixin.qq.com/pay/orderquery";
    /**
     * 交易退款地址
     */
    public static final String REFUND_URL = "https://api.mch.weixin.qq.com/secapi/pay/refund";
    /**
     * 交易退款查询地址
     */
    public static final String REFUND_QUERY = "https://api.mch.weixin.qq.com/pay/refundquery";
    /**
     * 获取access_token 地址
     */
    public static final String ACCESS_TOKEN_URL = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=APPID&secret=APPSECRET&code=CODE&grant_type=authorization_code";
    /**
     * 验证 token 地址
     */
    public static final String GET_ACCESS_TOKEN = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET";
    /**
     * 支付成功回调
     */
    public static final String NOTIFY_PAY = "http://iotsvr.he-live.com/sellWater/wechatNotify";
    /**
     * 退款成功回调
     */
    public static final String REFUND_RUL = "http://iotsvr.he-live.com/sellWater/refundNotify";
    /**
     * 企业转账url
     */
    public static final String TRANSFER_RUL = "https://api.mch.weixin.qq.com/mmpaymkttransfers/promotion/transfers";

    /**
     * 成功标识
     */
    public static final String SUCCESS_REQUEST = "SUCCESS";
    /**
     * APPID这个字符串
     */
    public static final String APPID = "APPID";
    /**
     * APPSECRET这个字符串
     */
    public static final String APPSECRET = "APPSECRET";
    /**
     * CODE这个字符串
     */
    public static final String CODE = "CODE";

    /**
     * 微信token
     */
    private final static String WEIXIN_URL = "https://api.weixin.qq.com/sns/oauth2/access_token";

    private final static String WEIXIN_INFO_URL = "https://api.weixin.qq.com/sns/userinfo";

    /**
     * 不可实例化
     */
    private WechatConfig() {
    }

    private volatile static WechatClient wechatClient = null;

    /**
     * 双重锁单例
     *
     * @return WechatClient实例
     */
    public static WechatClient getInstance(String appId, String mchId, String appSecret, String tradeType, String cert, String secret) {
        if (wechatClient == null) {
            synchronized (WechatConfig.class) {
                if (wechatClient == null) {
                    return new WechatClient(appId, mchId, appSecret, tradeType, cert, secret);
                }
            }
        }
        return wechatClient;
    }

}
