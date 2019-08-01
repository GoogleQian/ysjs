package com.xiaohe.ysjspt.config.aspect;

import com.xiaohe.ysjspt.entity.Result;
import com.xiaohe.ysjspt.modules.cache.CaptchaService;
import com.xiaohe.ysjspt.utils.JavaWebToken;
import org.apache.commons.lang.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

/**
 * @program: ysjsApi
 * @description: 维修工AOP
 * @author: Gmq
 * @date: 2018-12-11 10:11
 **/

@Component
@Aspect
public class RequestAspect {

    @Autowired
    private CaptchaService captchaService;
    @Resource
    HttpServletResponse response;
    //使用org.slf4j.Logger,这是spring实现日志的方法
    private final static Logger logger = LoggerFactory.getLogger(RequestAspect.class);

    /**
     * 表示在执行被@MonitorRequest注解修饰的方法之前 会执行doBefore()方法
     *
     * @param joinPoint 连接点，就是被拦截点
     */
    @Before(value = "@annotation(com.xiaohe.ysjspt.config.aspect.RepairerRequest)")
    public void doBefore(JoinPoint joinPoint) throws IOException {
        Result result = new Result();
        //获取到请求的属性
        ServletRequestAttributes attributes =
                (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        //获取到请求头对象
        HttpServletRequest request = attributes.getRequest();
        String token = request.getHeader("token");
        response.setCharacterEncoding("UTF-8");
        if (token != null) {
            Map<String, Object> map = JavaWebToken.parserJavaWebToken(token);
            if (CollectionUtils.isEmpty(map)) {
                response.sendError(401);
                return;
            }
            boolean flag = StringUtils.isBlank(captchaService.getRepairer((int) map.get("userid")));
            if (flag) {
                response.sendError(401);
            }
        } else {
            response.sendError(401);
        }

    }

}
