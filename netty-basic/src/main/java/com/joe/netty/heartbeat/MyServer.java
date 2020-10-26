package com.joe.netty.heartbeat;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.timeout.IdleStateHandler;

import java.util.concurrent.TimeUnit;

/**
 * @author ckh
 * @create 10/26/20 10:15 AM
 */
public class MyServer {


    public static void main(String[] args) {

        int port = 7000;

        EventLoopGroup bossGroup = new NioEventLoopGroup(1);
        EventLoopGroup workerGroup = new NioEventLoopGroup();

        try {
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            serverBootstrap.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .handler(new LoggingHandler(LogLevel.INFO))
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ch.pipeline()
                                    /*
                                        netty 提供的 IdleStateHandler, 处理空闲状态的处理器, 可能触发 IdleStateEvent 事件

                                        @param readerIdleTime 多长时间没有读, 就会发送一个心跳检测包, 检测是否连接,
                                        @param writerIdleTime 多长时间没有写, 就会发送一个心跳检测包, 检测是否连接
                                        @param allIdleTime 多长时间没有读写, 就会发送一个心跳检测包, 检测是否连接
                                        @param unit 时间单位

                                        当 IdleStateEvent 触发后, 会传递给管道的下一个 handler 处理,
                                        触发 handler的 userEventTriggered 方法
                                     */
                                    .addLast(new IdleStateHandler(3, 5, 7, TimeUnit.SECONDS))
                                    // 对空闲检测进一步处理, 自定义
                                    .addLast(new MyServerHeartHandler());
                        }
                    });

            System.out.println("server is starting ...");
            ChannelFuture channelFuture = serverBootstrap.bind(port).sync();
            channelFuture.channel().closeFuture().sync();

        } catch (InterruptedException e) {
            System.out.println(e.getMessage());
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }
}
