package com.github.shepherdviolet.webdemo.demo.mybatis.entity;

/**
 * UserAuth DO
 */
public class UserAuth {

    private long authId;

    private String username;

    private String authority;

    public long getAuthId() {
        return authId;
    }

    public void setAuthId(long authId) {
        this.authId = authId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAuthority() {
        return authority;
    }

    public void setAuthority(String authority) {
        this.authority = authority;
    }

    @Override
    public String toString() {
        return "UserAuth{" +
                "authId=" + authId +
                ", username='" + username + '\'' +
                ", authority='" + authority + '\'' +
                '}';
    }

}
