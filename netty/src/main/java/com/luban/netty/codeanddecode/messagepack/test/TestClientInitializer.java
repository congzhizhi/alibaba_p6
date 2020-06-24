package com.luban.netty.codeanddecode.messagepack.test;

import com.luban.netty.codeanddecode.messagepack.MessagePackDecoder;
import com.luban.netty.codeanddecode.messagepack.MessagePackEncoder;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;

public class TestClientInitializer extends ChannelInitializer<SocketChannel>{

    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ch.pipeline().addLast("frameDecoder",new LengthFieldBasedFrameDecoder(1024, 0, 2,0,2));
        ch.pipeline().addLast("msgpack decoder",new MessagePackDecoder());
        ch.pipeline().addLast("frameEncoder",new LengthFieldPrepender(2));
        ch.pipeline().addLast("msgpack encoder",new MessagePackEncoder());
        ch.pipeline().addLast(new TestClientHandler());
    }
}
