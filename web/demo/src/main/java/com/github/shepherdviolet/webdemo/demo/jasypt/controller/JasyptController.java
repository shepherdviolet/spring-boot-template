package com.github.shepherdviolet.webdemo.demo.jasypt.controller;

import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import org.jasypt.encryption.pbe.config.SimpleStringPBEConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * jasypt-spring-boot-starter示例: 参数加密
 */
@RestController
@RequestMapping("/jasypt")
public class JasyptController {

    private static final Logger logger = LoggerFactory.getLogger(JasyptController.class);

    @Value("${jasypt-test.param1}")
    private String param1;

    /**
     * http://localhost:8000/jasypt
     */
    @RequestMapping("")
    public String test() {
        return param1;
    }

    /* ****************************************************************************************************************
     * 对称加密方案
     **************************************************************************************************************** */

    /**
     * 生成密码, 加密信息
     * @param args
     */
    public static void main(String[] args) {
        /*
         * 生成和保管密码:
         * 建议用工具生成一个密码(例如https://www.sexauth.com/)
         * 注意!!! 这个密码不要直接放在程序配置文件里, 应该通过启动参数/环境变量等方式在运行环境设置.
         *
         * 例如在启动参数里:
         * -Djasypt.encryptor.password=PRGkutx3FfsCYtyvgL9OjHyHQ7SkxGL
         */
        String password = "IPRGkutx3FfsCYtyvgL9OjHyHQ7SkxGL";
        /*
         * 在这里填入需要用密码保护的参数原文
         */
        String message = "The parameter that need to be protected";
        /*
         * 用jasypt默认算法加密信息, 如果你更换了加密算法, 则要用对应的加密算法加密
         */
        String messageEncrypted = encryptor(password).encrypt(message);
        /*
         * 为了避免解密出来的数据和原文不同, 这里重新解密成原文做个对比
         */
        String messageRaw = encryptor(password).decrypt(messageEncrypted);
        if (!message.equals(messageRaw)){
            throw new RuntimeException("Encrypt failed, decrypted messsage: " + messageRaw);
        }
        /*
         * 密文加上前缀后缀, 就可以作为参数设置到Spring配置/启动参数里了
         *
         * 例如在application.properties里:
         * param-key=ENC(cABV59oATrHNGVZ33GBDHIBb4LdC7naVuExv9rdJ2dtnYSS6WhK/88KRunZavB1M)
         *
         * 例如在启动参数里:
         * -Dparam-key=ENC(cABV59oATrHNGVZ33GBDHIBb4LdC7naVuExv9rdJ2dtnYSS6WhK/88KRunZavB1M)
         */
        System.out.println("ENC(" + messageEncrypted + ")");
    }

    /**
     * jasypt-spring-boot-starter 默认的加密器.
     * 如果配置了自定义加密器或者参数, 请相应调整.
     */
    public static StandardPBEStringEncryptor encryptor(String password) {
        StandardPBEStringEncryptor encryptor = new StandardPBEStringEncryptor();
        SimpleStringPBEConfig config = new SimpleStringPBEConfig();
        config.setPassword(password);
        config.setAlgorithm("PBEWITHHMACSHA512ANDAES_256");
        config.setKeyObtentionIterations("1000");
        config.setPoolSize("1");
        config.setProviderName(null);
        config.setProviderClassName(null);
        config.setSaltGeneratorClassName("org.jasypt.salt.RandomSaltGenerator");
        config.setIvGeneratorClassName("org.jasypt.iv.RandomIvGenerator");
        config.setStringOutputType("base64");
        encryptor.setConfig(config);
        return encryptor;
    }

}
