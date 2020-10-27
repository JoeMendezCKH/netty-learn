package com.joe.im.protocol;

import com.joe.im.protocol.request.LoginRequestPacket;
import com.joe.im.serialize.Serializer;
import com.joe.im.serialize.impl.JSONSerializer;
import io.netty.buffer.ByteBuf;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author ckh
 * @create 10/27/20 11:00 AM
 */
public class PacketCodeCTest {
    @Test
    public void encode() {

        Serializer serializer = new JSONSerializer();
        LoginRequestPacket loginRequestPacket = new LoginRequestPacket();

        loginRequestPacket.setVersion(((byte) 1));
        loginRequestPacket.setUserId("123");
        loginRequestPacket.setUsername("zhangsan");
        loginRequestPacket.setPassword("password");

        PacketCodeC packetCodeC = PacketCodeC.INSTANCE;
        ByteBuf byteBuf = packetCodeC.encode(loginRequestPacket);
        Packet decodedPacket = packetCodeC.decode(byteBuf);

        assertArrayEquals(serializer.serialize(loginRequestPacket), serializer.serialize(decodedPacket));

    }

}