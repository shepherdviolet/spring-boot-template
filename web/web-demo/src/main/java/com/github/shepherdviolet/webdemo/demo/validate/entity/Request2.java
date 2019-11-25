package com.github.shepherdviolet.webdemo.demo.validate.entity;

import com.github.shepherdviolet.webdemo.demo.validate.validation.BinaryFlagMatch;

public class Request2 {

    /**
     * 自定义检查
     */
    @BinaryFlagMatch(0x00001000)
    private int state;

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    @Override
    public String toString() {
        return "Request2{" +
                "state=" + state +
                '}';
    }
}
