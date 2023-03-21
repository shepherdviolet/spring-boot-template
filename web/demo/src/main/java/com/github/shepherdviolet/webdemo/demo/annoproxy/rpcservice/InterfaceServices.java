package com.github.shepherdviolet.webdemo.demo.annoproxy.rpcservice;

import com.github.shepherdviolet.glacimon.spring.x.config.interfaceinst.InterfaceInstance;
import com.github.shepherdviolet.webdemo.demo.annoproxy.fakerpc.FakeRpc;

/**
 * 方法注解切面(非AspectJ)示例 + 接口类实例化(空实现)示例
 * 在接口的方法上使用注释, 注意, 接口必须声明@InterfaceInstance注释, 才会被框架实例化
 */
@InterfaceInstance
public interface InterfaceServices {

    /**
     * 申明了该注释的方法会被代理
     */
    @FakeRpc(name = "service2", version = "1.0.1")
    String service2(String request);

}
