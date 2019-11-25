package template.demo.xmlconfig.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import template.demo.xmlconfig.entity.XmlBeanWrapper;

/**
 * XML方式配置示例
 *
 * @author S.Violet
 */
@RestController
@RequestMapping("/xmlconfig")
public class XmlConfigController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private XmlBeanWrapper xmlBeanWrapper;

    @RequestMapping("")
    public String test() {
        return String.valueOf(xmlBeanWrapper);
    }

}
