package com.joe.netty.inboundandoutboundhandler;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;

/**
 * @author ckh
 * @create 10/26/20 8:18 PM
 */
public class MyClientInitializer extends ChannelInitializer<SocketChannel> {

    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();
        // 加入 出站 handler, 对数据编码； 自定义handler 处理业务
        pipeline.addLast(new MyLongToByteEncoder())
                .addLast(new MyByteToLongDecoder2())
                .addLast(new MyClientHandler());
    }
}
