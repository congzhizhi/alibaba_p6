import io.netty.buffer.ByteBuf;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.buffer.Unpooled;
import io.netty.util.ReferenceCountUtil;
import sun.misc.Signal;
import sun.misc.SignalHandler;

public class Test {

    public static void main(String[] args) throws InterruptedException {
        PooledByteBufAllocator allocator = new PooledByteBufAllocator(true);
        //池化堆内
        ByteBuf buf = allocator.heapBuffer(10);
        //池化堆外
        ByteBuf bu2 = allocator.directBuffer(10);

        //非池化堆内
        ByteBuf bu3 = Unpooled.buffer(20);
        //非池化堆外
        ByteBuf bu4 = Unpooled.directBuffer(10);


    }
}
