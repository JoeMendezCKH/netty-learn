package com.joe.netty.websocket;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;

import java.time.LocalDateTime;

/**
 * TextWebSocketFrame 表示一个文本帧
 *
 * @author ckh
 * @create 10/26/20 11:06 AM
 */
public class MyTextWebSocketFrameHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, TextWebSocketFrame msg) throws Exception {
        System.out.println("server receive: " + msg.text());

        // 回显消息
        ctx.channel().writeAndFlush(new TextWebSocketFrame("server time: " + LocalDateTime.now() + " ->" + msg.text()));

    }

    /**
     * 连接建立后第一个被执行
     * web 客户端连接后触发
     */
    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        // id 表示唯一的值, LongText 是唯一的, ShortText 不是唯一
        System.out.println("handlerAdded using: " + ctx.channel().id().asLongText());
        System.out.println("handlerAdded using: " + ctx.channel().id().asShortText());
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        System.out.println("handlerRemoved using: " + ctx.channel().id().asLongText());
        System.out.println("handlerRemoved using: " + ctx.channel().id().asShortText());
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        System.out.println("exception " + cause.getMessage());
        ctx.close();
    }
}
