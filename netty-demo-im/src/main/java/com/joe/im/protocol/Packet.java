package com.joe.im.protocol;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

/**
 * @author ckh
 * @create 10/27/20 10:44 AM
 */
@Data
public abstract class Packet {

    @JSONField(deserialize = false, serialize = false)
    private Byte version = 1;

    /**
     * get command
     *
     * @return command
     */
    @JSONField(serialize = false)
    public abstract Byte getCommand();


}
