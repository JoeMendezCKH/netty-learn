package com.joe.netty.bufdemo;


import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.util.CharsetUtil;

import java.nio.charset.StandardCharsets;

/**
 * @author ckh
 * @create 10/23/20 5:44 PM
 */
public class NettyByteBuf02 {
    public static void main(String[] args) {
        ByteBuf buffer = Unpooled.copiedBuffer("hello,world; 嵩哥牛逼", CharsetUtil.UTF_8);

        if (buffer.hasArray()) {
            byte[] content = buffer.array();
            System.out.println(new String(content, StandardCharsets.UTF_8));
            System.out.println("buffer = " + buffer);

            System.out.println("buffer.readByte() = " + buffer.readByte());

            System.out.println("buffer.arrayOffset() = " + buffer.arrayOffset());
            System.out.println("buffer.readerIndex() = " + buffer.readerIndex());
            System.out.println("buffer.writerIndex() = " + buffer.writerIndex());
            System.out.println("buffer.capacity() = " + buffer.capacity());
            int len = buffer.readableBytes();
            System.out.println("buffer.readableBytes() = " + len);
            for (int i = 0; i < len; i++) {
                System.out.println((char)buffer.getByte(i));
            }

            System.out.println(buffer.getCharSequence(2,6, CharsetUtil.UTF_8));
        }

    }
}
