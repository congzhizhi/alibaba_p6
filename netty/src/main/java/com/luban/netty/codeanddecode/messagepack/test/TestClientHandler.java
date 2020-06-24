package com.luban.netty.codeanddecode.messagepack.test;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class TestClientHandler extends SimpleChannelInboundHandler<Object> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Object object) throws Exception {

        System.out.println("i have received message : "+object);
//        ctx.writeAndFlush(ctx);
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        int sendNumber = 100;
        User user = null;
        for (int i = 0; i < sendNumber; i++) {
            user = new User();
            user.setAge(10+i);
            user.setUserName("丛治志——>"+i);
            ctx.write(user);
        }
        ctx.flush();

    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }



}
