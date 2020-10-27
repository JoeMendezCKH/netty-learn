package com.joe.im.attribute;

import com.joe.im.session.Session;
import io.netty.util.AttributeKey;

/**
 * @author ckh
 * @create 10/27/20 3:25 PM
 */
public interface Attributes {
    AttributeKey<Boolean> LOGIN = AttributeKey.newInstance("login");

    AttributeKey<Session> SESSION = AttributeKey.newInstance("session");
}
