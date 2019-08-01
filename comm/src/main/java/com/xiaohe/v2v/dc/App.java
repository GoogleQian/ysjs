package com.xiaohe.v2v.dc;

import java.io.IOException;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.xiaohe.hservice.NotifyService;
import com.xiaohe.v2v.server.TcpServer;
import com.xiaohe.v2v.service.HServiceImpl;
import com.xiaohe.v2v.utility.Log4jUtil;

public class App {
	public static void main(String[] args) {

		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("server-spring.xml");
		ctx.start();

		Log4jUtil.info("启动started....");
		
		
		
		NotifyService notifyServer = TcpServer.getNotifyService(); //获得一次远程调用的bean, 让应用成为消费者
		
		/*
		if (notifyServer != null){
			notifyServer.notifyResul("123456", 1);
		}
		*/

		try {
			System.in.read();
			ctx.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
