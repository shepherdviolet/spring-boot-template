package template.demo.mockito.service;

import org.springframework.stereotype.Service;

/**
 * 被调用的服务, Mock时实际上不会调用该实例的方法
 *
 * @author S.Violet
 */
@Service
public class InnerServiceImpl implements InnerService {

    @Override
    public String getData(String id) {
        return "raw data";
    }

}
