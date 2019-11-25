package com.github.shepherdviolet.webdemo.demo.xmlconfig.entity;

/**
 * 用XML组装Beans示例
 */
public class XmlBean {

    public String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "XmlBean{" +
                "name='" + name + '\'' +
                '}';
    }
}
