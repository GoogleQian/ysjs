package com.he.water.entity.wechat.entity;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * 微信订单查询
 *
 * @author hzh
 * @date 2018/7/20
 */
@XmlRootElement(name = "xml")
public class WechatOrderQuery {
    private String appid;// 公众账号ID
    private String mch_id;//商户号
    private String transaction_id;// 微信订单号  2选1
    private String out_trade_no;// 商户订单号
    private String nonce_str;// 随机字符串
    private String sign;// 签名
    private String sign_type;//  签名类型

    @XmlRootElement(name = "xml")
    public static class Response {
        private String return_code;//   返回状态码
        private String return_msg;//返回信息 return_msg
        // 以下字段在return_code为SUCCESS的时候有返回
        private String appid;//    公众账号ID appid
        private String mch_id;//  商户号 mch_id
        private String nonce_str;//   随机字符串
        private String sign;// 签名 sign
        private String result_code;// 业务结果 result_code
        private String err_code;// 错误代码 err_code
        private String err_code_des;//    错误代码描述
        // 以下字段在return_code 、result_code、trade_state都为SUCCESS时有返回 ，如trade_state不为 SUCCESS，则只返回out_trade_no（必传）和attach（选传）。
        private String device_info;//        设备号 device_info
        private String openid;//     用户标识
        private String is_subscribe;//   是否关注公众账号
        private String trade_type;//  交易类型 trade_type
        private String trade_state;//  交易状态
        private String bank_type;//          银行类型
        private int total_fee;//标价金额
        private int settlement_total_fee;//应结订单金额   当订单使用了免充值型优惠券后返回该参数，应结订单金额=订单金额-免充值优惠券金额。
        private String fee_type;//  标价币种
        private int cash_fee;//        现金支付金额
        private String cash_fee_type; // 现金支付币种
        private int coupon_fee;//   代金券金额
        private int coupon_count;// 代金券使用数量
        private int coupon_type_$n;// 代金券类型
        private String coupon_id_$n;// 代金券ID
        private int coupon_fee_$n;//单个代金券支付金额
        private String transaction_id;// 微信支付订单号
        private String out_trade_no;//  商户订单号
        private String attach;//   附加数据
        private String time_end;//  支付完成时间
        private String trade_state_desc;   // 交易状态描述

        public Response(String result_code) {
            this.result_code = result_code;
        }

        public Response() {
        }

        public String getReturn_code() {
            return return_code;
        }

        public void setReturn_code(String return_code) {
            this.return_code = return_code;
        }

        public String getReturn_msg() {
            return return_msg;
        }

        public void setReturn_msg(String return_msg) {
            this.return_msg = return_msg;
        }

        public String getAppid() {
            return appid;
        }

        public void setAppid(String appid) {
            this.appid = appid;
        }

        public String getMch_id() {
            return mch_id;
        }

        public void setMch_id(String mch_id) {
            this.mch_id = mch_id;
        }

        public String getNonce_str() {
            return nonce_str;
        }

        public void setNonce_str(String nonce_str) {
            this.nonce_str = nonce_str;
        }

        public String getSign() {
            return sign;
        }

        public void setSign(String sign) {
            this.sign = sign;
        }

        public String getResult_code() {
            return result_code;
        }

        public void setResult_code(String result_code) {
            this.result_code = result_code;
        }

        public String getErr_code() {
            return err_code;
        }

        public void setErr_code(String err_code) {
            this.err_code = err_code;
        }

        public String getErr_code_des() {
            return err_code_des;
        }

        public void setErr_code_des(String err_code_des) {
            this.err_code_des = err_code_des;
        }

        public String getDevice_info() {
            return device_info;
        }

        public void setDevice_info(String device_info) {
            this.device_info = device_info;
        }

        public String getOpenid() {
            return openid;
        }

        public void setOpenid(String openid) {
            this.openid = openid;
        }

        public String getIs_subscribe() {
            return is_subscribe;
        }

        public void setIs_subscribe(String is_subscribe) {
            this.is_subscribe = is_subscribe;
        }

        public String getTrade_type() {
            return trade_type;
        }

        public void setTrade_type(String trade_type) {
            this.trade_type = trade_type;
        }

        public String getTrade_state() {
            return trade_state;
        }

        public void setTrade_state(String trade_state) {
            this.trade_state = trade_state;
        }

        public String getBank_type() {
            return bank_type;
        }

        public void setBank_type(String bank_type) {
            this.bank_type = bank_type;
        }

        public int getTotal_fee() {
            return total_fee;
        }

        public void setTotal_fee(int total_fee) {
            this.total_fee = total_fee;
        }

        public int getSettlement_total_fee() {
            return settlement_total_fee;
        }

        public void setSettlement_total_fee(int settlement_total_fee) {
            this.settlement_total_fee = settlement_total_fee;
        }

        public String getFee_type() {
            return fee_type;
        }

        public void setFee_type(String fee_type) {
            this.fee_type = fee_type;
        }

        public int getCash_fee() {
            return cash_fee;
        }

        public void setCash_fee(int cash_fee) {
            this.cash_fee = cash_fee;
        }

        public String getCash_fee_type() {
            return cash_fee_type;
        }

        public void setCash_fee_type(String cash_fee_type) {
            this.cash_fee_type = cash_fee_type;
        }

        public int getCoupon_fee() {
            return coupon_fee;
        }

        public void setCoupon_fee(int coupon_fee) {
            this.coupon_fee = coupon_fee;
        }

        public int getCoupon_count() {
            return coupon_count;
        }

        public void setCoupon_count(int coupon_count) {
            this.coupon_count = coupon_count;
        }

        public int getCoupon_type_$n() {
            return coupon_type_$n;
        }

        public void setCoupon_type_$n(int coupon_type_$n) {
            this.coupon_type_$n = coupon_type_$n;
        }

        public String getCoupon_id_$n() {
            return coupon_id_$n;
        }

        public void setCoupon_id_$n(String coupon_id_$n) {
            this.coupon_id_$n = coupon_id_$n;
        }

        public int getCoupon_fee_$n() {
            return coupon_fee_$n;
        }

        public void setCoupon_fee_$n(int coupon_fee_$n) {
            this.coupon_fee_$n = coupon_fee_$n;
        }

        public String getTransaction_id() {
            return transaction_id;
        }

        public void setTransaction_id(String transaction_id) {
            this.transaction_id = transaction_id;
        }

        public String getOut_trade_no() {
            return out_trade_no;
        }

        public void setOut_trade_no(String out_trade_no) {
            this.out_trade_no = out_trade_no;
        }

        public String getAttach() {
            return attach;
        }

        public void setAttach(String attach) {
            this.attach = attach;
        }

        public String getTime_end() {
            return time_end;
        }

        public void setTime_end(String time_end) {
            this.time_end = time_end;
        }

        public String getTrade_state_desc() {
            return trade_state_desc;
        }

        public void setTrade_state_desc(String trade_state_desc) {
            this.trade_state_desc = trade_state_desc;
        }
    }

    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }

    public String getMch_id() {
        return mch_id;
    }

    public void setMch_id(String mch_id) {
        this.mch_id = mch_id;
    }

    public String getTransaction_id() {
        return transaction_id;
    }

    public void setTransaction_id(String transaction_id) {
        this.transaction_id = transaction_id;
    }

    public String getOut_trade_no() {
        return out_trade_no;
    }

    public void setOut_trade_no(String out_trade_no) {
        this.out_trade_no = out_trade_no;
    }

    public String getNonce_str() {
        return nonce_str;
    }

    public void setNonce_str(String nonce_str) {
        this.nonce_str = nonce_str;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getSign_type() {
        return sign_type;
    }

    public void setSign_type(String sign_type) {
        this.sign_type = sign_type;
    }
}
