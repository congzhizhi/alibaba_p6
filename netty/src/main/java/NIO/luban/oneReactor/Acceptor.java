    // 接受連線請求線程  
    package NIO.luban.oneReactor;

    import java.io.IOException;
    import java.nio.channels.SelectionKey;
    import java.nio.channels.Selector;
    import java.nio.channels.ServerSocketChannel;
    import java.nio.channels.SocketChannel;

    public class Acceptor implements Runnable {  
      
        private final ServerSocketChannel ssc;  
        private final Selector selector;  
          
        public Acceptor(Selector selector, ServerSocketChannel ssc) {  
            this.ssc=ssc;  
            this.selector=selector;  
        }  
          
        @Override  
        public void run() {  
            try {
                // 接受client链接请求
                SocketChannel sc= ssc.accept();
                System.out.println(sc.socket().getRemoteSocketAddress().toString() + " is connected.");  
                if(sc!=null) {
                    // 設置為非阻塞
                    sc.configureBlocking(false);
                    // SocketChannel向selector註冊一個读事件，然後返回該通道的key
                    SelectionKey sk = sc.register(selector, SelectionKey.OP_READ);
                    // 使一個阻塞住的selector操作立即返回
                    selector.wakeup();
                    // 給定key一個附加的TCPHandler對象，用来处理写操作
                    sk.attach(new TCPHandler(sk, sc));
                }
            } catch (IOException e) {  
                // TODO Auto-generated catch block  
                e.printStackTrace();  
            }  
        }  

    }  