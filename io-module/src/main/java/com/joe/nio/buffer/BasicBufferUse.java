package com.joe.nio.buffer;

import java.nio.IntBuffer;

/**
 * @author ckh
 * @create 10/22/20 10:27 AM
 */
public class BasicBufferUse {
    public static void main(String[] args) {
        // create a buffer , capacity is 5
        IntBuffer buffer = IntBuffer.allocate(5);

        for (int i = 0; i < buffer.capacity(); i++) {
            buffer.put(i * 2 + 1);
        }

        // 读写切换
        /*
            public final Buffer flip() {
                limit = position;
                position = 0;
                mark = -1;
                return this;
            }
        */
        buffer.flip();

        while (buffer.hasRemaining()) {
            System.out.println(buffer.get());
        }
    }
}
