package com.xiaohe.ysjspt.config.aspect;

import com.google.gson.Gson;
import com.xiaohe.ysjspt.entity.Result;
import com.xiaohe.ysjspt.modules.operateLog.entity.OperateLog;
import com.xiaohe.ysjspt.modules.operateLog.service.OperateLogService;
import com.xiaohe.ysjspt.modules.sys.entity.SysUserEntity;
import com.xiaohe.ysjspt.modules.sys.shiro.ShiroUtils;
import com.xiaohe.ysjspt.modules.sys.untils.R;
import com.xiaohe.ysjspt.utils.IPUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Date;

/**
 * @program: ysjspt
 * @description: 操作日志切面
 * @author: Gmq
 * @date: 2018-12-24 17:38
 **/
@Aspect
@Component
public class OperateLogAspect {

    @Autowired
    private OperateLogService operateLogService;

    /**
     * 本地异常日志记录对象
     */
    private static final Logger log = LoggerFactory.getLogger(OperateLogAspect.class);


    /**
     * Controller层切点
     */
    @Pointcut("")
    public void controllerAspect() {
    }


    @Around(value = "@annotation(com.xiaohe.ysjspt.config.aspect.LogOperate)")
    public Object doAfter(ProceedingJoinPoint joinPoint) throws Throwable {
        Object o=null;
        //调用执行目标方法(result为目标方法执行结果)
        long beginTime = System.currentTimeMillis();
        Object res = joinPoint.proceed();
        long time = System.currentTimeMillis() - beginTime;
        if(res instanceof R){
            R result = (R) res;
            //执行时长(毫秒)
            int status = (int)result.get("code");
            saveSysLog(joinPoint, time, status);
            o = result;
        }else if(res instanceof Result){
            Result result = (Result) res;
            //执行时长(毫秒)
            int status = result.getRet();
            saveSysLog(joinPoint, time, status);
            o = result;
        }
        return o;
    }

    /**
     * 保存日志
     *
     * @param joinPoint
     * @param time
     */
    private void saveSysLog(JoinPoint joinPoint, long time, int status) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();

        LogOperate logOperate = method.getAnnotation(LogOperate.class);
        OperateLog operateLog = new OperateLog();

        //操作信息
        operateLog.setOperateInfo(logOperate.description());

        //请求的方法名
        String className = joinPoint.getTarget().getClass().getName();
        String methodName = signature.getName();

        //请求的参数
        Object[] args = joinPoint.getArgs();
        try {
            String params = new Gson().toJson(args);
            operateLog.setReqParam(params);
        } catch (Exception e) {

        }
        //获取到请求的属性
        ServletRequestAttributes attributes =
                (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        //获取到请求头对象
        HttpServletRequest request = attributes.getRequest();
        //设置IP地址
        operateLog.setIp(IPUtils.getIpAddr(request));
        operateLog.setUserAgent(request.getHeader("User-Agent"));
        //请求方式
        operateLog.setReqType(request.getMethod());
        //用户名
        String username = ((SysUserEntity) ShiroUtils.getSubject().getPrincipal()).getUsername();
        operateLog.setUserName(username);
        //状态
        operateLog.setStatus(0==status?1:0);
        //请求路径
        operateLog.setReqUrl(request.getRequestURI());
        operateLog.setReqTime((int)time);
        operateLog.setCreateTime(new Date());
        //保存系统日志
        operateLogService.save(operateLog);
        log.info("保存操作日志......");
    }


}
