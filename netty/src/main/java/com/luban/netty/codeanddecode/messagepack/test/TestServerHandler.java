package com.luban.netty.codeanddecode.messagepack.test;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;


public class TestServerHandler extends SimpleChannelInboundHandler<Object> {

    private  int counter;
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, Object user) throws Exception {
        System.out.println("服务端接收数据："+user);
        channelHandlerContext.writeAndFlush(user);
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
        cause.printStackTrace();
        ctx.close();
    }
}
