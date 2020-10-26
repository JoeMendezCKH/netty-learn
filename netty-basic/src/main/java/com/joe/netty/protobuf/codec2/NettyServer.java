package com.joe.netty.protobuf.codec2;

import com.joe.netty.protobuf.codec1.StudentPojo;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.protobuf.ProtobufDecoder;

/**
 * @author ckh
 * @create 10/23/20 11:31 AM
 */
public class NettyServer {

    public static final int PORT = 6668;

    public static void main(String[] args) throws InterruptedException {
        EventLoopGroup bossGroup = new NioEventLoopGroup(1);
        EventLoopGroup workerGroup = new NioEventLoopGroup();

        try {
            // create server starter configure
            ServerBootstrap bootstrap = new ServerBootstrap();
            bootstrap
                    .group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .option(ChannelOption.SO_BACKLOG, 128)
                    .childOption(ChannelOption.SO_KEEPALIVE, true)
                    .childHandler(
                            new ChannelInitializer<SocketChannel>() {
                                /**
                                 * 给 pipeline 设置处理器
                                 */
                                @Override
                                protected void initChannel(SocketChannel ch) throws Exception {
                                    ChannelPipeline pipeline = ch.pipeline();
                                    pipeline
                                            .addLast("decoder",
                                                    new ProtobufDecoder(MyDataInfo.MyMessage.getDefaultInstance()))
                                            .addLast(new NettyServerHandler());
                                }
                            });

            System.out.println("... server is ready ...");

            ChannelFuture channelFuture = bootstrap.bind(PORT).sync();

            channelFuture.addListener((ChannelFutureListener) future -> {
                if (channelFuture.isSuccess()) {
                    System.out.println("listen 6668 success...");
                } else {
                    System.out.println("listen 6668 failed...");
                }
            });

            channelFuture.channel().closeFuture().sync();
        } finally {
            // 关闭
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }
}
