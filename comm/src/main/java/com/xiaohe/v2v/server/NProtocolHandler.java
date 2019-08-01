package com.xiaohe.v2v.server;

import java.net.SocketAddress;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;


public class NProtocolHandler extends  SimpleChannelInboundHandler<Object> {
	
	private static final Logger logger = LoggerFactory.getLogger(NProtocolHandler.class);
	
	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		Channel ch = ctx.channel();
		
		SocketAddress  address = ch.remoteAddress();
		
		String linkInfo = address.toString();
		
		logger.info("connect from: " + linkInfo);
	        
	}

  
    
	@Override
	protected void messageReceived(ChannelHandlerContext ctx, Object msg) throws Exception {
		
		if (msg instanceof MsgFrame){
		
			MsgFrame theMsg = (MsgFrame)msg;
			theMsg.reactor();
			
		}

	}
    
    

}
