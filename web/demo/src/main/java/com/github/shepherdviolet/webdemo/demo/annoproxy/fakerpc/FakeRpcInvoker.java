package com.github.shepherdviolet.webdemo.demo.annoproxy.fakerpc;

import org.aopalliance.intercept.MethodInvocation;

/**
 * 代理方法真正执行的逻辑
 *
 * @author S.Violet
 */
class FakeRpcInvoker {

    private FakeRpc fakeRpc;

    FakeRpcInvoker(FakeRpc fakeRpc) {
        this.fakeRpc = fakeRpc;
    }

    Object invoke(MethodInvocation invocation){
        if (invocation.getArguments() == null || invocation.getArguments().length <= 0) {
            throw new IllegalArgumentException("Method has no argument which has FakeRpc annotation");
        }
        /*
            这里模拟:将方法参数发送给FakeRpc注解指定的服务
         */
        return "Send message:" + invocation.getArguments()[0] + " To http://host:port/" + fakeRpc.name() + "/" + fakeRpc.version();
    }

}
