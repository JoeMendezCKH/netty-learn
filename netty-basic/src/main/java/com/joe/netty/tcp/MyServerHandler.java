package com.joe.netty.tcp;

import io.netty.buffer.ByteBuf;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.CharsetUtil;

import java.nio.charset.StandardCharsets;
import java.util.UUID;

/**
 * @author ckh
 * @create 10/27/20 9:04 AM
 */
public class MyServerHandler extends SimpleChannelInboundHandler<ByteBuf> {

    private int count;

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ByteBuf msg) throws Exception {
        byte[] buffer = new byte[msg.readableBytes()];
        msg.readBytes(buffer);

        String message = new String(buffer, StandardCharsets.UTF_8);
        System.out.println("server receive msg: " + message + "\t");
        System.out.println("server count: " + (++this.count));

        ByteBuf response = Unpooled.copiedBuffer(UUID.randomUUID().toString() + "\t", CharsetUtil.UTF_8);
        ctx.writeAndFlush(response);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
