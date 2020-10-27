package com.joe.netty.protobuf.codec1;

import io.netty.buffer.ByteBuf;
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

        // 发送一个 Student 对象到服务器
        StudentPojo.Student student = StudentPojo.Student.newBuilder().setId(4).setName("joe ckh").build();
        ctx.writeAndFlush(student);
    }

    /**
     * 通道有读取事件时, 会触发
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf buf = (ByteBuf) msg;
        System.out.println("Server address : " + ctx.channel().remoteAddress() + " replay: " + buf.toString(CharsetUtil.UTF_8));

    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        System.out.println("cause.getMessage() = " + cause.getMessage());
        ctx.close();
    }
}
