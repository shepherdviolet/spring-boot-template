package com.github.shepherdviolet.webdemo.demo.annoproxy.fakerpc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.ClassFilter;
import org.springframework.aop.support.StaticMethodMatcherPointcut;

import java.lang.reflect.Method;

/**
 * 切点
 *
 * @author S.Violet
 */
class FakeRpcPointcut extends StaticMethodMatcherPointcut {

    private static final Logger logger = LoggerFactory.getLogger(FakeRpcPointcut.class);

    private String[] basePackages;

    FakeRpcPointcut(String[] basePackages) {
        this.basePackages = basePackages;
        //设置类过滤器
        setClassFilter(new ClassFilterImpl());
    }

    /**
     * 实现方法匹配逻辑, 若该方法需要做切面, 则返回true
     */
    @Override
    public boolean matches(Method method, Class targetClass) {
        boolean matches = matchesImpl(method, targetClass);
        if (matches) {
            if (logger.isDebugEnabled()) {
                logger.debug("FakeRpcAdvisor: pointcut found: method={}, declaringClass={}, targetClass={}",
                        method.getName(),
                        method.getDeclaringClass().getName(),
                        targetClass == null ? null : targetClass.getName());
            }
        } else {
            if (logger.isTraceEnabled()) {
                logger.trace("FakeRpcAdvisor: pointcut not found: method={}, declaringClass={}, targetClass={}",
                        method.getName(),
                        method.getDeclaringClass().getName(),
                        targetClass == null ? null : targetClass.getName());
            }
        }
        return matches;
    }

    /**
     * 实现方法匹配逻辑, 若该方法需要做切面, 则返回true
     */
    private boolean matchesImpl(Method method, Class targetClass) {
        if (!matchesThis(method.getDeclaringClass())) {
            //判断类是否符合basePackages的配置
            return false;
        }
        //key
        String key = InvokerMap.getKey(method, targetClass);
        //缓存中获取invoker
        FakeRpcInvoker fakeRpcInvoker = InvokerMap.get(key);
        if (fakeRpcInvoker != null) {
            //允许切面
            return true;
        } else {
            //判断方法是否有FakeRpc注释, 判断入参出参
            if (method.isAnnotationPresent(FakeRpc.class) &&
                    String.class.isAssignableFrom(method.getReturnType()) &&
                    method.getParameterCount() == 1 &&
                    String.class.isAssignableFrom(method.getParameterTypes()[0])) {
                InvokerMap.put(key, new FakeRpcInvoker(method.getDeclaredAnnotation(FakeRpc.class)));
                //允许切面
                return true;
            } else {
                //不允许切面
                return false;
            }
        }
    }

    /**
     * 判断类名是否符合basePackages的配置
     */
    private boolean matchesThis(Class clazz) {
        String name = clazz.getName();
        if (exclude(name)) {
            return false;
        }
        return include(name);
    }

    private boolean include(String name) {
        if (basePackages != null) {
            for (String p : basePackages) {
                if (name.startsWith(p)) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean exclude(String name) {
        if (name.startsWith("java")) {
            return true;
        }
        if (name.startsWith("org.springframework")) {
            return true;
        }
        if (name.indexOf("$$EnhancerBySpringCGLIB$$") >= 0) {
            return true;
        }
        return false;
    }

    /**
     * 类过滤器, 实现类名符合basePackages的配置
     */
    private class ClassFilterImpl implements ClassFilter {

        @Override
        public boolean matches(Class clazz) {
            boolean matches = matchesImpl(clazz);
            if (matches) {
                if (logger.isDebugEnabled()) {
                    logger.debug("FakeRpcAdvisor: class found: class={}",
                            clazz == null ? null : clazz.getName());
                }
            }
            return matches;
        }

        private boolean matchesImpl(Class clazz) {
            if (matchesThis(clazz)) {
                return true;
            }
            Class[] interfaces = clazz.getInterfaces();
            if (interfaces != null) {
                for (Class c : interfaces) {
                    if (matchesImpl(c)) {
                        return true;
                    }
                }
            }
            if (!clazz.isInterface()) {
                Class superClass = clazz.getSuperclass();
                if (superClass != null && matchesImpl(superClass)) {
                    return true;
                }
            }
            return false;
        }

    }

}
