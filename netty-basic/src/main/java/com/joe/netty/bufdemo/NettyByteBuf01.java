package com.joe.netty.bufdemo;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

/**
 * @author ckh
 * @create 10/23/20 5:44 PM
 */
public class NettyByteBuf01 {
    public static void main(String[] args) {
        // 1. 创建一个对象, 该对象包含一个数组 arr = byte[10]
        // 2. netty buffer 中, 不用 flip 反转, 因为底层维护了 readerIndex 和 writerIndex
        //    0 <= readerIndex <= writerIndex <= capacity
        // 3. 通过 readerIndex 和 writerIndex 和 capacity, 将 buffer 分成三个区域
        //    [0,readerIndex)               已经读取的区域
        //    [readerIndex,writerIndex)     可读的区域
        //    [writerIndex, capacity)       可写的区域
        ByteBuf buffer = Unpooled.buffer(10);
        for (int i = 0; i < 10; i++) {
            buffer.writeByte(i);
        }

        System.out.println("buffer.capacity() = " + buffer.capacity());
        for (int i = 0; i < buffer.capacity(); i++) {
            System.out.print(buffer.readByte() + "\t");
        }
    }
}
