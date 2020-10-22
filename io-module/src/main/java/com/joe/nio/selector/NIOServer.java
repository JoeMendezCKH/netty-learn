package com.joe.nio.selector;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;
import java.util.Set;

/**
 * @author ckh
 * @create 10/22/20 4:50 PM
 */
public class NIOServer {
    public static void main(String[] args) throws IOException {
        // Linux: EPollSelectorImpl, Win10: WindowsSelectorImpl
        Selector selector = Selector.open();

        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        // bind port
        serverSocketChannel.socket().bind(new InetSocketAddress(6666));
        // config non-blocking
        serverSocketChannel.configureBlocking(false);
        // serverSocketChannel register into selector and setting events
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);

        System.out.println("register selectionKey num="+selector.keys().size());

        // hold on client
        while (true) {
            // no event happened
            if (selector.select(5000) == 0) {
                System.out.println("hold on 1 second, non connection");
                continue;
            }
            // get the related SelectionKey collection
            Set<SelectionKey> selectionKeys = selector.selectedKeys();

            // 根据不同事件做不同处理
            Iterator<SelectionKey> keyIterator = selectionKeys.iterator();
            while (keyIterator.hasNext()) {
                SelectionKey selectionKey = keyIterator.next();
                if (selectionKey.isAcceptable()) {
                    // generator a client channel
                    SocketChannel socketChannel = serverSocketChannel.accept();
                    socketChannel.configureBlocking(false);
                    System.out.println("client connect success ...  " + socketChannel.hashCode());
                    socketChannel.register(selector, SelectionKey.OP_READ, ByteBuffer.allocate(1024));

                    System.out.println("register selectionKey num="+selector.keys().size());


                } else if (selectionKey.isReadable()) {
                    SocketChannel channel = (SocketChannel) selectionKey.channel();
                    // 获取该 channel 关联的 buffer(在连接时设置的)
                    ByteBuffer buffer = (ByteBuffer) selectionKey.attachment();
                    channel.read(buffer);
                    System.out.println("from client: " + channel.hashCode() + " :" + new String(buffer.array()));
                }

                // 从集合中移除当前的 selectionKey 防止重复操作
                keyIterator.remove();
            }

        }

    }
}
