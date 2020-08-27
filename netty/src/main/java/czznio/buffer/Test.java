package czznio.buffer;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.RandomAccessFile;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.*;
import java.nio.channels.*;

public class Test {
    public static void main(String[] args) throws Exception {


ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
serverSocketChannel.socket().bind(new InetSocketAddress("localhost",3333));
        serverSocketChannel.validOps();

        Selector selector = Selector.open();
        ServerSocketChannel ssc= ServerSocketChannel.open();
        InetSocketAddress addr = new InetSocketAddress(3322);
        ssc.socket().bind(addr); // 在ServerSocketChannel綁定監聽端口
//        ssc.configureBlocking(false); // 設置ServerSocketChannel為非阻塞
        SelectionKey sk = ssc.register(selector, SelectionKey.OP_ACCEPT);

        System.out.println();









//        RandomAccessFile outputStream = new RandomAccessFile("test.txt","rw");
//        FileChannel fileChannel = outputStream.getChannel();
//        MappedByteBuffer mappedByteBuffer= fileChannel.map(FileChannel.MapMode.READ_WRITE,0,fileChannel.size());
//
//        System.out.println(mappedByteBuffer.get());
//        mappedByteBuffer.put(0, (byte) 'c');
//        mappedByteBuffer.flip();
//        mappedByteBuffer.force();
////        fileChannel.write(mappedByteBuffer);
//        System.out.println();
    }
}
