package template.demo.annoproxy.fakerpc;

import org.springframework.aop.Pointcut;
import org.springframework.aop.support.AbstractBeanFactoryPointcutAdvisor;

/**
 * 自定义的Advisor
 *
 * 实现MyPointcut, 为自己的拦截器匹配正确的切点
 *
 * @author S.Violet
 */
public class FakeRpcAdvisor extends AbstractBeanFactoryPointcutAdvisor {

    static final String ADVISOR_BEAN_NAME = "spring.boot.template.annoproxy.fakerpcAdvisor";

    private String[] basePackages;

    @Override
    public Pointcut getPointcut() {
        //自定义的切点
        return new FakeRpcPointcut(basePackages);
    }

    void setBasePackages(String[] basePackages) {
        this.basePackages = basePackages;
    }
}
