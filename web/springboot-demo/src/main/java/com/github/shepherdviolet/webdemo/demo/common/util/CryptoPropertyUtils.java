package com.github.shepherdviolet.webdemo.demo.common.util;

import com.github.shepherdviolet.glacimon.spring.x.crypto.cryptoprop.decryptor.SimpleCryptoPropUtils;

/**
 * CryptoProp属性加密工具类
 */
public class CryptoPropertyUtils {

    public static void main(String[] args) {

        String plain = "decrypted successfully 111";

        //非对称加密
        System.out.println(SimpleCryptoPropUtils.encryptAndWrap(plain,
                "rsa:classpath:config/demo/common/cryptoprop/cryptoprop-public-key.pem"));

        // 对称加密
        System.out.println(SimpleCryptoPropUtils.encryptAndWrap(plain,
                "aes:classpath:config/demo/common/cryptoprop/cryptoprop-key.txt"));

    }

}
