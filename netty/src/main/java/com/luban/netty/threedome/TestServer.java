package com.luban.netty.threedome;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.buffer.UnpooledByteBufAllocator;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import sun.misc.Signal;
import sun.misc.SignalHandler;

public class TestServer {
    public static void main(String[] args) {
        EventLoopGroup bossGroup=new NioEventLoopGroup();
        EventLoopGroup workGroup=new NioEventLoopGroup();
        try {
            ServerBootstrap serverBootstrap=new ServerBootstrap();
            serverBootstrap.group(bossGroup,workGroup)
                            .channel(NioServerSocketChannel.class)
                            .option(ChannelOption.ALLOCATOR,PooledByteBufAllocator.DEFAULT)
                            .childOption(ChannelOption.ALLOCATOR,PooledByteBufAllocator.DEFAULT)
                            .childOption(ChannelOption.RCVBUF_ALLOCATOR, new AdaptiveRecvByteBufAllocator(64, 4096, 65536))//设置缓冲区大小
                            .handler(new LoggingHandler(LogLevel.DEBUG))
                            .childHandler(new TestServerInitializer());
            ChannelFuture channelFuture = serverBootstrap.bind(8989).sync();
            ChannelFuture closeFuture  = channelFuture.channel().closeFuture().sync();

        }catch (Exception e){
            e.printStackTrace();
        }finally {
            bossGroup.shutdownGracefully();
            workGroup.shutdownGracefully();
        }
    }
    private static String getOSSignalType() {
        return  "SIG"+ (System.getProperties().getProperty("os.name").toLowerCase().startsWith("win")?"USER1":"TERM");
    }
}
