package com.luban.netty.threedome;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;
import io.netty.util.concurrent.GlobalEventExecutor;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class TestClient {

    public static void main(String[] args) {
        List<Channel> list = new ArrayList<>();
        int poolSize = 1;
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        ChannelFuture channelFuture = null;
        try {
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(bossGroup)
                    .channel(NioSocketChannel.class)
                    //客户端连接超时设置
                    .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 1000)
                    .option(ChannelOption.TCP_NODELAY, true)
                    .handler(new TestClientInitializer());
            for (int i = 0; i < poolSize; i++) {
                channelFuture = bootstrap.connect("127.0.0.1", 8989);
                channelFuture.addListener(new GenericFutureListener<Future<? super Void>>() {
                    @Override
                    public void operationComplete(Future<? super Void> future) throws Exception {
                        System.out.println("连接成功了吗？"+future.isSuccess());
                    }
                });
                list.add(channelFuture.channel());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void init(int poolSize) {


    }
}
