package com.joe.im.serialize;

import com.joe.im.serialize.impl.JSONSerializer;

/**
 * @author ckh
 * @create 10/27/20 10:49 AM
 */
public interface Serializer {

    Serializer DEFAULT = new JSONSerializer();

    /**
     * 序列化算法
     */
    byte getSerializerAlgorithm();

    /**
     * java 对象转换成二进制
     */
    byte[] serialize(Object object);

    /**
     * 二进制转换成 java 对象
     */
    <T> T deserialize(Class<T> clazz, byte[] bytes);
}
