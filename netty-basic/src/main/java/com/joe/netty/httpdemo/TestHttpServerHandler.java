package com.joe.netty.httpdemo;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.*;
import io.netty.util.CharsetUtil;

/**
 * 1. SimpleChannelInboundHandler extends ChannelInboundHandlerAdapter
 * 2. HttpObject : 客户端和服务器段通信的数据被封装的数据类型
 *
 * @author ckh
 * @create 10/23/20 4:05 PM
 */
public class TestHttpServerHandler extends SimpleChannelInboundHandler<HttpObject> {

    /**
     * 有读取事件发生时会触发
     */
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, HttpObject msg) throws Exception {
        if (msg instanceof HttpRequest) {

            System.out.println("pipeline hash = " + ctx.pipeline().hashCode() +
                    " TestHttpServerHandler hash = " + this.hashCode());

            System.out.println("msg = " + msg.getClass());
            System.out.println("address = " + ctx.channel().remoteAddress());

            HttpRequest request = (HttpRequest) msg;
            // 获取 uri 过滤指定的资源
            String uri = request.uri();
            if ("/favicon.ico".equals(uri)) {
                System.out.println("request for \"favicon.ico\", no response");
                return;
            }

            // replay info to browser
            ByteBuf content = Unpooled.copiedBuffer("hello, this is server 你好 ", CharsetUtil.UTF_8);

            // construct a http response
            FullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1,
                    HttpResponseStatus.OK, content);

            response.headers()
                    .set(HttpHeaderNames.CONTENT_TYPE, "text/plain;charset=utf-8")
                    .set(HttpHeaderNames.CONTENT_LENGTH, content.readableBytes());

            ctx.writeAndFlush(response);
            System.out.println("========================================");
        }

    }
}
