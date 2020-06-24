package com.luban.netty.粘包拆包.delimiterbasedframedecoder;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class TestClientHandler extends SimpleChannelInboundHandler<String> {

    private int counter;
    private byte[] req;

    public TestClientHandler() {
        req = ("QUERY TIME ORDER"+"\r\n").getBytes();
    }
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, String object) throws Exception {
//        ByteBuf buf = (ByteBuf)object;
//        byte[] req = new byte[buf.readableBytes()];
//        buf.readBytes(req);
//        String body = new String(req,"UTF-8");
        System.out.println("时间："+object+" thie counter is "+ ++counter);

    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        ByteBuf message = null;
        for (int i=0;i<100;i++){
            message = Unpooled.buffer(req.length);
            message.writeBytes(req);
            ctx.writeAndFlush(message);
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
