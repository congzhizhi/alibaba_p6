    // Reactor線程 （该类与单线程的处理基本无变动） 
    package NIO.luban.manyReactor;

    import java.io.IOException;
    import java.net.InetSocketAddress;
    import java.nio.channels.SelectionKey;
    import java.nio.channels.Selector;
    import java.nio.channels.ServerSocketChannel;
    import java.util.Iterator;
    import java.util.Set;
    public class    TCPReactor implements Runnable { private final ServerSocketChannel ssc;
    private final Selector selector;

    public TCPReactor(int port) throws IOException {
        //打开一个selector IO多路复用器
        selector = Selector.open();
        //打开服务端通道
        ssc = ServerSocketChannel.open();
        InetSocketAddress addr = new InetSocketAddress(port);
        //绑定端口
        ssc.socket().bind(addr);
        ssc.configureBlocking(false); // 設置ServerSocketChannel為非阻塞
        //注册连接请求事件
        SelectionKey sk = ssc.register(selector, SelectionKey.OP_ACCEPT); // ServerSocketChannel向selector註冊一個OP_ACCEPT事件，然後返回該通道的key
        //绑定连接处理器，连接进来后用Acceptor做后续处理
        sk.attach(new Acceptor(selector, ssc));
    }

    @Override
    public void run() {
        while (!Thread.interrupted()) { // 在線程被中斷前持續運行
            System.out.println("Waiting for new event on port: " + ssc.socket().getLocalPort() + "...");
            try {
                //轮询查看是否有事件就绪， 若沒有事件就緒則不往下執行
                if (selector.select() == 0)
                    continue;
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            //程序执行到这里说明有连接事件发生了，也就是说有客户端请求连接了
            //获取所有的连接事件，遍历处理
            Set<SelectionKey> selectedKeys = selector.selectedKeys();
            Iterator<SelectionKey> it = selectedKeys.iterator();
            while (it.hasNext()) {
                //连接请求转发
                dispatch((SelectionKey) (it.next())); // 根據事件的key進行調度
                //删除事件，表示已经处理完了，下次循环不再处理已经处理过的连接
                it.remove();
            }
        }
    }

    //获取事件的处Acceptor理器，开启一个线程进行处理
    private void dispatch(SelectionKey key) {
        Runnable r = (Runnable) (key.attachment());
        if (r != null)
            r.run();
    }}