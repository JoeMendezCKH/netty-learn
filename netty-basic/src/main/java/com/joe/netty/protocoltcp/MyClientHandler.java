package com.joe.netty.protocoltcp;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.CharsetUtil;
import sun.nio.cs.ext.MS874;

/**
 * @author ckh
 * @create 10/27/20 9:04 AM
 */
public class MyClientHandler extends SimpleChannelInboundHandler<MessageProtocol> {

    private int count;

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        for (int i = 0; i < 10; i++) {
            String mes = "hello server , 嵩哥牛逼";
            byte[] content = mes.getBytes(CharsetUtil.UTF_8);
            int len = content.length;

            MessageProtocol message = new MessageProtocol();
            message.setLength(len).setContent(content);

            ctx.writeAndFlush(message);
        }
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, MessageProtocol msg) throws Exception {
        int len = msg.getLength();
        byte[] content = msg.getContent();

        System.out.println("client receive: ");
        System.out.println("len = " + len);
        System.out.println("content = " + new String(content,CharsetUtil.UTF_8));
        System.out.println("client count: "+(++this.count));
        System.out.println();

    }


    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        System.out.println(cause.getMessage());
        ctx.close();
    }
}
