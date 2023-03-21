package com.github.shepherdviolet.webdemo.demo.common.entity;

import java.util.Map;

/**
 * 一个简单的Bean
 */
public class SimpleBeanImpl implements SimpleBean {

    private String name;
    private Map<String, Object> map;

    public SimpleBeanImpl() {
    }

    public SimpleBeanImpl(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public Map<String, Object> getMap() {
        return map;
    }

    @Override
    public void setMap(Map<String, Object> map) {
        this.map = map;
    }

    @Override
    public String toString() {
        return "SimpleBeanImpl{" +
                "name='" + name + '\'' +
                ", map=" + map +
                '}';
    }
}
