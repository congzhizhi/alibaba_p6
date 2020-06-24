package com.luban.netty.粘包拆包.delimiterbasedframedecoder;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;


public class TestServerHandler extends SimpleChannelInboundHandler<String> {

    private  int counter;
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, String body) throws Exception {
//        byte[] req = new byte[buf.readableBytes()];
//        buf.readBytes(req);
//        String body = new String(req,"UTF-8").substring(0,req.length);

        System.out.println("收到："+body+" thie counter is "+ ++counter);

        String currentTime = "QUERY TIME ORDER".equalsIgnoreCase(body)
                ? new java.util.Date(System.currentTimeMillis()).toString():"BAD ORDER";
        currentTime+="\r\n";
        ByteBuf resp = Unpooled.copiedBuffer(currentTime.getBytes());
        channelHandlerContext.writeAndFlush(resp);
    }

    //channel注册事件
    @Override
    public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
        super.channelRegistered(ctx);
    }
    //channel活跃 通道准备就绪事件
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
    }



    //channel取消注册事件
    @Override
    public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {
        super.channelUnregistered(ctx);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }
}
