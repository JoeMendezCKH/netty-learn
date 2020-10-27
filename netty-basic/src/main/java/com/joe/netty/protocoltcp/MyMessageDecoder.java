package com.joe.netty.protocoltcp;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ReplayingDecoder;

import java.util.List;

/**
 * @author ckh
 * @create 10/27/20 9:39 AM
 */
public class MyMessageDecoder extends ReplayingDecoder<Void> {
    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {

        System.out.println("MyMessageDecoder.decode");
        // 将得到的二进制字节码 -> messageProtocol
        int len = in.readInt();
        byte[] content = new byte[len];

        in.readBytes(content);

        MessageProtocol message = new MessageProtocol();
        message.setContent(content).setLength(len);

        out.add(message);
    }
}
