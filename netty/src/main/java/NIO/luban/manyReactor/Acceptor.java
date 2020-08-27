    // 接受連線請求線程  
    package NIO.luban.manyReactor;

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
                // 接受client连接请求
                SocketChannel sc= ssc.accept();
                System.out.println(sc.socket().getRemoteSocketAddress().toString() + " is connected.");  
                  
                if(sc!=null) {  
                    sc.configureBlocking(false); // 設置為非阻塞
                    //注册读事件
                    SelectionKey sk = sc.register(selector, SelectionKey.OP_READ); // SocketChannel向selector註冊一個OP_READ事件，然後返回該通道的key  
//                    System.out.println(sk.selector()==selector);
                    selector.wakeup(); // 使一個阻塞住的selector操作立即返回
                    // 将读事件交给TCPHandler进行处理
                    sk.attach(new TCPHandler(sk, sc));
                }  
                  
            } catch (IOException e) {  
                // TODO Auto-generated catch block  
                e.printStackTrace();  
            }  
        }  
      
          
    }  