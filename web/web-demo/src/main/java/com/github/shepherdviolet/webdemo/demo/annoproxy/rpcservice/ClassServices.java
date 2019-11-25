package com.github.shepherdviolet.webdemo.demo.annoproxy.rpcservice;

import org.springframework.stereotype.Component;
import com.github.shepherdviolet.webdemo.demo.annoproxy.fakerpc.FakeRpc;

/**
 * 方法注解切面(非AspectJ)示例
 * 在类的方法上使用注释, 该对象的方法会直接被代理
 */
@Component
public class ClassServices {

    /**
     * 申明了该注释的方法会被代理
     */
    @FakeRpc(name = "service1", version = "1.0.0")
    public String service1(String request) {
        //不需要实现任何东西
        return null;
    }

}
