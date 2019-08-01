package com.xiaohe.ysjspt.log;

import java.text.SimpleDateFormat;
import java.util.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import com.xiaohe.ysjspt.service.OperationLogService;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * @author
 * wzq
 */

@Aspect
@Component
public class SystemLogAspect {

    /**
     * 本地异常日志记录对象
     */
    private static final Logger log = LoggerFactory.getLogger(SystemLogAspect.class);

    @Autowired
    OperationLogService operationLogService;

    /**
     * Controller层切点
     */
    @Pointcut("@annotation(com.xiaohe.ysjspt.log.ControllerLog)")
    public void controllerAspect() {
    }

    @After(value = "controllerAspect()")
    public void doAfter(JoinPoint joinPoint) {
        log.info("进入日志系统————————————");
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        HttpSession session = request.getSession();
        Object userName = session.getAttribute("username");
        log.info("userName:" + userName);
        Enumeration enu = request.getParameterNames();
        Map parameterMap = request.getParameterMap();
        Collection paramValue = parameterMap.values();
        Iterator iterator = paramValue.iterator();

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    String paraName = "";
                    String description = getControllerMethodDescription(joinPoint);
                    Object[] param = getControllerMethodArguments(joinPoint);

                    for (int i = 0; i < paramValue.size(); i++) {
                        while (iterator.hasNext()) {
                            boolean ret = false;
                            if(iterator.hasNext()) {
                                Object[] object =(Object[]) iterator.next();
                                String value =object[0].toString();
                                paraName += enu.nextElement() + "=" + value + "&";
                                ret = true;
                                break;
                            }
                            if (ret) {
                                break;
                            }
                        }
                    }

                    if(!paraName.isEmpty()) {
                        paraName = paraName.substring(0, paraName.length() - 1);
                    }
                    Date dNow = new Date();
                    String loginName = "";
                    SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    log.info("description:" + description);
                    log.info("param:" + paraName);
                    log.info("date:" + ft.format(dNow));
                    if (userName != null) {
                        loginName = userName.toString();
                    }

                    operationLogService.add(loginName, description, paraName, ft.format(dNow));

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }


    /**
     * 通过反射获取传入的方法
     * @param joinPoint
     * @return
     * @throws Exception
     */
    public static String getControllerMethodDescription(JoinPoint joinPoint) throws Exception {
        String targetName = joinPoint.getTarget().getClass().getName();
        //获取方法名
        String methodName = joinPoint.getSignature().getName();
        return targetName + "." + methodName;
    }

    /**
     * 通过反射获取参入的参数
     * @param joinPoint
     * @return
     * @throws Exception
     */
    public static Object[] getControllerMethodArguments(JoinPoint joinPoint) throws Exception {
        //获取参数值
        Object[] arguments = joinPoint.getArgs();
        return arguments;
    }
}