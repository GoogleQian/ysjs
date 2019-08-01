package com.xiaohe.v2v.server;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import com.xiaohe.hservice.NotifyService;
import com.xiaohe.v2v.mapper.WaterDataDao;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;


public class TcpServer implements ApplicationContextAware, InitializingBean{

    
    public static ApplicationContext applicationContext;
    private static ServerBootstrap bootstrap;
    
    
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
    	TcpServer.applicationContext = applicationContext;	
	}
	
	public static WaterDataDao getWaterDataDao() {
	    return (WaterDataDao)applicationContext.getBean("waterDataDao"); 
	}
	
	public static NotifyService getNotifyService() {
	    return (NotifyService)applicationContext.getBean("notifyService"); 
	}


	public void afterPropertiesSet() throws Exception {
		// TODO Auto-generated method stub
		new Thread(){
    		public void run(){
				EventLoopGroup boss = new NioEventLoopGroup(1);
		        EventLoopGroup worker = new NioEventLoopGroup();
		        
		        
  		        try {
		            ServerBootstrap bootstrap = new ServerBootstrap();
		            bootstrap.group(boss, worker).channel(NioServerSocketChannel.class)
		                    .childHandler(new ChannelInitializer<SocketChannel>() {
		                        @Override
		                        public void initChannel(SocketChannel channel) throws Exception {
		                                                	
		                        	channel.pipeline().addLast(new ProtocolDecoder(1000))
		                        	.addLast(new NProtocolHandler());
		                        }
		                    })
		                    .option(ChannelOption.SO_BACKLOG, 128)
		                    .childOption(ChannelOption.SO_KEEPALIVE, true);

		            ChannelFuture ch = bootstrap.bind(14167).sync();
			      
			        
					ch.channel().closeFuture().sync();
						
		        } catch (Exception e){
		        	
		        }
		        finally {
		        	boss.shutdownGracefully();
		        	worker.shutdownGracefully();
		        }    					        
		        
		        
		        /*
		        try {
			        bootstrap = new ServerBootstrap();
			        bootstrap.group(boss,worker).channel(NioServerSocketChannel.class).handler(new LoggingHandler(LogLevel.INFO))
			                .childHandler(new ChannelInitializer<SocketChannel>() {
			            @Override
			            public void initChannel(SocketChannel ch) throws Exception {
			                ChannelPipeline p = ch.pipeline();
			                p.addLast(new ConsoleHandler());
			            }
			        });
			
			        // Start the client.
			        ChannelFuture ch = bootstrap.bind(14167).sync();
			        // Wait until the connection is closed.
			        
						ch.channel().closeFuture().sync();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				*/
		    }
    	}.start();
	}

	
}


