package com.github.shepherdviolet.webdemo.demo.aspectj.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * within()示例
 */
@Component
@Aspect
public class WithinAspect {

    private static final Logger logger = LoggerFactory.getLogger(WithinAspect.class);

    /**
     * 调用前
     * 所有标注了@Mark注解的类的方法(类上面声明), 接口上声明的无效
     */
    @Before(value = "within(@com.github.shepherdviolet.webdemo.demo.aspectj.aspect.Mark *)")
    public void allClassWithMark(JoinPoint joinPoint){
        logger.info("all classes with mark, " + joinPoint.getArgs()[0]);
    }

    /**
     * 调用前
     * 所有实现了IService接口的类的方法
     */
    @Before(value = "within(com.github.shepherdviolet.webdemo.demo.aspectj.service.IService+)")
    public void allClassImplInterface(JoinPoint joinPoint){
        logger.info("all classes implements interface, " + joinPoint.getArgs()[0]);
    }

    /**
     * 调用前
     * 所有com.github.shepherdviolet.webdemo.demo.aspectj.service子包下的类的接口
     */
    @Before(value = "within(com.github.shepherdviolet.webdemo.demo.aspectj.service..*)")
    public void allClass(JoinPoint joinPoint){
        logger.info("all classes, " + joinPoint.getArgs()[0]);
    }

}
