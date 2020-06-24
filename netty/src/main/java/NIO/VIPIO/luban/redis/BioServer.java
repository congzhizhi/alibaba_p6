package NIO.VIPIO.luban.redis;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.SimpleDateFormat;

public class BioServer {
    public static void main(String[] args) {
        try {
            ServerSocket serverSocket=new ServerSocket(6789);
            while (true){
                Socket socket = serverSocket.accept();
                new Thread(){
                    @Override
                    public void run() {
                        while (true){
                            try {
                                InputStream inputStream = socket.getInputStream();
                                byte[] bytes = new byte[inputStream.available()];
                                inputStream.read(bytes);
                                System.out.println(new String(bytes));
                                OutputStream outputStream = socket.getOutputStream();
                                outputStream.write("OK".getBytes("utf-8"));
                                outputStream.flush();
                                socket.close();
                                break;
                            }catch (Exception e){
                                e.printStackTrace();
                            }
                        }
                    }
                }.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
