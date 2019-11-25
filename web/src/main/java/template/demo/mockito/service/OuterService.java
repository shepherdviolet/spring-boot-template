package template.demo.mockito.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 被测试的服务, 需要注入模拟的服务
 *
 * @author S.Violet
 */
@Service
public class OuterService {

    @Autowired
    private InnerService innerService;

    public String doSomething(String id){
        return innerService.getData(id) + " wrapped";
    }

}
