package com.github.shepherdviolet.webdemo.demo.aspectj.aspect;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * 多切点条件示例
 * args()示例
 */
@Component//这个必须
@Aspect//这个必须
//@Order(0)//同一个方法被多次切面时, 优先度越高(数字越小)的切面越先执行(越外层), 默认优先度最低(Integer.MAX_VALUE)
public class MultiAspect {

    private static final Logger logger = LoggerFactory.getLogger(MultiAspect.class);

    /**
     * 切点
     * com.github.shepherdviolet.webdemo.demo.aspectj.service任意子包下任意类, 两个方法参数, 且均标注了@Mark注解
     */
    @Pointcut(value = "execution(* com.github.shepherdviolet.webdemo.demo.aspectj.service..*.*(@com.github.shepherdviolet.webdemo.demo.aspectj.aspect.Mark (*), @com.github.shepherdviolet.webdemo.demo.aspectj.aspect.Mark (*)))")
    public void paramDoubleMarkPointcut(){
    }

    /**
     * 切点
     * args切点开销很大, 慎用
     * 实际传入的参数一个为Integer, 一个为Float, 注意这个类型是实际传入参数的类型, 不是方法签名
     */
    @Pointcut(value = "args(param1, param2)", argNames = "param1,param2")
    public void paramIntegerFloatPointcut(Integer param1, Float param2){
    }

    /**
     * 引用命名切点
     */
    @Before(value = "paramDoubleMarkPointcut() && paramIntegerFloatPointcut(param1, param2)", argNames = "param1,param2")
    public void ref(Integer param1, Float param2){
        logger.info("ref, " + param1 + " " + param2);
    }

    /**
     * 匿名切点
     * args切点开销很大, 慎用
     * com.github.shepherdviolet.webdemo.demo.aspectj.service任意子包下任意类, 方法有@Mark注解, 实际传入的参数为Integer, 注意这个类型是实际传入参数的类型, 不是方法签名
     */
    @Before(value = "execution(@com.github.shepherdviolet.webdemo.demo.aspectj.aspect.Mark * com.github.shepherdviolet.webdemo.demo.aspectj.service..*.*(..)) && args(param1)", argNames = "param1")
    public void anonymous(Integer param1){
        logger.info("anonymous, " + param1);
    }

}
