package com.xiaohe.v2v.server;


import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.xiaohe.v2v.utility.ByteUtility;
import com.xiaohe.v2v.utility.Log4jUtil;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.ByteToMessageDecoder;



public class ProtocolDecoder extends ByteToMessageDecoder  {
	
		private static final Logger logger = LoggerFactory.getLogger(ProtocolDecoder.class);
	
	   
//	    private final ByteOrder byteOrder;
	    private final int maxFrameLength;

	    
	 
	    
	    @Override
	    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
	        //channel失效，从Map中移除
	   	
	    	logger.info("disconnect ......" + ctx.channel().remoteAddress());
			ChanneRegistry.remove((SocketChannel)ctx.channel());
	    }

	    @Override
	    public void channelActive(ChannelHandlerContext ctx) throws Exception {
	
	    	
	    	logger.info("connect from ......" + ctx.channel().remoteAddress());
	    }	    

	
	    public ProtocolDecoder(int maxFrameLength){
	    	this.maxFrameLength = maxFrameLength;
	    }

	   
	 

	    @Override
	    protected final void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
	      
	    	int length = in.readableBytes();
	    			
	    	if (length < 5) { 
                return; //小于头部8字节不做处理  
            }  
	    	
	    	if (length > maxFrameLength){
	    		in.discardReadBytes();	
	    	}
	    	
	    	byte head = in.getByte(0);
	    	byte tail = in.getByte(length - 1);
	    	
	    	// 遇到了一帧  && ((tail & 0xff) == 0xe7
	    	if (((head & 0xff) == 0xFE)){
	    		
	    		byte[] byteArray = new byte[length];
	    		
	    		byte[] src = in.readBytes(length).array();
	    	   	System.arraycopy(src, 0, byteArray, 0, length);
	    		
	        	 		      	      	
		    	MsgFrame msg = new MsgFrame(ctx.channel(), byteArray);		    	
		    	out.add(msg);  		    		 
		    	
	    	}
	    	
	
	    }


}
