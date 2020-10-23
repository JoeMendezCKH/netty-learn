package com.joe.netty.simple;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelPipeline;
import io.netty.util.CharsetUtil;

import java.util.concurrent.TimeUnit;

/**
 * 1. 自定义的 handler 需要继承 Netty 规定好的某个 HandlerAdapter
 * 2. 这时我们自定义的 Handler 才能称为一个 handler
 *
 * @author ckh
 * @create 10/23/20 2:30 PM
 */
public class NettyServerHandler extends ChannelInboundHandlerAdapter {

    /**
     * 读取数据事件, 可以读取客户端发送的消息
     *
     * @param ctx 上下文对象, 含有管道pipeline, 通道channel, 地址等信息
     * @param msg 客户端发送的数据, 默认是 Object
     * @throws Exception ex
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
//        test1(ctx, (ByteBuf) msg);
        // 如果这有一个耗时的业务, 会阻塞客户端, 不好
//        TimeUnit.SECONDS.sleep(5);
//        ctx.writeAndFlush(Unpooled.copiedBuffer("hello client, wait long time..", CharsetUtil.UTF_8));

        // 需要异步执行 -> 提交到该channel 对应的 NioEventLoop 的 TaskQueue 中
        // solve 1 : 自定义普通任务
        ctx.channel().eventLoop().execute(() -> {
            try {
                TimeUnit.SECONDS.sleep(3);
                ctx.writeAndFlush(Unpooled.copiedBuffer("hello client, wait long time.. method1, 11111111 \t ", CharsetUtil.UTF_8));
            } catch (InterruptedException e) {
                System.out.println(e.getMessage());
            }
        });
        // 会在上面任务执行结束后再计时 6s
        ctx.channel().eventLoop().execute(() -> {
            try {
                TimeUnit.SECONDS.sleep(6);
                ctx.writeAndFlush(Unpooled.copiedBuffer("hello client, wait long time.. method1, 22222222 \t ", CharsetUtil.UTF_8));
            } catch (InterruptedException e) {
                System.out.println(e.getMessage());
            }
        });

        System.out.println("go on to complete");
    }

    /**
     * 数据读取完毕
     */
    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        // write and flush, send message need to encode
        ctx.writeAndFlush(Unpooled.copiedBuffer("hello client!", CharsetUtil.UTF_8));
    }

    /**
     * 处理异常, 一般就是关闭通道
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.channel().close();
    }

    private void test1(ChannelHandlerContext ctx, ByteBuf msg) {
        System.out.println("server thread : " + Thread.currentThread().getName());
        System.out.println("server ctx = " + ctx);

        System.out.println("debug pipeline vs channel");
        Channel channel = ctx.channel();
        // pipeline is a double linked
        ChannelPipeline pipeline = ctx.pipeline();

        // msg -> byte buffer (netty 提供的)
        ByteBuf byteBuf = msg;
        System.out.println("client send msg = " + byteBuf.toString(CharsetUtil.UTF_8));
        System.out.println("client address = " + channel.remoteAddress());
    }
}
