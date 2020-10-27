package com.joe.im.protocol.command;

/**
 * @author ckh
 * @create 10/27/20 10:46 AM
 */
public interface Command {
    Byte LOGIN_REQUEST = 1;
    Byte LOGIN_RESPONSE = 2;
    Byte MESSAGE_REQUEST = 3;
    Byte MESSAGE_RESPONSE = 4;
}
