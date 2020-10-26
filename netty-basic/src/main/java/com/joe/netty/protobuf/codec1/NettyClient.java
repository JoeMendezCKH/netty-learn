package com.joe.netty.protobuf.codec1;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.protobuf.ProtobufEncoder;

/**
 * @author ckh
 * @create 10/23/20 2:45 PM
 */
public class NettyClient {
    public static final String HOST = "127.0.0.1";
    public static final int PORT = 6668;

    public static void main(String[] args) throws InterruptedException {
        // client need a event loop group
        EventLoopGroup group = new NioEventLoopGroup();

        try {
            // create client starter object
            Bootstrap bootstrap = new Bootstrap();

            // setting configure
            bootstrap
                    .group(group)
                    .channel(NioSocketChannel.class)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ch.pipeline()
                                    .addLast("encoder", new ProtobufEncoder())
                                    .addLast(new NettyClientHandler());
                        }
                    });
            System.out.println("client is ok ...");

            // start client to connect server
            ChannelFuture channelFuture = bootstrap.connect(HOST, PORT).sync();
            channelFuture.channel().closeFuture().sync();

        } finally {
            group.shutdownGracefully();
        }
    }
}
