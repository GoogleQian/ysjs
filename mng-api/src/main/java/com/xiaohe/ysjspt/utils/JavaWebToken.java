package com.xiaohe.ysjspt.utils;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.crypto.spec.SecretKeySpec;
import javax.servlet.http.HttpServletRequest;
import javax.xml.bind.DatatypeConverter;
import java.security.Key;
import java.util.Date;
import java.util.Map;

/**
 * @program: ysjsApi
 * @description: jwt
 * @author: Gmq
 * @date: 2018-12-11 09:41
 **/
public class JavaWebToken {
    private static Logger log = LoggerFactory.getLogger(JavaWebToken.class);

    //该方法使用HS256算法和Secret:bankgl生成signKey
    private static Key getKeyInstance() {
        //We will sign our JavaWebToken with our ApiKey secret
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
        byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary("bankgl");
        Key signingKey = new SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.getJcaName());
        return signingKey;
    }

    //使用HS256签名算法和生成的signingKey最终的Token,claims中是有效载荷
    public static String createJavaWebToken(Map<String, Object> claims) {
        // 过期时间，单位毫秒
        long EXPIRE_TIME = 20 * 60 * 1000;
        // 生成过期时间
        Date date = new Date(System.currentTimeMillis() + EXPIRE_TIME);
        return Jwts.builder().setExpiration(date).setClaims(claims).signWith(SignatureAlgorithm.HS256, getKeyInstance()).compact();
    }

    //解析Token，同时也能验证Token，当验证失败返回null
    public static Map<String, Object> parserJavaWebToken(String jwt) {
        try {
            Map<String, Object> jwtClaims =
                    Jwts.parser().setSigningKey(getKeyInstance()).parseClaimsJws(jwt).getBody();
            return jwtClaims;
        } catch (Exception e) {
            log.error("json web token verify failed");
            return null;
        }
    }

    /**
     * 获取维修工ID
     *
     * @return
     */
    public static Integer getRepairerId() {

        //获取到请求的属性
        ServletRequestAttributes attributes =
                (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        //获取到请求头对象
        HttpServletRequest request = attributes.getRequest();
        String requestHeader = request.getHeader("token");
        String token = requestHeader;
        Integer repairerId;
        if (token != null) {
            repairerId = (Integer) JavaWebToken.parserJavaWebToken(token).get("userid");
        } else {
            return null;
        }
        return repairerId;
    }
}
