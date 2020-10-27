package com.joe.im.server.hanlder;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.CharsetUtil;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Date;

/**
 * @author ckh
 * @create 10/27/20 11:05 AM
 */
public class FirstServerHandler extends SimpleChannelInboundHandler<ByteBuf> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ByteBuf msg) throws Exception {
        System.out.println(new Date() + ": 服务端读到数据 -> " + msg.toString(StandardCharsets.UTF_8));

        // 回复数据到客户端
        System.out.println(new Date() + ": 服务端写出数据");
        ByteBuf out = Unpooled.copiedBuffer("你好，song ge niu bi!", CharsetUtil.UTF_8);
        ctx.channel().writeAndFlush(out);
    }

}
