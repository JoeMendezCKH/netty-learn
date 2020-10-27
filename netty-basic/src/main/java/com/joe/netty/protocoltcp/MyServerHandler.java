package com.joe.netty.protocoltcp;

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
public class MyServerHandler extends SimpleChannelInboundHandler<MessageProtocol> {

    private int count;

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, MessageProtocol msg) throws Exception {
        int length = msg.getLength();
        byte[] content = msg.getContent();

        System.out.println("server receive: ");
        System.out.println("length = " + length);
        System.out.println("content = " + new String(content, CharsetUtil.UTF_8));
        System.out.println("server count: " + (++this.count));
        System.out.println();

        String response = UUID.randomUUID().toString();
        int resLen = response.getBytes(CharsetUtil.UTF_8).length;
        MessageProtocol res = new MessageProtocol().setLength(resLen).setContent(response.getBytes());
        ctx.writeAndFlush(res);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
