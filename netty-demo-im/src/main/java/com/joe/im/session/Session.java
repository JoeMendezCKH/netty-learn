package com.joe.im.session;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author ckh
 * @create 10/27/20 5:17 PM
 */
@Data
@NoArgsConstructor
public class Session {
    // 用户唯一性标识
    private String userId;
    private String userName;

    public Session(String userId, String userName) {
        this.userId = userId;
        this.userName = userName;
    }

    @Override
    public String toString() {
        return userId + ":" + userName;
    }

}
