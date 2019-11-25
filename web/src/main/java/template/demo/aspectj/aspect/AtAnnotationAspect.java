package template.demo.aspectj.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * <pre>@annotation()示例</pre>
 */
@Component
@Aspect
public class AtAnnotationAspect {

    private static final Logger logger = LoggerFactory.getLogger(AtAnnotationAspect.class);

    /**
     * 调用前
     * 所有标注了@Mark注解的方法(方法上面声明), 接口上声明的无效
     */
    @Before(value = "@annotation(template.demo.aspectj.aspect.Mark)")
    public void allMethodsWithMark(JoinPoint joinPoint){
        logger.info("all methods with @Mark, " + joinPoint.getArgs()[0]);
    }

    /**
     * 调用前
     * 所有标注了@Mark注解的方法(方法上面声明), 接口上声明的无效, 并能够获取注解参数
     */
    @Before(value = "@annotation(mark)", argNames = "joinPoint,mark")
    public void allMethodsWithMarkHasParam(JoinPoint joinPoint, Mark mark){
        logger.info("all methods with @Mark has param, " + mark.param() + ", " + joinPoint.getArgs()[0]);
    }

}
