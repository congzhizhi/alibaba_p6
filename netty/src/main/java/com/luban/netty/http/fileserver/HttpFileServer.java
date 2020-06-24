package com.luban.netty.http.fileserver;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpRequestDecoder;
import io.netty.handler.codec.http.HttpResponseEncoder;
import io.netty.handler.stream.ChunkedWriteHandler;

public class HttpFileServer {
//    private static final String DEFAULT_URL = "/netty/src/main/java/com/luban/netty";
    private static final String DEFAULT_URL = "/";
    private static final String LOCALADDRESS = "D:\\";
    private static final int PORT = 80 ;

    public void run(final int port,final String url,final String localaddress) throws Exception{
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();

        try{
            ServerBootstrap bootstrap = new ServerBootstrap();
            bootstrap.group(bossGroup,workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer<SocketChannel>() {

                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ch.pipeline().addLast("http-decoder",new HttpRequestDecoder());
                            ch.pipeline().addLast("http-aggregator",new HttpObjectAggregator(65535));//将多个消息转换成单一的FullHttpRequest或者FullHttpResponse,原因是HTTP解码器在每个HTTP消息中会生成多个消息对象
                            ch.pipeline().addLast("http-encoder",new HttpResponseEncoder());
                            ch.pipeline().addLast("http-chunked",new ChunkedWriteHandler());//支持异步发送大的码流，例如大文件传输，但不占用过多的内存，防止发生JAVA内存溢出
                            ch.pipeline().addLast("fileServerHandler",new HttpFileServerHandler(url,localaddress));
                        }
                    });
            ChannelFuture future = bootstrap.bind("192.168.0.114",port).sync();
            System.out.println("HTTP 文件目录服务器启动，网址：" + "http://localhost:" + port + url);
            future.channel().closeFuture().sync();
        }finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }

    public static void main(String[] args){
        int port = PORT;
        String url = DEFAULT_URL;
        String localaddress = LOCALADDRESS;
        try {
            new HttpFileServer().run(port,url,localaddress);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
