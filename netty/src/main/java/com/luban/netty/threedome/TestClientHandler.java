package com.luban.netty.threedome;

import io.netty.channel.*;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class TestClientHandler extends SimpleChannelInboundHandler<String> {
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, String s) throws Exception {
        System.out.println(s.trim()+"\n");
    }


    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        BufferedReader bufferedReader=new BufferedReader(new InputStreamReader(System.in));
        try {
            for(; ;){
                String s = bufferedReader.readLine()+"\r\n";
                byte[] co = new byte[]{97,98,97,97,99,100,97,97};
                if (channel.isOpen()){

                    channel.writeAndFlush(s);
                }else{
                    System.out.println("链路失败啦！");
                    break;
                }
            }
        } finally {
            System.out.println("关闭资源");
            bufferedReader.close();
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
//        ctx.fireExceptionCaught(cause);
        throw new Exception("链路关闭啦");
    }
}
