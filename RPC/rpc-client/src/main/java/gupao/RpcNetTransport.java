package gupao;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * 腾讯课堂搜索 咕泡学院
 * 加群获取视频：608583947
 * 风骚的Michael 老师
 */
public class RpcNetTransport {

    private String host;
    private int port;

    public RpcNetTransport(String host, int port) {
        this.host = host;
        this.port = port;
    }



    public Object send(RpcRequest request){
        Socket socket=null;
        Object result=null;
        ObjectOutputStream outputStream=null;
        ObjectInputStream inputStream=null;

        try {
            socket=new Socket(host,port); //建立连接

            outputStream =new ObjectOutputStream(socket.getOutputStream());//网络socket
            outputStream.writeObject(request); //序列化()
            outputStream.flush();

            inputStream=new ObjectInputStream(socket.getInputStream());
            result=inputStream.readObject();


        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }finally {
            if(inputStream!=null){
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if(outputStream!=null){
                try {
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return result;

    }
}
