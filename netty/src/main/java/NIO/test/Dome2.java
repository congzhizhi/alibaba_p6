package NIO.test;

import java.io.FileInputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class Dome2 {
    public static void main(String[] args) throws Exception {
        FileInputStream fileInputStream=new FileInputStream("dome2.txt");
        FileChannel channel = fileInputStream.getChannel();
        ByteBuffer byteBuffer=ByteBuffer.allocate(10);
        while (true) {
            int num = channel.read(byteBuffer);
            if (num==-1)
                break;
            byteBuffer.flip();
            System.out.println(new String(byteBuffer.array()));
            byteBuffer.clear();
        }
        fileInputStream.close();
    }
}
