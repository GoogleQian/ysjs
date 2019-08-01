package com.xiaohe.ysjspt.utils;

import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

/**
 * @author
 * wzq
 */
public class ResponseUtils {
    public static void write(HttpServletResponse response, Object o)throws Exception{
        response.setContentType("text/html;charset=utf-8");
        PrintWriter out=response.getWriter();
        out.println(o.toString());
        out.flush();
        out.close();
    }
}
