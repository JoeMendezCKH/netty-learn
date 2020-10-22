package com.joe.nio.channel;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * 使用一个 Buffer 完成文件读取、写入
 *
 * @author ckh
 * @create 10/22/20 10:56 AM
 */
public class NIOFileChannel04 {
    public static void main(String[] args) throws IOException {
        FileInputStream fileInputStream = new FileInputStream("/home/joe/Pictures/Wallpapers/a.jpg");
        FileChannel sourceChannel = fileInputStream.getChannel();

        FileOutputStream fileOutputStream = new FileOutputStream("io-module/src/main/resources/copy.jpg");
        FileChannel destChannel = fileOutputStream.getChannel();

        // using transferFrom to copy
        destChannel.transferFrom(sourceChannel,0,sourceChannel.size());

        fileInputStream.close();
        fileOutputStream.close();


    }
}
