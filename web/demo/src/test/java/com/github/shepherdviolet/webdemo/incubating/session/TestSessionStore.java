package com.github.shepherdviolet.webdemo.incubating.session;

import sviolet.thistle.util.crypto.SecureRandomUtils;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 测试用会话存储器, 注意, 数据无有效期, 不会清理
 *
 * @author S.Violet
 */
public class TestSessionStore implements SessionStore {

    private static final String SALT_PREFIX = "session-salt-";
    private static final String CONTEXT_PREFIX = "session-context-";

    private final Map<String, Object> cache = new ConcurrentHashMap<>();

    /**
     * @inheritDoc
     */
    @Override
    public void cleanSession(String hashId) {
        cache.remove("session-salt-" + hashId);
        cache.remove("session-context-" + hashId);
    }

    /**
     * @inheritDoc
     */
    @Override
    public String getTokenSalt(String hashId) {
        String key = SALT_PREFIX + hashId;
        // 未处理: 类型转换异常
        String salt = (String) cache.get(key);
        if (salt == null) {
            salt = genSalt(hashId);
            // setnx
            cache.putIfAbsent(key, salt);
            // 重新获取 (模拟Redis的情况)
            // 未处理: 类型转换异常
            salt = (String) cache.get(key);
            // 失败 (模拟Redis的情况)
            if (salt == null) {
                throw new IllegalStateException("Cache unavailable");
            }
        }
        // 未实现: 延长数据有效期
        return salt;
    }

    protected String genSalt(String hashId) {
        return SecureRandomUtils.nextLong() + "-" + UUID.randomUUID().toString();
    }

    /**
     * @inheritDoc
     */
    @Override
    public Map<String, Object> getSessionContext(String hashId) {
        String key = "session-context-" + hashId;
        // 未实现: 延长数据有效期
        // 未处理: 类型转换异常
        return (Map<String, Object>) cache.get(key);
    }

}
