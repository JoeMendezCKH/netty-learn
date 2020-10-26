package com.joe.netty.websocket;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.stream.ChunkedWriteHandler;

/**
 * @author ckh
 * @create 10/26/20 10:55 AM
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
                            ChannelPipeline pipeline = ch.pipeline();
                            pipeline
                                    // 基于 http 编解码
                                    .addLast(new HttpServerCodec())
                                    // 以块方式写, 添加 ChunkedWriteHandler 处理器
                                    .addLast(new ChunkedWriteHandler())
                                    /*
                                        1. http 数据在传输过程中是分段的, HttpObjectAggregator 可以将多个段聚合
                                        2. 当浏览器发送大量数据时, 会发出多次请求
                                     */
                                    .addLast(new HttpObjectAggregator(8192))
                                    /*
                                        1. 对应 webSocket , 数据以帧(frame)的形式传递
                                        2. 可以看到 webSocketFrame 有6个子类
                                        3. 浏览器请求时: ws://localhost:7000/hello 表示请求的uri
                                        4. WebSocketServerProtocolHandler 核心功能: 将 http 协议升级为 ws 协议, 保持长连接
                                        5. 通过一个状态码 101
                                     */
                                    .addLast(new WebSocketServerProtocolHandler("/hello"))
                                    // 处理业务逻辑
                                    .addLast(new MyTextWebSocketFrameHandler());
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
