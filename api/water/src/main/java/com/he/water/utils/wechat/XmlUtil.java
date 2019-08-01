package com.he.water.utils.wechat;


import com.he.water.entity.wechat.entity.WechatRefundNotify;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.*;

public class XmlUtil {


    /**
     * XML转对象
     *
     * @param xmlStr
     * @param t
     * @return
     */
    @SuppressWarnings("unchecked")
    public static <T> T xmlToBean(String xmlStr, Class<T> t) {
        try {
            JAXBContext context = JAXBContext.newInstance(t);
            Unmarshaller unmarshaller = context.createUnmarshaller();
            T ts = (T) unmarshaller.unmarshal(new StringReader(xmlStr));
            return ts;
        } catch (JAXBException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * XML转对象
     *
     * @param t
     * @return
     */
    @SuppressWarnings("unchecked")
    public static <T> T xmlToBean(InputStream input, Class<T> t) {
        try {
            JAXBContext context = JAXBContext.newInstance(t);
            Unmarshaller unmarshaller = context.createUnmarshaller();
            T ts = (T) unmarshaller.unmarshal(new InputStreamReader(input,
                    "UTF-8"));
            return ts;
        } catch (JAXBException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 对象转XML
     *
     * @param out
     * @param to
     */
    public static String beanToXml(ByteArrayOutputStream out, Object to) {
        try {
            JAXBContext context = JAXBContext.newInstance(to.getClass());
            Marshaller marshaller = context.createMarshaller();
            marshaller.marshal(to, out);
            return new String(out.toByteArray(), "UTF-8");
        } catch (JAXBException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String getXml(String returnCode, String returnMsg) {
        return "<xml><return_code><![CDATA[" + returnCode + "]]></return_code><return_msg><![CDATA[" + returnMsg + "]]></return_msg></xml> ";
    }

    public static void main(String[] args) {
        String ss = "<root>\n" +
                "<out_refund_no><![CDATA[RN1533011503481]]></out_refund_no>\n" +
                "<out_trade_no><![CDATA[he1533010798929]]></out_trade_no>\n" +
                "<refund_account><![CDATA[REFUND_SOURCE_RECHARGE_FUNDS]]></refund_account>\n" +
                "<refund_fee><![CDATA[1]]></refund_fee>\n" +
                "<refund_id><![CDATA[50000107632018073105822360876]]></refund_id>\n" +
                "<refund_recv_accout><![CDATA[支付用户零钱]]></refund_recv_accout>\n" +
                "<refund_request_source><![CDATA[API]]></refund_request_source>\n" +
                "<refund_status><![CDATA[SUCCESS]]></refund_status>\n" +
                "<settlement_refund_fee><![CDATA[1]]></settlement_refund_fee>\n" +
                "<settlement_total_fee><![CDATA[1]]></settlement_total_fee>\n" +
                "<success_time><![CDATA[2018-07-31 12:31:49]]></success_time>\n" +
                "<total_fee><![CDATA[1]]></total_fee>\n" +
                "<transaction_id><![CDATA[4200000134201807311825928913]]></transaction_id>\n" +
                "</root>";

ss=ss.replaceAll("root", "xml");
       System.out.println(xmlToBean(ss, WechatRefundNotify.Response.class));
    }
}
