    // Handler線程  
    package NIO.luban.oneReactor;

    import java.io.IOException;
    import java.nio.ByteBuffer;
    import java.nio.channels.SelectionKey;
    import java.nio.channels.SocketChannel;

    public class TCPHandler implements Runnable {  
      
        private final SelectionKey sk;  
        private final SocketChannel sc;  
      
        int state;   
      
        public TCPHandler(SelectionKey sk, SocketChannel sc) {  
            this.sk = sk;  
            this.sc = sc;  
            state = 0; // 初始狀態設定為READING ，第一次肯定是先读客户端数据
        }  
      
        @Override  
        public void run() {  
            try {  
                if (state == 0)  
                    read(); // 读取数据
                else  
                    send(); // 发送
      
            } catch (IOException e) {  
                System.out.println("[Warning!] A client has been closed.");  
                closeChannel();  
            }  
        }  
          

      
        private synchronized void read() throws IOException {  
            byte[] arr = new byte[1024];
            ByteBuffer buf = ByteBuffer.wrap(arr);  
              
            int numBytes = sc.read(buf); // 讀取字符串  
            if(numBytes == -1)  
            {  
                System.out.println("[Warning!] A client has been closed.");  
                closeChannel();  
                return;  
            }  
            String str = new String(arr); // 將读取到的byte內容转换字符串
            if ((str != null) && !str.equals(" ")) {
                //处理数据
                process(str); //
                System.out.println(sc.socket().getRemoteSocketAddress().toString()  
                        + " > " + str);
                //在这个通道读完了后，下一步往这个通道写数据
                //改成写状态
                state = 1;
                sk.interestOps(SelectionKey.OP_WRITE); // 通過key改變通道註冊的事件  
                sk.selector().wakeup(); // 使一個阻塞住的selector操作立即返回  
            }  
        }  
      
        private void send() throws IOException  {  
            // get message from message queue  
              
            String str = "Your message has sent to "  
                    + sc.socket().getLocalSocketAddress().toString() + "\r\n";  
            ByteBuffer buf = ByteBuffer.wrap(str.getBytes()); // wrap自動把buf的position設為0，所以不需要再flip()  
      
            while (buf.hasRemaining()) {  
                sc.write(buf); // 回傳給client回應字符串，發送buf的position位置 到limit位置為止之間的內容  
            }  
              
            state = 0; // 改變狀態  
            sk.interestOps(SelectionKey.OP_READ); // 通過key改變通道註冊的事件  
            sk.selector().wakeup(); // 使一個阻塞住的selector操作立即返回  
        }  
          
        void process(String str) {  
            // do process(decode, logically process, encode)..
            // ..
            try {
                //等待6秒，模拟数据处理
                Thread.sleep(60000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        //关闭通道
        private void closeChannel() {
            try {
                sk.cancel();
                sc.close();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
    }  