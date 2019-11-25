package template.demo.common.service;

import org.springframework.stereotype.Service;

@Service("sampleService")
public class SampleService extends PrintService {

    /**
     * 实现处理前逻辑
     */
    @Override
    public String beforeHandle(String input) {
        return input + " preHandled";
    }

}
