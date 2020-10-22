package com.joe.nio;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * 本地文件写数据
 *
 * @author ckh
 * @create 10/22/20 10:56 AM
 */
public class NIOFileChannel01 {
    public static void main(String[] args) throws IOException {
        String str = "hello world java";
        // create a outputStream -> Channel
        FileOutputStream outputStream = new FileOutputStream("io-module/src/main/resources/file01.txt");
        // use outputStream get FileChannel, actual type is FileChannelImpl
        FileChannel fileChannel = outputStream.getChannel();

        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
        byteBuffer.put(str.getBytes());

        byteBuffer.flip();

        fileChannel.write(byteBuffer);

        outputStream.close();


    }
}
