    // Handler線程  
    package NIO.luban.manyReactor;

    import java.io.IOException;
    import java.nio.channels.SelectionKey;
    import java.nio.channels.SocketChannel;
    import java.util.concurrent.LinkedBlockingQueue;
    import java.util.concurrent.ThreadPoolExecutor;
    import java.util.concurrent.TimeUnit;

    public class TCPHandler implements Runnable {  
      
        private final SelectionKey sk;  
        private final SocketChannel sc;  
        private static final int THREAD_COUNTING = 10;
        //读写事件交给线程池处理
        private static ThreadPoolExecutor pool = new ThreadPoolExecutor(  
                THREAD_COUNTING, THREAD_COUNTING, 10, TimeUnit.SECONDS,  
                new LinkedBlockingQueue<Runnable>()); // 線程池  

        //读写状态处理器
        HandlerState state;
      
        public TCPHandler(SelectionKey sk, SocketChannel sc) {  
            this.sk = sk;  
            this.sc = sc;
            // 初始状态设置为读状态
            state = new ReadState();
            pool.setMaximumPoolSize(32); // 設置線程池最大線程數  
        }  
      
        @Override  
        public void run() {  
            try {
                //利用线程池处理读写
                state.handle(this, sk, sc, pool);  
            } catch (IOException e) {
                System.out.println("[Warning!] A client has been closed.");  
                closeChannel();  
            }  
        }  
          
        public void closeChannel() {  
            try {  
                sk.cancel();  
                sc.close();  
            } catch (IOException e1) {  
                e1.printStackTrace();  
            }  
        }  

        //读写状态的更改，读事件处理完改为写状态，写状态处理完改为读状态
        public void setState(HandlerState state) {  
            this.state = state;  
        }  
    }  