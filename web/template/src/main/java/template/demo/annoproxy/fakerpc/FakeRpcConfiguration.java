package template.demo.annoproxy.fakerpc;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportAware;
import org.springframework.context.annotation.Role;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.type.AnnotationMetadata;

/**
 * 由注解启用的配置类
 *
 * @author S.Violet
 */
@Configuration
public class FakeRpcConfiguration implements ImportAware, ApplicationContextAware {

    private AnnotationAttributes annotationAttributes;
    private ApplicationContext applicationContext;

    /**
     * 获得注解的参数
     */
    @Override
    public void setImportMetadata(AnnotationMetadata importMetadata) {
        this.annotationAttributes = AnnotationAttributes.fromMap(
                importMetadata.getAnnotationAttributes(EnableFakeRpc.class.getName(), false));
        if (this.annotationAttributes == null) {
            throw new IllegalArgumentException(
                    "@EnableFakeRpc is not present on importing class " + importMetadata.getClassName());
        }
    }

    /**
     * 获得上下文
     */
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    /**
     * 实例化一个自定义的Advisor
     *
     * 这个类会打印警告日志:PostProcessorRegistrationDelegate$BeanPostProcessorChecker : Bean 'template.demo.annoproxy.
     * fakerpc.FakeRpcConfiguration' of type [template.demo.annoproxy.fakerpc.FakeRpcConfiguration$$EnhancerBySpring
     * CGLIB$$af681079] is not eligible for getting processed by all BeanPostProcessors (for example: not eligible for auto-proxying)
     *
     * 因为这个Advisor会被提前装配, 导致FakeRpcConfiguration类被提前装配, 所以提醒FakeRpcConfiguration类无法被所有的BeanPostProcessor处理
     */
    @Bean(name = FakeRpcAdvisor.ADVISOR_BEAN_NAME)
    @Role(BeanDefinition.ROLE_INFRASTRUCTURE)
    public FakeRpcAdvisor fakeRpcAdvisor() {

        //自定义的拦截器
        FakeRpcInterceptor fakeRpcInterceptor = new FakeRpcInterceptor();
        fakeRpcInterceptor.setApplicationContext(applicationContext);

        //自定义的Advisor
        FakeRpcAdvisor advisor = new FakeRpcAdvisor();
        advisor.setAdviceBeanName(FakeRpcAdvisor.ADVISOR_BEAN_NAME);
        advisor.setAdvice(fakeRpcInterceptor);
        advisor.setBasePackages(this.annotationAttributes.getStringArray("basePackages"));
        advisor.setOrder(this.annotationAttributes.<Integer>getNumber("order"));
        return advisor;
    }

}
