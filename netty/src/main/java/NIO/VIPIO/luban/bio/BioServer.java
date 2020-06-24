package NIO.VIPIO.luban.bio;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;

public class BioServer {

    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(9999);
            while (true) {
                Socket socket = serverSocket.accept();
                System.out.println("客户端" + socket.getRemoteSocketAddress().toString() + "来连接了");
                InputStream inputStream = null;
                OutputStream outputStream = null;
                try {
                    inputStream = socket.getInputStream();
                    outputStream = socket.getOutputStream();
                    int count = 0;
                    byte[] bytes = new byte[1024];
                    while ((count = inputStream.read(bytes)) != -1) {
                        String line = new String(bytes, 0, count, "utf-8");
                        System.out.println(line);
                        outputStream.write("ok".getBytes());
                        outputStream.flush();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    socket.close();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            serverSocket.close();
        }
    }
}
