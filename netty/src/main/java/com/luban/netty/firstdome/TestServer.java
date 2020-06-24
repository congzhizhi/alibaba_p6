package com.luban.netty.firstdome;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

public class TestServer {
    public static void main(String[] args) {

        EventLoopGroup bossGroup=new NioEventLoopGroup(2);  //接收客户端连接的线程组
        EventLoopGroup workGroup=new NioEventLoopGroup(); //真正处理读写事件的线程组   16,CPU核数的2倍

        try {
            ServerBootstrap serverBootstrap=new ServerBootstrap();
            serverBootstrap.group(bossGroup,workGroup)
                    .channel(NioServerSocketChannel.class)  //服务端用什么通道
                    .option(ChannelOption.SO_BACKLOG,128) //设置线程队列中等待连接的个数
                    .childHandler(new TestServerLnitializer()); //已经连接上来的客户端
            ChannelFuture channelFuture = serverBootstrap.bind(8989).sync();
            channelFuture.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            bossGroup.shutdownGracefully();
            workGroup.shutdownGracefully();
        }
    }
}
