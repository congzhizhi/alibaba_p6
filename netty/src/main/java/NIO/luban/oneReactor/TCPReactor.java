    // Reactor線程  
    package NIO.luban.oneReactor;

    import java.io.IOException;
    import java.net.InetSocketAddress;
    import java.nio.channels.SelectionKey;
    import java.nio.channels.Selector;
    import java.nio.channels.ServerSocketChannel;
    import java.util.Iterator;
    import java.util.Set;

    public class TCPReactor implements Runnable {  
      
        private final ServerSocketChannel ssc;  
        private final Selector selector;  
      
        public TCPReactor(int port) throws IOException {
            //打开选择器进行IO多路复用
            selector = Selector.open();
            //打开服务器通道
            ssc = ServerSocketChannel.open();  
            InetSocketAddress addr = new InetSocketAddress(port);
            //绑定端口
            ssc.socket().bind(addr); // 在ServerSocketChannel綁定監聽端口
            //設置ServerSocketChannel為非阻塞
            ssc.configureBlocking(false);
            //注册链接事件
            SelectionKey sk = ssc.register(selector, SelectionKey.OP_ACCEPT);
            //将时间绑定一个处理器，事件发生后由这个处理器完成后续操作
            sk.attach(new Acceptor(selector, ssc));
        }  
      
        @Override  
        public void run() {  
            while (!Thread.interrupted()) { // 在線程被中斷前持續運行  
                System.out.println("Waiting for new event on port: " + ssc.socket().getLocalPort() + "...");  
                try {
                    // 若沒有事件就緒則不往下執行
                    if (selector.select() == 0)
                        continue;  
                } catch (IOException e) {  
                    e.printStackTrace();
                }
                // 取得所有已就緒事件的key集合
                Set<SelectionKey> selectedKeys = selector.selectedKeys();
                //遍历事件
                Iterator<SelectionKey> it = selectedKeys.iterator();  
                while (it.hasNext()) {
                    //调度事件，在这里我们开启另一个线程进行读写操作
                    dispatch((it.next()));
                    it.remove();  
                }  
            }  
        }  
      
        /* 
         * name: dispatch(SelectionKey key) 
         * description: 調度方法，根據事件綁定的對象開新線程 
         */  
        private void dispatch(SelectionKey key) {  
            Runnable r = (Runnable) (key.attachment()); // 根據事件之key綁定的對象開新線程  
            if (r != null)  {
                r.run();
            }
        }
      
    }  