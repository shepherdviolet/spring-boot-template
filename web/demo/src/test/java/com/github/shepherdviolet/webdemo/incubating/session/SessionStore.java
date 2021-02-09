package com.github.shepherdviolet.webdemo.incubating.session;

import java.util.Map;

public interface SessionStore {

    /**
     * 清除会话相关的所有数据
     * @param hashId 哈希ID(哈希值, 第一位'1'表示登录用户, '0'表示匿名用户)
     */
    void cleanSession(String hashId);

    /**
     * 获取Token的盐值, 不存在则新建, 每次刷新有效期
     * @param hashId 哈希ID(哈希值, 第一位'1'表示登录用户, '0'表示匿名用户)
     * @return Token的盐值
     */
    String getTokenSalt(String hashId);

    /**
     * 获取会话上下文, 不存在返回null, 每次刷新有效期
     * @param hashId 哈希ID(哈希值, 第一位'1'表示登录用户, '0'表示匿名用户)
     * @return 会话上下文
     */
    Map<String, Object> getSessionContext(String hashId);

}
