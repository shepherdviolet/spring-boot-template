package com.github.shepherdviolet.webdemo.demo.annoproxy.fakerpc;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 声明一个方法需要做代理
 * 这里模拟声明一个RPC服务
 * @author S.Violet
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface FakeRpc {

    /**
     * 自定义参数
     * 服务名
     */
    String name();

    /**
     * 自定义参数
     * 服务版本
     */
    String version();

}