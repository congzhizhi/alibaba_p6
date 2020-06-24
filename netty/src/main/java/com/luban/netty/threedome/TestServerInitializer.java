package com.luban.netty.threedome;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.*;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.timeout.IdleStateHandler;
import io.netty.util.CharsetUtil;


public class TestServerInitializer extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {
        ChannelPipeline pipeline = socketChannel.pipeline();

        pipeline.addLast(new LineBasedFrameDecoder(4096));

//        pipeline.addLast(new FixedLengthFrameDecoder(10));
//        pipeline.addLast(new DelimiterBasedFrameDecoder(4096, Delimiters.lineDelimiter())); //基于分隔符的解码器
        pipeline.addLast(new StringDecoder(CharsetUtil.UTF_8));
        pipeline.addLast(new StringEncoder(CharsetUtil.UTF_8));
//        pipeline.addLast(new IdleStateHandler(40,50,45));
//        //自定义的读写空闲状态检测
//        pipeline.addLast(new HeartBeatHandler());
        pipeline.addLast(new LoggingHandler(LogLevel.INFO));
        //针对客户端，如果1分钟之内没有向服务器发送读写心跳，则主动断开
        pipeline.addLast(new IdleStateHandler(40,50,45));
        EventLoopGroup mutilThread = new NioEventLoopGroup();
        pipeline.addLast(mutilThread,new TestServerHandler());
    }
}
