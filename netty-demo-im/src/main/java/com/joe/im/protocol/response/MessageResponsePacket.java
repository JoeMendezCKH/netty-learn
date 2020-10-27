package com.joe.im.protocol.response;

import com.joe.im.protocol.Packet;
import com.joe.im.protocol.command.Command;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author ckh
 * @create 10/27/20 3:23 PM
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class MessageResponsePacket extends Packet {
    private String fromUserId;

    private String fromUserName;

    private String message;

    @Override
    public Byte getCommand() {
        return Command.MESSAGE_RESPONSE;
    }
}
