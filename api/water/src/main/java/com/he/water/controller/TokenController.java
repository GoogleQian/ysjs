package com.he.water.controller;

import com.he.water.entity.Result;
import com.he.water.utils.SignUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @author
 */
@RestController
@RequestMapping(value = "/sellWater")
public class TokenController {
    private static Logger log = LoggerFactory.getLogger(TokenController.class);

    @RequestMapping(value = "/checkToken", method = RequestMethod.GET)
    public Result check(HttpServletRequest request, HttpServletResponse response) {
        //微信加密签名，signature结合了开发者填写的token参数和请求中的timestamp，nonce参数
        String signature = request.getParameter("signature");
        //时间戳
        String timestamp = request.getParameter("timestamp");
        //随机数
        String nonce = request.getParameter("nonce");
        //随机字符串
        String echostr = request.getParameter("echostr");

        if (SignUtil.checkSignature(signature, timestamp, nonce)) {
            log.info("[signature: " + signature + "]<-->[timestamp: " + timestamp + "]<-->[nonce: " + nonce + "]<-->[echostr: " + echostr + "]");
            PrintWriter printWriter = null;
            try {
                printWriter = response.getWriter();
                printWriter.print(echostr);
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (printWriter != null) {
                    printWriter.close();
                }

            }
        }
        return new Result().ok();
    }

//    @RequestMapping(value = "/getPermission", method = RequestMethod.GET)
//    public Result getPermission(HttpServletRequest request, HttpServletResponse response) {
//        //微信加密签名，signature结合了开发者填写的token参数和请求中的timestamp，nonce参数
//        String signature = request.getParameter("signature");
//        //时间戳
//        String timestamp = request.getParameter("timestamp");
//        //随机数
//        String nonce = request.getParameter("nonce");
//        //随机字符串
//        String echostr = request.getParameter("echostr");
//
//        if (SignUtil.checkSignature(signature, timestamp, nonce)) {
//            log.info("[signature: " + signature + "]<-->[timestamp: " + timestamp + "]<-->[nonce: " + nonce + "]<-->[echostr: " + echostr + "]");
//            PrintWriter printWriter = null;
//            try {
//                printWriter = response.getWriter();
//                printWriter.print(echostr);
//            } catch (IOException e) {
//                e.printStackTrace();
//            } finally {
//                if (printWriter != null) {
//                    printWriter.close();
//                }
//
//            }
//        }
//        return new Result().ok();
//    }


}
