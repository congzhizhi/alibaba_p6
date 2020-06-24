package com.luban.netty.threedome;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.buffer.UnpooledByteBufAllocator;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;
import io.netty.util.concurrent.GlobalEventExecutor;


public class TestServerHandler extends SimpleChannelInboundHandler<String>{

    public static ChannelGroup group=new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

    //channel读取数据
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, String s) throws Exception {
        Channel channel = channelHandlerContext.channel();
        if (channel.alloc() instanceof UnpooledByteBufAllocator){
            System.out.println("UnpooledByteBufAllocator");
        }
        channel.writeAndFlush("收到啦");

        group.forEach(ch->{
            if(channel!=ch){
                ChannelFuture future= ch.writeAndFlush(channel.remoteAddress()+"："+s+"\r\n");
                future.addListener(new GenericFutureListener<Future<? super Void>>() {
                    @Override
                    public void operationComplete(Future<? super Void> future) throws Exception {
                        System.out.println("我写完了");
                    }
                });
            }
        });
        System.out.println("接收到的消息"+s);
        channelHandlerContext.fireChannelRead(s);
    }

    //channel 助手类(拦截器)的添加
    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        System.out.println(1);
        Channel channel = ctx.channel();
        group.writeAndFlush(channel.remoteAddress()+"加入\n");
        group.add(channel);
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        System.out.println("读完啦");
    }

    //channel注册事件
    @Override
    public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
        System.out.println(2);
        System.out.println("channelRegistered");
        super.channelRegistered(ctx);
    }
    //channel活跃 通道准备就绪事件
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println(3);
        Channel channel = ctx.channel();
        System.out.println(channel.remoteAddress()+"上线\n");
        System.out.println(TestServerHandler.group);
        System.out.println("在线人数"+group.size());
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        super.userEventTriggered(ctx, evt);
    }

    //channel不活跃  通道关闭事件
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        System.out.println(4);
        Channel channel = ctx.channel();
        System.out.println(channel.remoteAddress()+"下线\n");
    }

    //channel取消注册事件
    @Override
    public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {
        System.out.println(5);
        System.out.println("channelUnregistered");
        super.channelUnregistered(ctx);
    }
    //channel 助手类(拦截器)移除
    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        System.out.println(6);
        Channel channel = ctx.channel();
        group.writeAndFlush(channel.remoteAddress()+"离开\n");
    }
    //发生异常回调
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        System.out.println("出现异常");
        cause.printStackTrace();
        ctx.close();
    }

}
