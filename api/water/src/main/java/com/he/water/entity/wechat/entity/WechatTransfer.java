package com.he.water.entity.wechat.entity;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * 微信企业转账
 *
 * @author hzh
 * @date 2018/7/20
 */
@XmlRootElement(name = "xml")
public class WechatTransfer {
    private String mch_appid;//  商户账号appid  是 wx8888888888888888 String 申请商户号的appid或商户号绑定的appid
    private String mchid; //商户号  是 1900000109 String(32) 微信支付分配的商户号
    private String device_info;//  设备号  否 013467007045764 String(32) 微信支付分配的终端设备号
    private String nonce_str;//  随机字符串  是 5K8264ILTKCH16CQ2502SI8ZNMTM67VS String(32) 随机字符串，不长于32位
    private String sign;// 签名  是 C380BEC2BFD727A4B6845133519F3AD6 String(32) 签名，详见签名算法
    private String partner_trade_no;//商户订单号  是 10000098201411111234567890 String 商户订单号，需保持唯一性
    private String openid;//       用户openid openid 是 oxTWIuGaIt6gTKsQRLau2M0yL16E String 商户appid下，某用户的openid
    private String check_name;//校验用户姓名选项  是 FORCE_CHECK String NO_CHECK：不校验真实姓名 FORCE_CHECK：强校验真实姓名
    private String re_user_name;//    收款用户姓名  可选 王小王 String 收款用户真实姓名。   如果check_name设置为FORCE_CHECK，则必填用户真实姓名
    private int amount;// 金额  是 10099 int 企业付款金额，单位为分
    private String desc;// 企业付款描述信息 desc 是 理赔 String 企业付款操作说明信息。必填。
    private String spbill_create_ip;//Ip地址  是 192.168.0.1 String(32) 该IP同在商户平台设置的IP白名单中的IP没有关联，该IP可传用户端或者服务端的IP。

    @XmlRootElement(name = "xml")
    public static class Response {
        private String return_code;//  返回状态码      此字段是通信标识，非交易标识，交易是否成功需要查看result_code来判断
        private String return_msg;// 返回信息   签名失败 ，如非空，为错误原因
        //以下字段在return_code为SUCCESS的时候有返回
        private String mch_appid;//商户appid   申请商户号的appid或商户号绑定的appid（企业号corpid即为此appId）
        private String mchid;// 商户号  微信支付分配的商户号
        private String device_info;//   设备号   微信支付分配的终端设备号，
        private String nonce_str;//随机字符串  随机字符串，不长于32位
        private String result_code;//业务结果  SUCCESS/FAIL，注意：当状态为FAIL时，存在业务结果未明确的情况，所以如果状态FAIL，请务必再请求一次查询接口[请务必关注错误代码（err_code字段），通过查询查询接口确认此次付款的结果。]，以确认此次付款的结果。
        private String err_code;// 错误代码   错误码信息，注意：出现未明确的错误码时（SYSTEMERROR等）[出现系统错误的错误码时（SYSTEMERROR），请务必用原商户订单号重试，或通过查询接口确认此次付款的结果。]，请请务必再请求一次查询接口，以确认此次付款的结果。
        private String err_code_des;// 错误代码描述  结果信息描述
        //以下字段在return_code 和result_code都为SUCCESS的时候有返回
        private String partner_trade_no;//  商户订单号   商户订单号，需保持历史全局唯一性(只能是字母或者数字，不能包含有符号)
        private String payment_no;  //微信订单号  String 企业付款成功，返回的微信订单号
        private String payment_time;// 微信支付成功时间  企业付款成功时间

        public Response(String return_code) {
            this.return_code = return_code;
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

        public String getMch_appid() {
            return mch_appid;
        }

        public void setMch_appid(String mch_appid) {
            this.mch_appid = mch_appid;
        }

        public String getMchid() {
            return mchid;
        }

        public void setMchid(String mchid) {
            this.mchid = mchid;
        }

        public String getDevice_info() {
            return device_info;
        }

        public void setDevice_info(String device_info) {
            this.device_info = device_info;
        }

        public String getNonce_str() {
            return nonce_str;
        }

        public void setNonce_str(String nonce_str) {
            this.nonce_str = nonce_str;
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

        public String getPartner_trade_no() {
            return partner_trade_no;
        }

        public void setPartner_trade_no(String partner_trade_no) {
            this.partner_trade_no = partner_trade_no;
        }

        public String getPayment_no() {
            return payment_no;
        }

        public void setPayment_no(String payment_no) {
            this.payment_no = payment_no;
        }

        public String getPayment_time() {
            return payment_time;
        }

        public void setPayment_time(String payment_time) {
            this.payment_time = payment_time;
        }
    }


    public String getMch_appid() {
        return mch_appid;
    }

    public void setMch_appid(String mch_appid) {
        this.mch_appid = mch_appid;
    }

    public String getMchid() {
        return mchid;
    }

    public void setMchid(String mchid) {
        this.mchid = mchid;
    }

    public String getDevice_info() {
        return device_info;
    }

    public void setDevice_info(String device_info) {
        this.device_info = device_info;
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

    public String getPartner_trade_no() {
        return partner_trade_no;
    }

    public void setPartner_trade_no(String partner_trade_no) {
        this.partner_trade_no = partner_trade_no;
    }

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public String getCheck_name() {
        return check_name;
    }

    public void setCheck_name(String check_name) {
        this.check_name = check_name;
    }

    public String getRe_user_name() {
        return re_user_name;
    }

    public void setRe_user_name(String re_user_name) {
        this.re_user_name = re_user_name;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getSpbill_create_ip() {
        return spbill_create_ip;
    }

    public void setSpbill_create_ip(String spbill_create_ip) {
        this.spbill_create_ip = spbill_create_ip;
    }
}
