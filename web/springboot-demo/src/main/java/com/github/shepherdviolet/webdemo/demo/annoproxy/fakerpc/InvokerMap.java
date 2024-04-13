package com.github.shepherdviolet.webdemo.demo.annoproxy.fakerpc;

import org.springframework.asm.Type;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Invoker缓存, 代理逻辑执行时, 可以从这里获取配置信息
 *
 * @author S.Violet
 */
class InvokerMap {

    private static Map<String, FakeRpcInvoker> invokers = new ConcurrentHashMap<>(128);

    static void put(String key, FakeRpcInvoker fakeRpcInvoker) {
        invokers.put(key, fakeRpcInvoker);
    }

    static FakeRpcInvoker get(String key) {
        return invokers.get(key);
    }

    /**
     * 根据方法和对象类生成缓存key
     */
    static String getKey(Method method, Class targetClass) {
        StringBuilder sb = new StringBuilder();
        sb.append(method.getDeclaringClass().getName());
        sb.append('.');
        sb.append(method.getName());
        sb.append(Type.getMethodDescriptor(method));
        if (targetClass != null) {
            sb.append('_');
            sb.append(targetClass.getName());
        }
        return sb.toString();
    }

}
