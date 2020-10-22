package com.joe.nio;

import java.nio.ByteBuffer;

/**
 * ByteBuffer 支持类型化的 put 和 get
 * put 放入的是什么数据类型,get 就应该使用相应的数据类型来取出,否则可能有 BufferUnderflowException 异常
 *
 * @author ckh
 * @create 10/22/20 2:53 PM
 */
public class NIOByteBufferPutGet {
    public static void main(String[] args) {
        ByteBuffer byteBuffer = ByteBuffer.allocate(64);

        byteBuffer.putInt(100);
        byteBuffer.putLong(200);
        byteBuffer.putChar('嵩');
        byteBuffer.putShort((short) 4);

        byteBuffer.flip();

        System.out.println(byteBuffer.getInt());
        System.out.println(byteBuffer.getLong());
        System.out.println(byteBuffer.getChar());
        System.out.println(byteBuffer.getShort());
//        System.out.println(byteBuffer.getLong());

        // 如果没有按照put 的类型获取时, 可能抛出 BufferUnderflowException
    }
}
