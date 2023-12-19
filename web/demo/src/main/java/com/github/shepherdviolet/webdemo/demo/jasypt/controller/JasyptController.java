package com.github.shepherdviolet.webdemo.demo.jasypt.controller;

import com.github.shepherdviolet.glacimon.java.conversion.Base64Utils;
import com.github.shepherdviolet.glacimon.java.crypto.PEMEncodeUtils;
import com.github.shepherdviolet.glacimon.java.crypto.RSAKeyGenerator;
import com.ulisesbocchio.jasyptspringboot.encryptor.SimpleAsymmetricConfig;
import com.ulisesbocchio.jasyptspringboot.encryptor.SimpleAsymmetricStringEncryptor;
import org.jasypt.encryption.StringEncryptor;
import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import org.jasypt.encryption.pbe.config.SimpleStringPBEConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.spec.InvalidKeySpecException;

/**
 * jasypt-spring-boot-starter示例: 参数加密
 */
@RestController
@RequestMapping("/jasypt")
public class JasyptController {

    private static final Logger logger = LoggerFactory.getLogger(JasyptController.class);

    @Value("${jasypt.test-param1}")
    private String param1;

    /**
     * http://localhost:8000/jasypt
     */
    @RequestMapping("")
    public String test() {
        return param1;
    }

    /* ****************************************************************************************************************
     * 非对称加密方案
     **************************************************************************************************************** */

    public static void main(String[] args) throws InvalidKeySpecException {
        /*
         * 生成RSA公私钥, 私钥配置在运行环境的启动参数/环境变量中, 公钥提供给开发人员.
         * 对密码强度要求不高的情况下可以考虑用1024位的密钥, 短一些. 要求高的用2048.
         *
         * 公钥提供给开发使用, 可以直接写在工具程序里, 供开发加密信息.
         *
         * 私钥配置在启动参数或环境变量中:
         * -Djasypt.encryptor.privateKeyString=......
         * 也可以将私钥放置在文件中:
         * jasypt.encryptor.privateKeyLocation=/home/yourname/privatekey.pem
         * 可以设置私钥格式, 默认DER, 可以设置成PEM
         * jasypt.encryptor.privateKeyFormat=DER
         * (本示例工程中, 配在了application.yaml里, 为了测试方便, 生产环境不要这么做)
         */
//        RSAKeyGenerator.RSAKeyPair keyPair = RSAKeyGenerator.generateKeyPair(1024);
//        String publicKeyDer = Base64Utils.encodeToString(keyPair.getX509EncodedPublicKey()); //DER格式公钥 (jasypt也支持PEM)
//        String privateKeyDer = Base64Utils.encodeToString(keyPair.getPKCS8EncodedPrivateKey()); //DER格式私钥 (jasypt也支持PEM)
//        System.out.println("\nPUBLIC: \n" + publicKeyDer);
//        System.out.println("\nPRIVATE: \n" + privateKeyDer);
//        System.out.println("\nPRIVATE PEM: \n" + PEMEncodeUtils.rsaPrivateKeyToPEMEncoded(privateKeyDer));

        /*
         * 用公钥加密信息
         */
        SimpleAsymmetricConfig config = new SimpleAsymmetricConfig();
        config.setPublicKey("MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCL1O9+qYUlgZmWZKSGo6mxPz4E7JPx4uYuQdPpwDyGqGk8oR4ZaISpn26GBwlDmz0iCUj4+xQa0K5Sh4/Ie97w2C4NiCkJNOOqrfpgIbRLnAlNH5K83NBuW6UD0hEApqQkTrn7ZpHrpmySsGTbzQ5cJyyM+MMkQaPtnFsOjpS2YwIDAQAB");

//        config.setKeyFormat(AsymmetricCryptography.KeyFormat.PEM);
//        config.setPublicKey("-----BEGIN RSA PUBLIC KEY-----\n" +
//                "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCL1O9+qYUlgZmWZKSGo6mxPz4E\n" +
//                "7JPx4uYuQdPpwDyGqGk8oR4ZaISpn26GBwlDmz0iCUj4+xQa0K5Sh4/Ie97w2C4N\n" +
//                "iCkJNOOqrfpgIbRLnAlNH5K83NBuW6UD0hEApqQkTrn7ZpHrpmySsGTbzQ5cJyyM\n" +
//                "+MMkQaPtnFsOjpS2YwIDAQAB\n" +
//                "-----END RSA PUBLIC KEY-----");

        StringEncryptor encryptor = new SimpleAsymmetricStringEncryptor(config);
        String message = "The parameter that need to be protected";
        String encrypted = encryptor.encrypt(message);

        /*
         * 密文加上前缀后缀, 就可以作为参数设置到Spring配置/启动参数里了
         */
        System.out.println("ENC(" + encrypted + ")");
    }

    /* ****************************************************************************************************************
     * 对称加密方案
     **************************************************************************************************************** */

    /**
     * 生成密码, 加密信息
     * @param args
     */
//    public static void main(String[] args) {
//        /*
//         * 生成和保管密码:
//         * 建议用工具生成一个密码(例如https://www.sexauth.com/)
//         * 注意!!! 这个密码不要直接放在程序配置文件里, 应该通过启动参数/环境变量等方式在运行环境设置.
//         * (本示例工程中, 配在了application.yaml里, 为了测试方便, 生产环境不要这么做)
//         *
//         * 例如在启动参数里:
//         * -Djasypt.encryptor.password=PRGkutx3FfsCYtyvgL9OjHyHQ7SkxGL
//         */
//        String password = "IPRGkutx3FfsCYtyvgL9OjHyHQ7SkxGL";
//        /*
//         * 在这里填入需要用密码保护的参数原文
//         */
//        String message = "The parameter that need to be protected";
//        /*
//         * 用jasypt默认算法加密信息, 如果你更换了加密算法, 则要用对应的加密算法加密
//         */
//        String messageEncrypted = encryptor(password).encrypt(message);
//        /*
//         * 为了避免解密出来的数据和原文不同, 这里重新解密成原文做个对比
//         */
//        String messageRaw = encryptor(password).decrypt(messageEncrypted);
//        if (!message.equals(messageRaw)){
//            throw new RuntimeException("Encrypt failed, decrypted messsage: " + messageRaw);
//        }
//        /*
//         * 密文加上前缀后缀, 就可以作为参数设置到Spring配置/启动参数里了
//         *
//         * 例如在application.properties里:
//         * param-key=ENC(cABV59oATrHNGVZ33GBDHIBb4LdC7naVuExv9rdJ2dtnYSS6WhK/88KRunZavB1M)
//         *
//         * 例如在启动参数里:
//         * -Dparam-key=ENC(cABV59oATrHNGVZ33GBDHIBb4LdC7naVuExv9rdJ2dtnYSS6WhK/88KRunZavB1M)
//         */
//        System.out.println("ENC(" + messageEncrypted + ")");
//    }
//
//    /**
//     * jasypt-spring-boot-starter 默认的加密器.
//     * 如果配置了自定义加密器或者参数, 请相应调整.
//     */
//    public static StandardPBEStringEncryptor encryptor(String password) {
//        StandardPBEStringEncryptor encryptor = new StandardPBEStringEncryptor();
//        SimpleStringPBEConfig config = new SimpleStringPBEConfig();
//        config.setPassword(password);
//        config.setAlgorithm("PBEWITHHMACSHA512ANDAES_256");
//        config.setKeyObtentionIterations("1000");
//        config.setPoolSize("1");
//        config.setProviderName(null);
//        config.setProviderClassName(null);
//        config.setSaltGeneratorClassName("org.jasypt.salt.RandomSaltGenerator");
//        config.setIvGeneratorClassName("org.jasypt.iv.RandomIvGenerator");
//        config.setStringOutputType("base64");
//        encryptor.setConfig(config);
//        return encryptor;
//    }

}
