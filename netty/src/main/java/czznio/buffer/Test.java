package czznio.buffer;

import java.nio.*;

public class Test {
    public static void main(String[] args) {
        ByteBuffer byteBuffer = ByteBuffer.allocate(100);
        IntBuffer intBufferBuffer = null;
        byteBuffer = byteBuffer.order(ByteOrder.BIG_ENDIAN);
        byteBuffer.putInt(2569);

        System.out.println();
    }
}
