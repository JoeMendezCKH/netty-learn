package com.joe.nio.zerocopy;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.FileChannel;
import java.nio.channels.SocketChannel;

/**
 * @author ckh
 * @create 10/22/2020 10:30 PM
 */
public class NewIOClient {
    public static void main(String[] args) throws IOException {
        SocketChannel socketChannel = SocketChannel.open();
        socketChannel.connect(new InetSocketAddress("localhost", 7001));

        String fileName = "io-module/src/main/resources/protoc-3.6.1-win32.zip";
        FileChannel fileChannel = new FileInputStream(fileName).getChannel();

        long start = System.currentTimeMillis();
        /*
            在 Linux 下, 一个 transferTo 方法就可以完成传输
            在 Windows 下, 一次调用 transferTo 方法只能发送 8M, 需要分段传输
            要处理传输时的位置
         */
        // transferTo 底层使用 零拷贝
        long count = fileChannel.transferTo(0, fileChannel.size(), socketChannel);
        System.out.println("send total byte number is " + count + " using time: "+(System.currentTimeMillis() - start));

        fileChannel.close();
    }
}
