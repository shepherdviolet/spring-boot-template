package com.github.shepherdviolet.webdemo.demo.annoproxy.custom;

import com.github.shepherdviolet.glacimon.spring.x.config.interfaceinst.InterfaceInstantiator;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * 接口类实例化(有实现)示例
 * 自定义的开关注解
 *
 * @author S.Violet
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import({CustomInterfaceInstSelector.class})
public @interface EnableCustomProxy {

    /**
     * 配置需要实例化的接口类包路径(可指定多个)
     */
    String[] basePackages();

    /**
     * 自定义的接口类实例化器
     */
    Class<? extends InterfaceInstantiator> interfaceInstantiator() default CustomInterfaceInstantiator.class;

    /**
     * true: 指定包路径下的接口类, 必须申明指定注解才进行实例化(注解类型由annotationClass指定, 默认@InterfaceInstance).
     * false: 指定包路径下的接口类, 不声明指定注解也进行实例化.
     * 默认true
     */
    boolean annotationRequired() default true;

    /**
     * 当annotationRequired为true时, 指定包路径下的接口类必须声明指定的注解才能实例化, 注解类型可以在这里定义.
     * 自定义为CustomAnnotation
     */
    Class<? extends Annotation> annotationClass() default CustomAnnotation.class;

}
