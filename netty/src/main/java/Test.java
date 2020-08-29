import io.netty.buffer.ByteBuf;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.buffer.Unpooled;
import io.netty.util.ReferenceCountUtil;
import javafx.concurrent.Worker;
import org.junit.Assert;
import sun.misc.Signal;
import sun.misc.SignalHandler;

import java.lang.ref.SoftReference;
import java.lang.ref.WeakReference;
import java.util.Date;
import java.util.HashMap;
import java.util.concurrent.*;
import java.util.concurrent.locks.ReentrantLock;

public class Test  {
    public static void main(String[] args) {
        //268M的缓冲池
        PooledByteBufAllocator pooledByteBufAllocator = new PooledByteBufAllocator(true,PooledByteBufAllocator.defaultNumHeapArena(),PooledByteBufAllocator.defaultNumDirectArena()
                ,65536,12);
//        PooledByteBufAllocator pooledByteBufAllocator =  PooledByteBufAllocator.DEFAULT;
        ByteBuf byteBuf = pooledByteBufAllocator.buffer();
        ByteBuf byteBuf2 = pooledByteBufAllocator.buffer(26444216);
        ByteBuf byteBuf3 = pooledByteBufAllocator.buffer(26444216);
        System.out.println();
    }

}




















