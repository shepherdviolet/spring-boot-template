package template.demo.annoproxy.customservice;

import template.demo.annoproxy.custom.CustomAnnotation;

/**
 * 接口类实例化(有实现)示例
 * 申明了自定义的@CustomAnnotation注解完成实例化, 并将方法调用转发到PortalService
 */
@CustomAnnotation(id = "customService")
public interface CustomService {

    String method(String input);

}
