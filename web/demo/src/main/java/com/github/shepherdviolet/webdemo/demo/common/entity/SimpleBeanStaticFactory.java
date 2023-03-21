package com.github.shepherdviolet.webdemo.demo.common.entity;

public class SimpleBeanStaticFactory {

    public static SimpleBean newSimpleBean(String name) {
        return new SimpleBeanImpl(name);
    }

}
