package com.luban.netty.udp;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.socket.DatagramPacket;
import io.netty.util.CharsetUtil;

public class ChineseProverbClientHandler extends
        SimpleChannelInboundHandler<DatagramPacket>
{
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, DatagramPacket datagramPacket) throws Exception {
        this.messageReceived(channelHandlerContext,datagramPacket);
    }

    public void messageReceived(ChannelHandlerContext ctx,DatagramPacket
            msg)throws Exception{
        String response=msg.content().toString(CharsetUtil.UTF_8);
//        if(response.startsWith("谚语查询结果：")){
            System.out.println(response);
            ctx.close();
//        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx,Throwable
            cause)throws Exception{
        cause.printStackTrace();
        ctx.close();
    }

}