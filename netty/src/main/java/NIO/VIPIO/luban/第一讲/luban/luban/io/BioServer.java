package NIO.VIPIO.luban.第一讲.luban.luban.io;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class BioServer {
    public static void main(String[] args) throws IOException {
        //端口
        int port=8080;
        ServerSocket serverSocket=null;
        try {
            //绑定端口
            serverSocket=new ServerSocket(port);
            //创建一个线程池，相当于一个固定规模的业务团队
            TimeServerHandlerExecutorPool pool = new TimeServerHandlerExecutorPool(50, 1000);
            while (true){
                //主线程main会阻塞在这里，等待客户端链接
                Socket socket = serverSocket.accept();
                pool.execute(()->{processClient(socket);});
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if(serverSocket!=null){
                serverSocket.close();
            }
        }
    }

    public static  void processClient(Socket socket)  {
        //模拟处理socket
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
