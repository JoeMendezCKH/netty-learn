package com.joe.netty.inboundandoutboundhandler;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;

/**
 * @author ckh
 * @create 10/26/20 8:11 PM
 */
public class MyServerInitializer extends ChannelInitializer<SocketChannel> {

    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();
        // 入站 handler 进行解码, MyByteToLongDecoder
        pipeline.addLast(new MyByteToLongDecoder2())
                .addLast(new MyLongToByteEncoder())
                .addLast(new MyServerHandler());
    }
}
