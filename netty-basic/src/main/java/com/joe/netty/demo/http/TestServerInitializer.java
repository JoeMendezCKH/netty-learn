package com.joe.netty.demo.http;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpServerCodec;

/**
 * @author ckh
 * @create 10/23/20 4:06 PM
 */
public class TestServerInitializer extends ChannelInitializer<SocketChannel> {

    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        // add processor to pipeline
        ChannelPipeline pipeline = ch.pipeline();
        // 1. add a codec(coder - decoder) by netty
        /*
            HttpServerCodec: A combination of and HttpResponseEncoder which enables easier server side HTTP implementation.
         */
        pipeline.addLast("MyHttpServerCodec", new HttpServerCodec());

        // 2. add self handler
        pipeline.addLast("MyTestHttpServerHandler",new TestHttpServerHandler());
    }
}
