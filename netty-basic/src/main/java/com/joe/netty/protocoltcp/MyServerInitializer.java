package com.joe.netty.protocoltcp;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;

/**
 * @author ckh
 * @create 10/27/20 8:58 AM
 */
public class MyServerInitializer extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ch.pipeline()
                .addLast(new MyMessageDecoder())
                .addLast(new MyMessageEncoder())
                .addLast(new MyServerHandler());
    }
}
