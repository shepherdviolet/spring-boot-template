package com.github.shepherdviolet.webdemo.demo.aspectj.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * execution()示例, 匹配方法执行
 */
@Component//这个必须
@Aspect//这个必须
//@Order(0)//同一个方法被多次切面时, 优先度越高(数字越小)的切面越先执行(越外层), 默认优先度最低(Integer.MAX_VALUE)
public class ExecutionAspect {

    private static final Logger logger = LoggerFactory.getLogger(ExecutionAspect.class);

    /**
     * 抛出异常后
     * com.github.shepherdviolet.webdemo.demo.aspectj.service任意子包下任意public类任意方法
     */
    @AfterThrowing(pointcut = "execution(public * com.github.shepherdviolet.webdemo.demo.aspectj.service..*.*(..))", throwing = "throwable", argNames = "throwable")
    public void afterThrowingAllPublic(Throwable throwable) {
        logger.error("catch a throwable, " + throwable.getMessage());
    }

    /**
     * 环绕
     * 任意实现了"com.github.shepherdviolet.webdemo.demo.aspectj.service任意子包下IService接口"的类的method1方法
     */
    @Around("execution(* com.github.shepherdviolet.webdemo.demo.aspectj.service..IService+.method1(..))")
    public Object surroundAllIServiceImpl(ProceedingJoinPoint joinPoint) throws Throwable {
        logger.info("around all iservice impl, " + joinPoint.getArgs()[0]);
        Object returnValue = joinPoint.proceed(joinPoint.getArgs());
        logger.info("around all iservice impl, " + joinPoint.getArgs()[0]);
        return returnValue;
    }

    /**
     * 调用前
     * com.github.shepherdviolet.webdemo.demo.aspectj.service任意子包下, 类名为Service1, 参数类型为一个Number的子类, 返回类型为Long
     * 注意:这里参数类型和返回类型是方法签名指定的类型(即方法上定义的类型, 不是调用时实际传入的参数类型)
     */
    @Before("execution(java.lang.Long com.github.shepherdviolet.webdemo.demo.aspectj.service..Service1.*(java.lang.Number+))")
    public void beforeService1StringString(JoinPoint joinPoint){
        logger.info("before service1 param:number+ return:long, " + joinPoint.getArgs()[0]);
    }

    /**
     * 调用后
     * com.github.shepherdviolet.webdemo.demo.aspectj.service任意子包下, 类名为Ser*, 方法名为met*
     */
    @After("execution(* com.github.shepherdviolet.webdemo.demo.aspectj.service..Ser*.met*(..))")
    public void afterSerMet(JoinPoint joinPoint) {
        logger.info("after ser* met*");
    }

}
