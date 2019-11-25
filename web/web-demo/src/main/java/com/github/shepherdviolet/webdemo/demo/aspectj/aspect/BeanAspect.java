package com.github.shepherdviolet.webdemo.demo.aspectj.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * bean()示例
 */
@Component
@Aspect
public class BeanAspect {

    private static final Logger logger = LoggerFactory.getLogger(BeanAspect.class);

    /**
     * 调用前
     * 所有名称为*AJService的Bean
     */
    @Before(value = "bean(*AJService)")
    public void allBeanWithAJService(JoinPoint joinPoint){
        logger.info("all bean named *AJService, " + joinPoint.getArgs()[0]);
    }

}
