package com.joe.netty.protobuf.codec2;

import com.joe.netty.protobuf.codec1.StudentPojo;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;

import java.util.Random;

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
        // 随机发送对象
        int random = new Random().nextInt(3);
        MyDataInfo.MyMessage message = null;
        if (random == 0) {
            message = MyDataInfo.MyMessage.newBuilder()
                    .setDataType(MyDataInfo.MyMessage.DataType.StudentType)
                    .setStudent(
                            MyDataInfo.Student.newBuilder()
                                    .setId(5)
                                    .setName("嵩哥牛逼")
                                    .build()
                    ).build();
        } else {
            message = MyDataInfo.MyMessage.newBuilder()
                    .setDataType(MyDataInfo.MyMessage.DataType.WorkerType)
                    .setWorker(
                            MyDataInfo.Worker.newBuilder()
                                    .setName("joe")
                                    .setAge(20)
                                    .build()
                    ).build();
        }
        ctx.writeAndFlush(message);
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
