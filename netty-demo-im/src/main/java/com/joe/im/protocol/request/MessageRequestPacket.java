package com.joe.im.protocol.request;

import com.joe.im.protocol.Packet;
import com.joe.im.protocol.command.Command;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * @author ckh
 * @create 10/27/20 3:22 PM
 */
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
public class MessageRequestPacket extends Packet {
    private String toUserId;
    private String message;

    public MessageRequestPacket(String toUserId, String message) {
        this.toUserId = toUserId;
        this.message = message;
    }

    @Override
    public Byte getCommand() {
        return Command.MESSAGE_REQUEST;
    }
}