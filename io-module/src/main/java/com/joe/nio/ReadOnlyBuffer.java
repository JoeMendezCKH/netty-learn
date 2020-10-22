package com.joe.nio;

import java.nio.ByteBuffer;

/**
 * @author ckh
 * @create 10/22/20 2:58 PM
 */
public class ReadOnlyBuffer {

    public static void main(String[] args) {
        ByteBuffer byteBuffer = ByteBuffer.allocate(64);
        for (int i = 0; i < 64; i++) {
            byteBuffer.put((byte) (i * 2));
        }
        byteBuffer.flip();

        ByteBuffer readOnlyBuffer = byteBuffer.asReadOnlyBuffer();
        System.out.println(readOnlyBuffer.getClass());

        while (readOnlyBuffer.hasRemaining()) {
            System.out.print(readOnlyBuffer.get() + "\t");
        }
        System.out.println();

        readOnlyBuffer.flip();
        // ReadOnlyBufferException
//        readOnlyBuffer.put((byte) 100);
    }
}
