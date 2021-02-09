package com.github.shepherdviolet.webdemo.demo.annoproxy.fakerpc;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * 拦截器, 实现代理逻辑
 *
 * @author S.Violet
 */
class FakeRpcInterceptor implements MethodInterceptor, ApplicationContextAware {

    private ApplicationContext applicationContext;

    /**
     * 获得上下文
     */
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    @Override
    public Object invoke(final MethodInvocation invocation) throws Throwable {
        //从缓存获取invoker
        String key = InvokerMap.getKey(invocation.getMethod(), invocation.getThis().getClass());
        FakeRpcInvoker fakeRpcInvoker = InvokerMap.get(key);

        //invoker不为空则继续调用链
        if (fakeRpcInvoker == null) {
            return invocation.proceed();
        }

        //调用invoker
        return fakeRpcInvoker.invoke(invocation);
    }

}
