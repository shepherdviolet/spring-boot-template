package com.github.shepherdviolet.webdemo.demo.annoproxy.custom;

import com.github.shepherdviolet.glacimon.spring.x.config.interfaceinst.InterfaceInstSelector;

import java.lang.annotation.Annotation;

/**
 * 接口类实例化(有实现)示例
 * 自定义的ImportSelector, 配合EnableCustomProxy注解开启功能
 * @author S.Violet
 */
public class CustomInterfaceInstSelector extends InterfaceInstSelector {

    /**
     * @return 返回配套的EnableCustomProxy注解类(父类InterfaceInstSelector会根据这个类型解析注解参数)
     */
    @Override
    protected Class<? extends Annotation> getEnableAnnotationType() {
        return EnableCustomProxy.class;
    }

}
