package template.demo.common.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import template.demo.aspectj.service.Service1;
import template.demo.aspectj.service.Service2;
import template.demo.aspectj.service.Service3;
import template.demo.aspectj.service.TestAJService;
import template.demo.common.service.SampleService;

/**
 * SpringBoot通用示例
 */
@RestController
@RequestMapping("/common")
public class CommonController {

    private static final Logger logger = LoggerFactory.getLogger(CommonController.class);

    @Autowired
    private SampleService sampleService;

    @RequestMapping("")
    public String test() {
        return sampleService.handle("Raw Data");
    }

}
