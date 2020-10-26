package com.joe.netty.inboundandoutboundhandler;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

/**
 * @author ckh
 * @create 10/26/20 8:12 PM
 */
public class MyByteToLongDecoder extends ByteToMessageDecoder {

    /**
     * @param ctx 上下文
     * @param in  入站的 ByteBuf数据
     * @param out 传给下一个 handler 处理的数据
     */
    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        System.out.println("MyByteToLongDecoder.decode");
        // long 有 8 个字节
        if (in.readableBytes() >= 8) {
            out.add(in.readLong());
        }

    }
}
