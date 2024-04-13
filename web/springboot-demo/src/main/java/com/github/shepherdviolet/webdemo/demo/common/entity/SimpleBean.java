package com.github.shepherdviolet.webdemo.demo.common.entity;

import java.util.Map;

/**
 * 一个简单的Bean接口
 */
public interface SimpleBean {

    String getName();

    void setName(String name);

    Map<String, Object> getMap();

    void setMap(Map<String, Object> map);

}
