package com.github.shepherdviolet.webdemo.demo.common.entity;

import org.springframework.beans.factory.support.MethodReplacer;

import java.lang.reflect.Method;

/**
 * 用于在xml里使用replaced-method标签替换某个Bean的某个方法实现
 */
public class TestMethodReplacer implements MethodReplacer {

    @Override
    public Object reimplement(Object obj, Method method, Object[] args) throws Throwable {
        return new SimpleBeanImpl("I'm from replacer");
    }

}
