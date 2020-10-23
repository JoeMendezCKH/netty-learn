package com.joe.netty.simple;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;

/**
 * @author ckh
 * @create 10/23/20 2:52 PM
 */
public class NettyClientHandler extends ChannelInboundHandlerAdapter {

    /**
     * 通道就绪时会触发该方法
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("client = " + ctx);
        ctx.writeAndFlush(Unpooled.copiedBuffer("hello server: MCGA", CharsetUtil.UTF_8));
    }

    /**
     * 通道有读取事件时, 会触发
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf buf = (ByteBuf) msg;
        System.out.println("server replay: " + buf.toString(CharsetUtil.UTF_8));
        System.out.println("Server address : " + ctx.channel().remoteAddress());
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        System.out.println("cause.getMessage() = " + cause.getMessage());
        ctx.close();
    }
}
