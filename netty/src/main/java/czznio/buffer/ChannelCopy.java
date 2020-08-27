package czznio.buffer;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.nio.channels.WritableByteChannel;

public class ChannelCopy {
    /**
     * 此代码将数据从stdin复制到stdout
     */
    public static void main(String[] argv)
            throws IOException {

        ReadableByteChannel source = Channels.newChannel(System.in);
        WritableByteChannel dest = Channels.newChannel(System.out);
        channelCopy1(source, dest);
        source.close();
        dest.close();
    }

    /**
     *通道复制方法1.
     * 此方法从src *通道复制数据并将其写入dest通道，直到src上的EOF。
     * 该实现利用临时缓冲区上的compact（）
     * 如果缓冲区没有完全耗尽，则压缩数据。
     * 可能会导致数据复制，但会最大程度地减少系统调用。
     * 它还需要清理循环以确保所有数据都已发送。
     */
    private static void channelCopy1(ReadableByteChannel src,
                                     WritableByteChannel dest)
            throws IOException {
        ByteBuffer buffer = ByteBuffer.allocateDirect(16 * 1024);
        while (src.read(buffer) != -1) {
            // 准备释放缓冲区
            buffer.flip();
            // 写到目的通道
            dest.write(buffer);
            //如果是部分转移，则将余数向下移位
            buffer.compact();
        }
        // EOF将使缓冲区处于填充状态
        buffer.flip();
        // 确保缓冲区已完全读完
        while (buffer.hasRemaining()) {
            dest.write(buffer);
        }
    }

}