package com.github.shepherdviolet.webdemo.incubating.session;

public class SessionInfo {

    private String hashId;
    private String token;
    private boolean isAnonymous;
    private boolean newToken;

    public SessionInfo(String hashId, String token, boolean isAnonymous, boolean newToken) {
        this.hashId = hashId;
        this.token = token;
        this.isAnonymous = isAnonymous;
        this.newToken = newToken;
    }

    public String getHashId() {
        return hashId;
    }

    public void setHashId(String hashId) {
        this.hashId = hashId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public boolean isAnonymous() {
        return isAnonymous;
    }

    public void setAnonymous(boolean anonymous) {
        isAnonymous = anonymous;
    }

    public boolean isNewToken() {
        return newToken;
    }

    public void setNewToken(boolean newToken) {
        this.newToken = newToken;
    }

    @Override
    public String toString() {
        return "SessionInfo{" +
                "hashId='" + hashId + '\'' +
                ", token='" + token + '\'' +
                ", isAnonymous=" + isAnonymous +
                ", newToken=" + newToken +
                '}';
    }

}
