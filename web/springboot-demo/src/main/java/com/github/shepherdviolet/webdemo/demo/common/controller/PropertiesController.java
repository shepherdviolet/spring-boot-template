package com.github.shepherdviolet.webdemo.demo.common.controller;

import com.github.shepherdviolet.webdemo.demo.common.prop.BindProperties;
import com.github.shepherdviolet.webdemo.demo.common.prop.YamlProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Properties;

/**
 * 配置文件加载示例(yml/properties)
 *
 * @author S.Violet
 */
@RestController
@RequestMapping("/common/properties")
public class PropertiesController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Value("${prop.general.greet}")
    private String generalGreet;

    @Value("${prop.yaml.map.item2.comment}")
    private String yamlEncrypted;

    @Value("${prop.general.encrypted}")
    private String generalEncrypted;

    @Value("${prop.bind.args.2.value}")
    private String bindEncrypted;

    /**
     * 注意, CryptoProp在普通模式下只支持@Value和XML中的占位符属性解密, 不支持Environment#getProperty属性解密.
     * 开启加强模式(glacispring.crypto-prop.mode=ENHANCED)支持Environment#getProperty属性解密.
     */
    @Autowired
    private Environment environment;

    @Autowired
    private BindProperties bindProperties;

    @Autowired
    private YamlProperties yamlProperties;

    @Autowired
    @Qualifier("myStandaloneProperties")
    private Properties myStandaloneProperties;

    @Value("${byxml.encrypted}")
    private String byXml;

    /**
     * http://localhost:8000/common/properties/
     */
    @RequestMapping("")
    public String test() {
        logger.info(toString());
        return toString();
    }

    @Override
    public String toString() {
        return "generalGreet=\n<br>" + generalGreet +
                "\n\n<br><br>yamlEncrypted=\n<br>" + yamlEncrypted +
                "\n\n<br><br>generalEncrypted=\n<br>" + generalEncrypted +
                "\n\n<br><br>bindEncrypted=\n<br>" + bindEncrypted +
                "\n\n<br><br>from environment=\n<br>" + environment.resolvePlaceholders("${prop.general.encrypted}") +
                "\n\n<br><br>bindProperties=\n<br>" + bindProperties +
                "\n\n<br><br>yamlProperties=\n<br>" + yamlProperties +
                "\n\n<br><br>myStandaloneProperties=\n<br>" + myStandaloneProperties +
                "\n\n<br><br>byXml=\n<br>" + byXml;
    }
}
