package com.joe.nio.demo.groupchatdemo;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;

/**
 * @author ckh
 * @create 10/22/2020 8:40 PM
 */
public class GroupChatServer {
    private Selector selector;
    private ServerSocketChannel listenChannel;
    private static final int PORT = 6667;
    private static final int TIMEOUT = 2000;

    public GroupChatServer() {
        init();
    }

    private void init() {
        try {
            selector = Selector.open();
            listenChannel = ServerSocketChannel.open();
            listenChannel.socket().bind(new InetSocketAddress(PORT));
            listenChannel.configureBlocking(false);
            listenChannel.register(selector, SelectionKey.OP_ACCEPT);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void listen() {
        try {
            while (true) {
                if (selector.select() > 0) {
                    Iterator<SelectionKey> keyIterator = selector.selectedKeys().iterator();
                    while (keyIterator.hasNext()) {
                        SelectionKey key = keyIterator.next();

                        if (key.isAcceptable()) {
                            SocketChannel channel = listenChannel.accept();
                            channel.configureBlocking(false);
                            channel.register(selector, SelectionKey.OP_READ);
                            System.out.println(channel.getRemoteAddress() + " on line ");
                            sendInfoToOtherClients(channel.getRemoteAddress() + " on line ...", channel);

                        } else if (key.isReadable()) {
                            // solve read data
                            readData(key);
                        }
                        keyIterator.remove();
                    }

                } else {
                    System.out.println("hold on ...");
                }
            }
        } catch (Exception e) {
            System.out.println("Exception: " + e.getMessage());
        }
    }

    /**
     * 读取客户端消息
     *
     * @param key 触发事件的 key
     */
    private void readData(SelectionKey key) {
        SocketChannel channel = null;

        try {
            channel = (SocketChannel) key.channel();
            ByteBuffer buffer = ByteBuffer.allocate(1024);
            int read = channel.read(buffer);
            if (read > 0) {
                String message = new String(buffer.array());
                System.out.println("from client: " + message);

                // 向其他客户端转发消息
                sendInfoToOtherClients(message, channel);
            }
        } catch (IOException e) {
            try {
                sendInfoToOtherClients(channel.getRemoteAddress() + " off line ...", channel);
                System.out.println(channel.getRemoteAddress() + " off line ...");
                // cancel register
                key.cancel();
                // close channel
                channel.close();

            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        }
    }

    /**
     * 服务器转发消息给其他客户端
     *
     * @param message msg
     * @param self    自己
     */
    private void sendInfoToOtherClients(String message, SocketChannel self) throws IOException {
//        System.out.println("server transfer info...")
        // get all selector selectorChannel and except self
        for (SelectionKey key : selector.keys()) {
            Channel targetChannel = key.channel();
            if (targetChannel instanceof SocketChannel && targetChannel != self) {
                SocketChannel dest = (SocketChannel) targetChannel;
                ByteBuffer buffer = ByteBuffer.wrap(message.getBytes());
                dest.write(buffer);
            }
        }
    }


    public static void main(String[] args) {
        GroupChatServer chatServer = new GroupChatServer();
        chatServer.listen();
    }
}
