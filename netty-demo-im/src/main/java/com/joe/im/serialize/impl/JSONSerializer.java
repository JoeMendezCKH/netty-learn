package com.joe.im.serialize.impl;

import com.alibaba.fastjson.JSON;
import com.joe.im.serialize.Serializer;
import com.joe.im.serialize.SerializerAlgorithmic;

/**
 * @author ckh
 * @create 10/27/20 10:53 AM
 */
public class JSONSerializer implements Serializer {

    @Override
    public byte getSerializerAlgorithm() {
        return SerializerAlgorithmic.JSON;
    }

    @Override
    public byte[] serialize(Object object) {
        return JSON.toJSONBytes(object);
    }

    @Override
    public <T> T deserialize(Class<T> clazz, byte[] bytes) {
        return JSON.parseObject(bytes, clazz);
    }
}