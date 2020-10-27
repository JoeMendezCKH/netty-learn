package com.joe.netty.inboundandoutboundhandler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * @author ckh
 * @create 10/26/20 8:22 PM
 */
public class MyClientHandler extends SimpleChannelInboundHandler<Long> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Long msg) throws Exception {
        System.out.println("server ip: " + ctx.channel().remoteAddress() + " send: " + msg);
    }

    /**
     * send message
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("MyClientHandler.channelActive send data");

        ctx.writeAndFlush(123456L);
    }
}
