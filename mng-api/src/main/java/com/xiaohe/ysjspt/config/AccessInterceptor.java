package com.xiaohe.ysjspt.config;



import com.xiaohe.ysjspt.service.DevSysUserService;
import io.jsonwebtoken.Jwts;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * this is spring interceptor
 *
 **/
/**
 * @author
 * Administrator
 */
@Component
public class AccessInterceptor implements HandlerInterceptor {

    @Autowired
    DevSysUserService devSysUserService;


    /**
     * 控制器方法处理之前
     *
     * @param httpServletRequest
     * @param httpServletResponse
     * @param handler
     * @return
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object handler) throws Exception {

        String token = httpServletRequest.getHeader("Authorization");
       // System.out.println("preHandle_header=" + token);

        if (StringUtils.isEmpty(token)) {
            illegalRequest(httpServletResponse, "token无效");
            return false;
        } else {
            String username = Jwts.parser()
                    .setSigningKey("MyJwtSecret")
                    .parseClaimsJws(token)
                    .getBody()
                    .getSubject();

            String localToken = devSysUserService.fineTokenByUsername(username);
            if (!localToken.equals(token)) {
                illegalRequest(httpServletResponse, "token无效");
                return false;
            }
        }

        return true;
    }

    /**
     * 控制器方法处理之后
     * 控制器方法调用不抛异常调用
     *
     * @param httpServletRequest
     * @param httpServletResponse
     * @param modelAndView
     * @throws Exception
     */
    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object handler, ModelAndView modelAndView) throws Exception {

    }

    /**
     * 控制器方法抛不抛异常都会被调用
     *
     * @param httpServletRequest
     * @param httpServletResponse
     * @param o
     * @param e
     * @throws Exception
     */
    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {
    }

    private void illegalRequest(HttpServletResponse response, String message) throws IOException {
        response.setStatus(403);
        response.setContentType("application/json;charset=UTF-8");
        response.setCharacterEncoding("UTF-8");
        Map<String, Object> result = new HashMap<>(16);
        result.put("ret", 10002);
        result.put("msg", message);
        response.getWriter().write(JSONObject.fromObject(result).toString());
        response.getWriter().flush();
        response.getWriter().close();
    }
}
