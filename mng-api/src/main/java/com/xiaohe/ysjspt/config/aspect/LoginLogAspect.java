package com.xiaohe.ysjspt.config.aspect;

import com.xiaohe.ysjspt.entity.Result;
import com.xiaohe.ysjspt.modules.loginLog.entity.LoginLog;
import com.xiaohe.ysjspt.modules.loginLog.service.LoginLogService;
import com.xiaohe.ysjspt.utils.IPUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * @program: ysjspt
 * @description: 登陆切面类
 * @author: Gmq
 * @date: 2018-12-24 16:48
 **/
@Component
@Aspect
public class LoginLogAspect {

    @Autowired
    private LoginLogService loginLogService;
    //使用org.slf4j.Logger,这是spring实现日志的方法
    private final static Logger logger = LoggerFactory.getLogger(LoginLogAspect.class);

    @Around(value = "@annotation(com.xiaohe.ysjspt.config.aspect.LogLogin)")
    public Result around(ProceedingJoinPoint joinPoint) throws Throwable {
        //调用执行目标方法(result为目标方法执行结果)
        long beginTime = System.currentTimeMillis();
        Result result = (Result) joinPoint.proceed();
        //执行时长(毫秒)
        long time = System.currentTimeMillis() - beginTime;
        saveSysLog(joinPoint, time, result.getRet());
        return result;
    }

    private void saveSysLog(JoinPoint joinPoint, long time, Integer status) {
        //获取到请求的属性
        ServletRequestAttributes attributes =
                (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        //获取到请求头对象
        HttpServletRequest request = attributes.getRequest();
        LoginLog loginLog = new LoginLog();
        loginLog.setUserName(request.getParameter("username"));
        loginLog.setOperateType(1);
        loginLog.setIp(IPUtils.getIpAddr(request));
        String opreateType = "登入";
        loginLog.setUserAgent(request.getHeader("User-Agent"));
        loginLog.setStatus(0==status?1:0);
        loginLog.setCreateTime(new Date());
        //存入日志表
        loginLogService.save(loginLog);
        logger.info("记录登陆日志......");
    }

}

