package com.github.shepherdviolet.webdemo.demo.annoproxy.custom;

import com.github.shepherdviolet.glacimon.java.misc.CheckUtils;
import com.github.shepherdviolet.glacimon.spring.x.config.interfaceinst.ContextAwaredInterfaceInstantiator;
import com.github.shepherdviolet.webdemo.demo.annoproxy.service.PortalService;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;

import java.lang.reflect.Method;

/**
 * 接口类实例化(有实现)示例
 * 实现将接口的方法调用转发到PortalService
 */
public class CustomInterfaceInstantiator extends ContextAwaredInterfaceInstantiator {

    private PortalService portalService;

    @Override
    public String resolveBeanName(String interfaceType) throws Exception {
        Class<?> interfaceClass = Class.forName(interfaceType);
        if (interfaceClass.isAnnotationPresent(CustomAnnotation.class)) {
            CustomAnnotation annotation = interfaceClass.getAnnotation(CustomAnnotation.class);
            if (!CheckUtils.isEmptyOrBlank(annotation.id())){
                return annotation.id();
            }
        }
        return super.resolveBeanName(interfaceType);
    }

    @Override
    protected void onInitialized(Class<?> interfaceType, Object proxy) {

    }

    @Override
    protected Object onMethodInvoke(Class<?> interfaceType, Object proxy, Method method, Object[] objects) throws Throwable {
        if (portalService == null) {
            throw new Exception("Application is not started yet, portalService is null");
        }
        if (objects.length == 1 && objects[0] instanceof String && "handle".equals(method.getName())){
            return portalService.handle((String) objects[0]);
        }
        return null;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        portalService = applicationContext.getBean(PortalService.class);
    }

}
