package com.joe.netty.heartbeat;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;

/**
 * @author ckh
 * @create 10/26/20 10:41 AM
 */
public class MyServerHeartHandler extends ChannelInboundHandlerAdapter {

    /**
     * @param ctx 上下文
     * @param evt 事件
     * @throws Exception exception
     */
    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if (evt instanceof IdleStateEvent) {
            IdleStateEvent stateEvent = (IdleStateEvent) evt;

            System.out.println(ctx.channel().remoteAddress() + " -- idle event -- " + stateEvent.state());

            switch (stateEvent.state()) {
                case READER_IDLE:
                    System.out.println("solving...reader");
                    break;
                case WRITER_IDLE:
                    System.out.println("solving...writer");
                    break;
                case ALL_IDLE:
                    System.out.println("solving...all");
                    break;
            }
        }
    }
}
