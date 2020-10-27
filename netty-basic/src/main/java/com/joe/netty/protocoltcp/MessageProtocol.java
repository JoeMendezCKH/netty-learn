package com.joe.netty.protocoltcp;

/**
 * @author ckh
 * @create 10/27/20 9:32 AM
 */
public class MessageProtocol {
    private int length;
    public byte[] content;

    public int getLength() {
        return length;
    }

    public MessageProtocol setLength(int length) {
        this.length = length;
        return this;
    }

    public byte[] getContent() {
        return content;
    }

    public MessageProtocol setContent(byte[] content) {
        this.content = content;
        return this;
    }
}
