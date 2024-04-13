package com.github.shepherdviolet.webdemo.demo.annoproxy.custom;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 接口类实例化(有实现)示例
 * 声明该接口需要被实例化并将调用转到PortalService
 * @author S.Violet
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface CustomAnnotation {

    String id() default "";

}