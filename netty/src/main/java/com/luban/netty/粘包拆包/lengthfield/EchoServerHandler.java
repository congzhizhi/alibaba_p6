package com.luban.netty.粘包拆包.lengthfield;
import java.nio.charset.Charset;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class EchoServerHandler extends ChannelInboundHandlerAdapter {
	private int counter = 0;
	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg)
			throws Exception {
	
		ByteBuf body = (ByteBuf)msg;
		if(body.readableBytes() <= 0){
			ctx.fireChannelRead(msg);
		}
		CCMessageHeader recHd = new CCMessageHeader();
		int nLength = body.readInt();
		int nType = body.readInt();
		int nDataSize = body.readableBytes();
		byte [] aa = new byte[nDataSize];
		body.readBytes(aa);
		String myMsg = new String(aa,Charset.forName("utf-8"));
		
		
		System.out.println("this is "+ ++ counter +" times receive client:[" + myMsg +"]" +" Type["+ nType +"]");
	
		CCMessageHeader hd = new CCMessageHeader();
		hd.setType(2);
		hd.setData("server data...");
		ByteBuf echo = Unpooled.directBuffer();
		echo.writeBytes(hd.getMessageFlag());
		echo.writeInt(hd.getLength());
		echo.writeInt(hd.getType());
		echo.writeCharSequence(hd.getData(), Charset.forName("utf-8"));
		ctx.writeAndFlush(echo);
	}

	@Override
	public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
		ctx.flush();
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
			throws Exception {
		cause.printStackTrace();
		ctx.close();
	}

}
