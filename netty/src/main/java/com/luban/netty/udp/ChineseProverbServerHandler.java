package com.luban.netty.udp;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.socket.DatagramPacket;
import io.netty.util.CharsetUtil;
import io.netty.util.internal.ThreadLocalRandom;

public class ChineseProverbServerHandler extends
        SimpleChannelInboundHandler<DatagramPacket>
{
    private static final String [] DICTIONARY={"小葱拌豆腐，一穷二白","只要功夫深，铁棒磨成针","山中无老虎，猴子称霸王"};

    private String nextQuote(){
        //线程安全岁基类，避免多线程环境发生错误
        int quote=ThreadLocalRandom.current().nextInt(DICTIONARY.length);
        return DICTIONARY[quote];
    }
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, DatagramPacket packet) throws Exception {
        ByteBuf buf = packet.copy().content();
        byte[] req = new byte[buf.readableBytes()];
        buf.readBytes(req);
        String body = new String(req, "UTF-8");
        System.out.println(body);//打印收到的信息
        //向客户端发送消息
        String json = "来自服务端: 南无阿弥陀佛";
        // 由于数据报的数据是以字符数组传的形式存储的，所以传转数据
        byte[] bytes = json.getBytes("UTF-8");
        DatagramPacket data = new DatagramPacket(Unpooled.copiedBuffer(bytes), packet.sender());
        ctx.writeAndFlush(data);//向客户端发送消息
//        this.messageReceived(ctx,packet);
    }
    //接收Netty封装的DatagramPacket对象，然后构造响应消息
    public void messageReceived(ChannelHandlerContext ctx,DatagramPacket packet)
            throws Exception{
        //利用ByteBuf的toString()方法获取请求消息
        String req=packet.content().toString(CharsetUtil.UTF_8);
        System.out.println(req);
        if("谚语字典查询?".equals(req)){
            //创建新的DatagramPacket对象，传入返回消息和目的地址（IP和端口）
            ctx.writeAndFlush(new DatagramPacket(Unpooled.copiedBuffer(
                    "谚语查询结果："+nextQuote(),CharsetUtil.UTF_8), packet.sender()));
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx,Throwable cause)
            throws Exception{
        ctx.close();
        cause.printStackTrace();
    }
}