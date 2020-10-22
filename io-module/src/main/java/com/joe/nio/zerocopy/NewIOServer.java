package com.joe.nio.zerocopy;

import javafx.beans.binding.When;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

/**
 * @author ckh
 * @create 10/22/2020 10:22 PM
 */
public class NewIOServer {
    public static void main(String[] args) throws IOException {
        InetSocketAddress socketAddress = new InetSocketAddress(7001);
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        ServerSocket serverSocket = serverSocketChannel.socket();

        serverSocket.bind(socketAddress);
        ByteBuffer buffer = ByteBuffer.allocate(4096);

        while (true) {
            SocketChannel channel = serverSocketChannel.accept();
            int read = 0;
            while (read != -1) {
                try {
                    read = channel.read(buffer);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                // position = 0, mark = -1;
                buffer.rewind();
            }
        }
    }
}
