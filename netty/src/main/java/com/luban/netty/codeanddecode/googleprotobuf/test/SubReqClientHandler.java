package com.luban.netty.codeanddecode.googleprotobuf.test;

import com.luban.netty.codeanddecode.googleprotobuf.SubscribeReqProto;
import com.luban.netty.codeanddecode.messagepack.test.User;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.ArrayList;
import java.util.List;

public class SubReqClientHandler extends SimpleChannelInboundHandler<Object> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Object object) throws Exception {

        System.out.println("i have received message : "+object);
//        ctx.writeAndFlush(ctx);
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("客户端开始发送");
        for(int i = 0;i <10;i++){
            ctx.write(subReq(i));
        }
        ctx.flush();

    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }


    private Object subReq(int i) {
        SubscribeReqProto.SubscribeReq.Builder builder = SubscribeReqProto.SubscribeReq.newBuilder();
        builder.setSubReqID(i);
        builder.setUserName("wangdecheng");
        builder.setProductName("netty book");
        List<String> address = new ArrayList<>();
        address.add("Nanjing yuhuatai");
        address.add("BeiJing LiuLiChang");
        address.add("Shenzhen HongShuLin");
        builder.setAddress("Zhongguo Beijing");
        return builder.build();
    }
}
