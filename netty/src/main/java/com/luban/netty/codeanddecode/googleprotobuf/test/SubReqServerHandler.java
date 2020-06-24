package com.luban.netty.codeanddecode.googleprotobuf.test;

import com.luban.netty.codeanddecode.googleprotobuf.SubscribeReqProto;
import com.luban.netty.codeanddecode.googleprotobuf.SubscribeRespProto;
import io.netty.channel.*;

public class SubReqServerHandler extends SimpleChannelInboundHandler<Object> {
    @Override
    public void channelRead0(ChannelHandlerContext ctx, Object msg){
        System.out.println("接收到客户端其你去");
        SubscribeReqProto.SubscribeReq req = (SubscribeReqProto.SubscribeReq)msg;
        if("wangdecheng".equalsIgnoreCase(req.getUserName())){
            System.out.println("server accept client subscribe req:[" + req.toString() + "]");
            SubscribeRespProto.SubscribeResp subscribeResp = resp(req.getSubReqID());
            ctx.writeAndFlush(subscribeResp);
            System.out.println(" sent to client:"+subscribeResp);
        }
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {

        System.out.println("我读完信息啦！");
    }

    private SubscribeRespProto.SubscribeResp resp(int subReqID) {
        SubscribeRespProto.SubscribeResp.Builder builder = SubscribeRespProto.SubscribeResp.newBuilder();
        builder.setSubReqID(subReqID);
        builder.setRespCode("0");
        builder.setDesc("Netty book order successd!");
        return builder.build();
    }



    //channel注册事件
    @Override
    public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
        super.channelRegistered(ctx);
    }
    //channel活跃 通道准备就绪事件
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("有新的连接");
        Channel channel = ctx.channel();
    }

    //channel取消注册事件
    @Override
    public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {
        super.channelUnregistered(ctx);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
