package com.luban.netty.codeanddecode.messagepack;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import org.msgpack.MessagePack;

import java.io.IOException;

public class MessagePackEncoder extends MessageToByteEncoder<Object> {
    public static void main(String[] args) {
    }

    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, Object obj, ByteBuf byteBuf) throws Exception {
        try {
            MessagePack msgpack = new MessagePack();
            byte[] raw = msgpack.write(obj);
            byteBuf.writeBytes(raw);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
