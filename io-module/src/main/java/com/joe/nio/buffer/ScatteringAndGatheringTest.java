package com.joe.nio.buffer;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Arrays;

/**
 * Scattering: 将数据写入到 buffer 时,可以采用 buffer 数组,依次写入 [分散]
 * Gathering: 从 buffer 读取数据时,可以采用 buffer 数组,依次读
 *
 * @author ckh
 * @create 10/22/20 3:37 PM
 */
public class ScatteringAndGatheringTest {
    public static void main(String[] args) throws IOException {

        // using serverSocketChannel and socketChannel network
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        InetSocketAddress inetSocketAddress = new InetSocketAddress(7000);

        serverSocketChannel.socket().bind(inetSocketAddress);
        System.out.println("server start ...");

        int messageMaxLength = 8;
        ByteBuffer[] byteBuffers = new ByteBuffer[2];
        byteBuffers[0] = ByteBuffer.allocate(5);
        byteBuffers[1] = ByteBuffer.allocate(3);

        // client
        SocketChannel socketChannel = serverSocketChannel.accept();
        System.out.println("client start...");
        while (true) {
            int byteRead = 0;
            while (byteRead < messageMaxLength) {
                long read = socketChannel.read(byteBuffers);
                byteRead += read;
                System.out.println("byteRead = " + byteRead);
                // using stream print
                Arrays.stream(byteBuffers).map(
                        byteBuffer -> "position=" + byteBuffer.position() + ", limit=" + byteBuffer.limit()
                ).forEach(System.out::println);
            }
            Arrays.stream(byteBuffers).forEach(Buffer::flip);

            int byteWrite = 0;
            while (byteWrite < messageMaxLength) {
                long write = socketChannel.write(byteBuffers);
                byteWrite += write;
            }
            Arrays.stream(byteBuffers).forEach(Buffer::clear);

            System.out.println("byteRead:=" + byteRead + " byteWrite=" + byteWrite + ", messageMaxLength" + messageMaxLength);
        }
    }
}
