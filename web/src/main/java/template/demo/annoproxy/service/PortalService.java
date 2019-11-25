package template.demo.annoproxy.service;

import org.springframework.stereotype.Service;

/**
 * 接口类实例化(有实现)示例
 * 入口服务, 将所有接口实例的调用转发到这里
 */
@Service
public class PortalService {

    /**
     * 处理所有接口实例的方法调用
     */
    public String handle(String input){
        return "Handle by PortalService:" + input;
    }

}
