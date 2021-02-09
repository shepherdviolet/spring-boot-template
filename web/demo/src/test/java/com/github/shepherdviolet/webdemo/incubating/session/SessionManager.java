package com.github.shepherdviolet.webdemo.incubating.session;

import sviolet.thistle.util.conversion.ByteUtils;
import sviolet.thistle.util.crypto.DigestCipher;

import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.UUID;

public class SessionManager {

    private static final int MAX_TOKEN_LENGTH = 200;

    private SessionStore sessionStore;
    private long expire = 1000;
    private long refresh = 500;
    private String tokenKey = "26u34poitu;qo4iutrq;kwjfwi";

    /**
     * 用户ID转哈希ID
     * @param id 用户ID
     * @return 哈希ID(哈希值, 第一位'1'表示登录用户, '0'表示匿名用户)
     */
    public String toHashId(String id) {
        return id != null ? "1" + hash(id) : newAnonymousHashId();
    }

    /**
     * 生成一个匿名哈希ID
     * @return 哈希ID(哈希值, 第一位'1'表示登录用户, '0'表示匿名用户)
     */
    public String newAnonymousHashId() {
        return "0" + hash(UUID.randomUUID().toString());
    }

    /**
     * 开始一个新会话
     * @param hashId 哈希ID(哈希值, 第一位'1'表示登录用户, '0'表示匿名用户)
     * @return 会话Token
     */
    public String newSession(String hashId) {
        if (hashId == null) {
            hashId = newAnonymousHashId();
        }
        return newToken(hashId);
    }

    /**
     * 验证会话Token, 如果无效或者过期会抛出异常, 成功会返回相关信息
     * @param token 会话Token
     * @return 会话相关信息
     */
    public SessionInfo verifyToken(String token) {
        if (token == null || token.length() > MAX_TOKEN_LENGTH) {
            throw new IllegalArgumentException("Illegal token");
        }
        String[] elements = token.split("-");
        if (elements.length != 3) {
            throw new IllegalArgumentException("Illegal token");
        }
        String hashId = elements[0];
        String timestampStr = elements[1];
        long timestamp;
        try {
            timestamp = Long.valueOf(timestampStr, 16);
        } catch (Throwable t) {
            throw new IllegalArgumentException("Illegal token", t);
        }
        long elapse = currentTimestamp() - timestamp;
        if (elapse > expire || elapse < -expire) {
            throw new IllegalArgumentException("Token expired");
        }
        if (!elements[2].equals(hmac(hashId, timestampStr))) {
            throw new IllegalArgumentException("Token expired");
        }
        boolean newToken = false;
        if (elapse > refresh) {
            token = newToken(hashId);
            newToken = true;
        }
        return new SessionInfo(hashId, token, !hashId.startsWith("1"), newToken);
    }

    public void cleanSession(String hashId) {
        sessionStore.cleanSession(hashId);
    }

    public Map<String, Object> getSessionContext(String hashId) {
        return sessionStore.getSessionContext(hashId);
    }

    protected long currentTimestamp() {
        return System.currentTimeMillis();
    }

    protected String newToken(String hashId) {
        String hexTimestamp = Long.toString(currentTimestamp(), 16);
        return hashId + "-" + hexTimestamp + "-" + hmac(hashId, hexTimestamp);
    }

    protected String hmac(String hashId, String hexTimestamp) {
        return hash(hashId + "-" + hexTimestamp + "-" + sessionStore.getTokenSalt(hashId) + "-" + tokenKey);
    }

    protected String hash(String s) {
        return ByteUtils.bytesToHex(DigestCipher.digest(s.getBytes(StandardCharsets.UTF_8), DigestCipher.TYPE_SHA1));
    }

    public SessionStore getSessionStore() {
        return sessionStore;
    }

    public void setSessionStore(SessionStore sessionStore) {
        this.sessionStore = sessionStore;
    }

    public long getExpire() {
        return expire;
    }

    public void setExpire(long expire) {
        this.expire = expire;
    }

    public long getRefresh() {
        return refresh;
    }

    public void setRefresh(long refresh) {
        this.refresh = refresh;
    }

    public String getTokenKey() {
        return tokenKey;
    }

    public void setTokenKey(String tokenKey) {
        this.tokenKey = tokenKey;
    }

}
