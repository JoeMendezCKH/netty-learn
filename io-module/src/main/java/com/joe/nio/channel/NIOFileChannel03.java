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
public class NIOFileChannel03 {
    public static void main(String[] args) throws IOException {
        FileInputStream fileInputStream = new FileInputStream("io-module/src/main/resources/01.txt");
        FileChannel inputStreamChannel = fileInputStream.getChannel();

        FileOutputStream fileOutputStream = new FileOutputStream("io-module/src/main/resources/02.txt");
        FileChannel outputStreamChannel = fileOutputStream.getChannel();

        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);

        while (inputStreamChannel.read(byteBuffer) != -1) {
            // Read and write mode switch
            byteBuffer.flip();
            outputStreamChannel.write(byteBuffer);

            // If there is no clear method, when the reading is completed, position == limit,
            // the result of read is always 0, and will not break the loop
            byteBuffer.clear();
        }

        fileInputStream.close();
        fileOutputStream.close();


    }
}
