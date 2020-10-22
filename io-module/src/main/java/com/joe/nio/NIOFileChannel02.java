package com.joe.nio;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * 本地文件读数据
 *
 * @author ckh
 * @create 10/22/20 10:56 AM
 */
public class NIOFileChannel02 {
    public static void main(String[] args) throws IOException {
        File file = new File("io-module/src/main/resources/file01.txt");

        // create a inputStream  -> Channel
        FileInputStream fileInputStream = new FileInputStream(file);
        // use outputStream get FileChannel, actual type is FileChannelImpl
        FileChannel fileChannel = fileInputStream.getChannel();

        ByteBuffer byteBuffer = ByteBuffer.allocate((int) file.length());

        // read data from channel to buffer
        fileChannel.read(byteBuffer);

        System.out.println(new String(byteBuffer.array()));

        fileInputStream.close();


    }
}
