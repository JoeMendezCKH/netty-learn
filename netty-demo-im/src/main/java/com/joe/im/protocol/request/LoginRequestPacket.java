package com.joe.im.protocol.request;

import com.joe.im.protocol.Packet;
import com.joe.im.protocol.command.Command;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author ckh
 * @create 10/27/20 10:46 AM
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class LoginRequestPacket extends Packet {
    private String userId;

    private String username;

    private String password;

    @Override
    public Byte getCommand() {

        return Command.LOGIN_REQUEST;
    }
}
