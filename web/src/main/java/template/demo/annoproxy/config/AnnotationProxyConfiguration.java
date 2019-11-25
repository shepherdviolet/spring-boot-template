package template.demo.annoproxy.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import sviolet.slate.common.x.proxy.interfaceinst.EnableInterfaceInstantiation;
import template.demo.annoproxy.fakerpc.EnableFakeRpc;

/**
 * 方法注解切面(非AspectJ)示例 + 接口类实例化(空实现)示例
 */
@Configuration
@ComponentScan({
        "template.demo.annoproxy.controller",
        "template.demo.annoproxy.service",
        "template.demo.annoproxy.rpcservice"
})
//启用FakeRpc并使用CGLIB代理
@EnableFakeRpc(basePackages = "template.demo.annoproxy.rpcservice", proxyTargetClass = true)
//实例化template.demo.annoproxy.rpcservice路径下的接口
@EnableInterfaceInstantiation(basePackages = {"template.demo.annoproxy.rpcservice"})
public class AnnotationProxyConfiguration {

}
