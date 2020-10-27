package com.joe.im.protocol.response;

import com.joe.im.protocol.Packet;
import lombok.Data;
import lombok.EqualsAndHashCode;

import static com.joe.im.protocol.command.Command.LOGIN_RESPONSE;

/**
 * @author ckh
 * @create 10/27/20 3:09 PM
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class LoginResponsePacket extends Packet {
    private String userId;

    private String userName;

    private boolean success;

    private String reason;

    @Override
    public Byte getCommand() {
        return LOGIN_RESPONSE;
    }
}
