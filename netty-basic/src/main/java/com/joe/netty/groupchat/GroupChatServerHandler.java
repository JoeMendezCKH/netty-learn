package com.joe.netty.groupchat;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;

import javax.lang.model.element.VariableElement;
import java.text.SimpleDateFormat;

/**
 * @author ckh
 * @create 10/26/20 9:09 AM
 */
public class GroupChatServerHandler extends SimpleChannelInboundHandler<String> {

    /**
     * 定义一个channel组, 管理所有的channel
     * GlobalEventExecutor.INSTANCE : 全局事件执行器, 是一个单例
     */
    private static ChannelGroup channelGroup = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

    /**
     * 连接建立后第一个被执行
     * 将当前channel 加入到 channelGroup
     */
    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        // 转发消息给 channelGroup 中所有的 channel
        // 推送该客户端上线消息到其他客户端
        channelGroup.writeAndFlush("[client] " + channel.remoteAddress() + " join the chatting room... \n");
        channelGroup.add(channel);
    }

    /**
     * 断开连接
     * 会从 channelGroup 中移除该 channel
     * xx 离开推送给其他客户端
     */
    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        channelGroup.writeAndFlush("[client] " + channel.remoteAddress() + " left the chatting room... \n");
        System.out.println("channelGroup.size() = " + channelGroup.size());
    }

    /**
     * channel 处于活动状态
     * 提示 xx 上线
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println(ctx.channel().remoteAddress() + " on line... ");
    }

    /**
     * channel 处于不活动状态
     * xx 离线
     */
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        System.out.println(ctx.channel().remoteAddress() + " off line... ");
    }

    /**
     * 有读取事件发生时会触发
     * 处理: 读取数据并转发
     */
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
        Channel channel = ctx.channel();
        channelGroup.forEach(ch -> {
            if (ch != channel) {
                ch.writeAndFlush("[client]" + channel.remoteAddress() + " send msg " + msg + "\n");
            } else {
                ch.writeAndFlush("[self] send " + msg + "\n");
            }
        });
//        channelGroup.writeAndFlush("[client]" + channel.remoteAddress() + " send msg " + msg + "\n");

    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }
}
