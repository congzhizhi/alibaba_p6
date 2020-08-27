package NIO.luban.chat;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.TimeUnit;

//聊天室服务端
public class ChatServer {

    private Selector selector;
    private ServerSocketChannel serverSocketChannel;
    private long timeout=2000;

    public ChatServer(){
        try {
            //服务端channel
            serverSocketChannel=ServerSocketChannel.open();
            //选择器对象,底层就是IO多路复用
            selector=Selector.open();
            //绑定端口
            serverSocketChannel.bind(new InetSocketAddress(9090));
            //设置非阻塞式
            serverSocketChannel.configureBlocking(false);
            //注册"监听连接"给Selector
            SelectionKey selectionKey = serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
            System.out.println("服务端准备就绪");
            start();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void start() throws Exception{
        int count=0;
        long start=System.nanoTime();

        while (true){
            //等待感兴趣的事件，没有事件就会阻塞2秒钟，2秒钟没有感兴趣事件发生，程序继续往下执行
            selector.select(timeout);
//            System.out.println("2秒了");
            long end=System.nanoTime();
            if(end-start>= TimeUnit.MILLISECONDS.toNanos(timeout)){
                count=1;
            }else{
                count++;//记录空轮询的次数
            }
            //空轮询次数太多的话，重新建立连接
            if(count>=10){
                System.out.println("有可能发生空轮询"+count+"次");
                rebuildSelector();
                count=0;
                selector.selectNow();
                continue;
            }
            //得到所有就绪的SelectionKey对象
            Set<SelectionKey> selectionKeys = selector.selectedKeys();
            Iterator<SelectionKey> iterator = selectionKeys.iterator();
            //遍历就绪事件，并判断就绪的事件类型
            while (iterator.hasNext()){
                SelectionKey selectionKey=iterator.next();
                //连接事件
                if(selectionKey.isAcceptable()){
                    //获取网络通道，有客户端来链接啦
                    SocketChannel accept = serverSocketChannel.accept();
                    //设置非阻塞式
                    accept.configureBlocking(false);
                    //连接上了  注册读取事件
                    accept.register(selector,SelectionKey.OP_READ);
                    System.out.println(accept.getRemoteAddress().toString()+"上线了");
                }
                //读事件
                if(selectionKey.isReadable()){     //读取客户端数据事件
                    //读取客户端发来的数据
                    readClientData(selectionKey);
                }
                //手动从当前集合将本次运行完的对象删除，事件处理完了就要删除
                iterator.remove();
            }
        }
    }

    //重新建立链接
    private void rebuildSelector() throws IOException {
        Selector newSelector=Selector.open();
        Selector oldSelect=selector;
        for (SelectionKey selectionKey : oldSelect.keys()) {
            //感兴趣事件对应的数值
            int i = selectionKey.interestOps();
            //取消旧的键
            selectionKey.cancel();
            //将channel注册到新的选择器上
            selectionKey.channel().register(newSelector,i);
        }
        selector=newSelector;
        oldSelect.close();//关闭旧的
    }

    //读取客户端发来的数据
    private void readClientData(SelectionKey selectionKey) throws IOException {
        //获取跟客户端连接的通道
        SocketChannel socketChannel = (SocketChannel) selectionKey.channel();
        //生成缓冲区，用于接收客户端传输进来的数据
        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
        //读取数据到缓冲区，返回实际读取到的字节数，没有数据返回-1
        int read = socketChannel.read(byteBuffer);
        //读之前，将缓冲区设置为读状态
        byteBuffer.flip();
        if(read>0){//判断确实读到数据了
            //创建临时发送字节数组
            byte[] bytes=new byte[read];
            //将缓冲区数据写到临时数组
            byteBuffer.get(bytes,0,read);
            //读取了数据  广播
            String s = new String(bytes,"utf-8");
            //将此数据发送到其他客户端
            writeClientData(socketChannel,s);
        }
    }

    //广播  将读取的数据群发
    private void writeClientData(SocketChannel socketChannel,String s) throws IOException {
        //获取到所有的注册事件，不管有没有就绪
        Set<SelectionKey> keys = selector.keys();
        //遍历事件
        for (SelectionKey key : keys) {
            //判断事件是否还有效
            if(key.isValid()){
                //获取事件对应的channel
                SelectableChannel channel = key.channel();
                //注意，我们只需要将信息发送给客户端
                if(channel instanceof  SocketChannel){
                    SocketChannel socketChannel1= (SocketChannel) channel;
                    //不需要发送给自己了
                    if(channel!=socketChannel){
                        ByteBuffer wrap = ByteBuffer.wrap(s.getBytes());
                        socketChannel1.write(wrap);
                    }
                }
            }
        }
    }


    public static void main(String[] args) throws Exception {
        new ChatServer().start();
    }


}
