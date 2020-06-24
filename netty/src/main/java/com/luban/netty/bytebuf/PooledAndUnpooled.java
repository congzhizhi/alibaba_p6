package com.luban.netty.bytebuf;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.buffer.Unpooled;

public class PooledAndUnpooled {

    //非池化堆内存
    public static  void unpooled(){
        long beginTime = System.currentTimeMillis();
        ByteBuf buf = null;
        int maxTime = 100000000;
        for (int i = 0; i < maxTime; i++) {
            buf = Unpooled.buffer(10*1024);
            buf.release();
        }
        System.out.println((System.currentTimeMillis() - beginTime)/1000);
    }

    //池化堆内存
    public static  void pooled(){
        long beginTime = System.currentTimeMillis();
        PooledByteBufAllocator allocator = new PooledByteBufAllocator(false);
        ByteBuf buf = null;
        int maxTime = 100000000;
        for (int i = 0; i < maxTime; i++) {
            buf = allocator.heapBuffer(10*1024);
            buf.release();
        }
        System.out.println((System.currentTimeMillis() - beginTime)/1000);
    }

    public static void main(String[] args) {
        pooled();
    }
}
