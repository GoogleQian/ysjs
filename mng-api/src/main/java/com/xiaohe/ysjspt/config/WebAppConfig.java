package com.xiaohe.ysjspt.config;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


/**
 * @author
 * Administrator
 */

@EnableScheduling
@EnableAutoConfiguration
@Configuration
public class WebAppConfig extends WebMvcConfigurerAdapter {

	@Value("${cbs.imagesPath}")
	private String mImagesPath;

	public final static String SESSION_KEY="username";


	@Bean
	public AccessInterceptor getAccessInterceptor() {
		return new AccessInterceptor();
	}


    
    /**
     * 配置拦截器
     * @author lance
     * @param registry
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
		// addPathPatterns 添加拦截
		// excludePathPatterns 排除拦截
    	/*registry.addInterceptor(getTokenInterceptor())
				.addPathPatterns("/**")
				.excludePathPatterns("/user/**")
				.excludePathPatterns("/page/get_page_content")
				.excludePathPatterns("/device/get_history_data")
				.excludePathPatterns("/device/get_latest_data");*/

		registry.addInterceptor(getAccessInterceptor())
				.addPathPatterns("/**")

				.excludePathPatterns("/**/**")//web端
				.excludePathPatterns("/track/**")//web端
				.excludePathPatterns("/mng/login")//web端
				.excludePathPatterns("/drive/record")//web端
				.excludePathPatterns("/drive/track")//web端
				.excludePathPatterns("/**/export")//web端
				.excludePathPatterns("/travel_data/export")//web端
				.excludePathPatterns("/orders/export")//web端
				.excludePathPatterns("/mng/logout")//web端
				.excludePathPatterns("/wstest/**")//web端
				.excludePathPatterns("/export/**")//web端
				.excludePathPatterns("/topic/**")//排除拦截(socket接口)
				.excludePathPatterns("/**/remote_control")//排除拦截(socket接口)
				.excludePathPatterns("/**/vehicle_state")//排除拦截(socket接口)
				.excludePathPatterns("/my-websocket")//排除拦截(socket接口)
				.excludePathPatterns("/my-websocket/websocket")//排除拦截(socket接口)
				.excludePathPatterns("/app/**")//排除拦截(app接口)
				.excludePathPatterns("/user/**")//排除拦截(app接口)
				.excludePathPatterns("/orders/find_by_phone_number")//排除拦截(app接口)
				.excludePathPatterns("/vehicle_detection/get_info_by_vehicle_number")//排除拦截(app接口)
				.excludePathPatterns("/app/vih/info")//排除拦截(app接口)
				.excludePathPatterns("/branch/get_all_branch")//排除拦截(app接口)
				.excludePathPatterns("/sys_notice/get_system_msg");//排除拦截(app接口)



//		InterceptorRegistration addInterceptor = registry.addInterceptor(getSecurityInterceptor());

		// 用户登录
//		addInterceptor.excludePathPatterns("/mng/login");

		// APP
//		addInterceptor.excludePathPatterns("/user/**");

//		addInterceptor.addPathPatterns("/**");


//		super.addInterceptors(registry);
	}

	@Bean
	public SecurityInterceptor getSecurityInterceptor(){
		return new SecurityInterceptor();
	}


	private class SecurityInterceptor extends HandlerInterceptorAdapter {


		@Override
		public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws IOException {


				/*HttpSession session = request.getSession();

			//判断是否已有该用户登录的session
			if (session.getAttribute(SESSION_KEY) != null) {
				return true;
			}

			String strResponse = "{\"ret\":6002, \"msg\":\"会话已经过期\"}";

			response.setCharacterEncoding("UTF-8");
			response.setContentType("application/json; charset=utf-8");
			PrintWriter out = null;
			try {
				out = response.getWriter();

				out.append(strResponse);

			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				if (out != null) {
					out.close();
				}
			}
			return  false;		*/

			return true;
		}
	}


	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		String cbsImg = "${cbs.imagesPath}";
		String jar = ".jar";
		String cls = "classes";
		if("".equals(mImagesPath) ||cbsImg.equals(mImagesPath) ){
			String imagesPath = WebAppConfig.class.getClassLoader().getResource("").getPath();
			if(imagesPath.indexOf(jar)>0){
				imagesPath = imagesPath.substring(0, imagesPath.indexOf(".jar"));
			}else if(imagesPath.indexOf(cls)>0){
				imagesPath = "file:"+imagesPath.substring(0, imagesPath.indexOf("classes"));
			}
			imagesPath = imagesPath.substring(0, imagesPath.lastIndexOf("/"))+"/images/";
			mImagesPath = imagesPath;
		}
		LoggerFactory.getLogger(WebAppConfig.class).info("imagesPath="+mImagesPath);
		registry.addResourceHandler("/images/**").addResourceLocations(mImagesPath);
		// TODO Auto-generated method stub
		super.addResourceHandlers(registry);
	}





}