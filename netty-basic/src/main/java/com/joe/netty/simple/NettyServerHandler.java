package com.joe.netty.simple;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;

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
        System.out.println("server ctx = " + ctx);
        // msg -> byte buffer (netty 提供的)
        ByteBuf byteBuf = (ByteBuf) msg;
        System.out.println("client send msg = " + byteBuf.toString(CharsetUtil.UTF_8));
        System.out.println("client address = " + ctx.channel().remoteAddress());
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
}
