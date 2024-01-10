# jasypt私钥文件 (非对称模式专用)

```
jasypt-spring-boot-starter 要求 springboot 2.0+ (配套3.0.3), 推荐 springboot 2.1+ (配套3.0.5+)
```

* 生产环境的私钥文件千万不要放在程序里!!! (这里只是为了示例方便)
* 生产环境的私钥文件千万不要放在程序里!!! (这里只是为了示例方便)
* 生产环境的私钥文件千万不要放在程序里!!! (这里只是为了示例方便)

## 启动参数设置私钥 (DER格式)

* 在生产环境启动脚本中增加启动参数:

```
-Djasypt.encryptor.privateKeyString=MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAIvU736phSWBmZZkpIajqbE/PgTsk/Hi5i5B0+nAPIaoaTyhHhlohKmfboYHCUObPSIJSPj7FBrQrlKHj8h73vDYLg2IKQk046qt+mAhtEucCU0fkrzc0G5bpQPSEQCmpCROuftmkeumbJKwZNvNDlwnLIz4wyRBo+2cWw6OlLZjAgMBAAECgYAw2o9mMHHtXa88ZSM6SxnxbEgNzl4OB5kmFiekpl4/Kb3CygPLGsImxSYHO5QYA7fDGX1eR9KJX9lXyjcI84Y7E4w0XcGQdSuCILMFcjUlhNtJJEnnzledEKrbysYajVwy7jY0EM6RWs0PVpOygLeqP+Vvwnns2Q3ypQ8IhHb5EQJBAPwX6ssy3VYn5bOTiAqRNmPd6CyVcNebU6mPpwdJKb3SuaVcqhlHLsonqwfWQlh8gHGSsISPa6le7TeC2w5ifmsCQQCN/6nxxJVcb67LL08U+PrsPrEgcAqZtqA4pEQ7jz15ZVqUVR6CPzV13L7rtOzhL70OrfqNhKoPdLwGaufCKrXpAkEAuSa/1fpHwi2PcbMbqdc5gWPMUGJ5/IEik1jkrl83/yk0HJXQgLxdSzCTVzAwjljy5Xd9mf7UbhNAWxMK3KfOfQJALIM4gtFdAN0Bri/mWmyyO9xrKf/1Uros/5R+zyzX2HYtLtJ//dRSrd/E+Z59oxmT6kYfhL1RkgbF6j0Y6YT6AQJAR/HHgWG29Clwx+bwIvsJLKckYYmZudTMKNrCnwUbUt8b5DVpKtXYyUc5hRVWNTI58ns82N2AYrAwXqqgIZ5fXQ==
```

## 文件存放私钥 (PEM格式)

* 在程序application.yaml中配置

```
jasypt:
  encryptor:
    privateKeyFormat: PEM
    privateKeyLocation: file:/home/yourname/jasypt_private.pem
    #privateKeyLocation: file:D:/__Temp/tomcat/jasypt_private.pem
```

* 编辑文件: /home/yourname/jasypt_private.pem

```
-----BEGIN PRIVATE KEY-----
MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAIvU736phSWBmZZk
pIajqbE/PgTsk/Hi5i5B0+nAPIaoaTyhHhlohKmfboYHCUObPSIJSPj7FBrQrlKH
j8h73vDYLg2IKQk046qt+mAhtEucCU0fkrzc0G5bpQPSEQCmpCROuftmkeumbJKw
ZNvNDlwnLIz4wyRBo+2cWw6OlLZjAgMBAAECgYAw2o9mMHHtXa88ZSM6SxnxbEgN
zl4OB5kmFiekpl4/Kb3CygPLGsImxSYHO5QYA7fDGX1eR9KJX9lXyjcI84Y7E4w0
XcGQdSuCILMFcjUlhNtJJEnnzledEKrbysYajVwy7jY0EM6RWs0PVpOygLeqP+Vv
wnns2Q3ypQ8IhHb5EQJBAPwX6ssy3VYn5bOTiAqRNmPd6CyVcNebU6mPpwdJKb3S
uaVcqhlHLsonqwfWQlh8gHGSsISPa6le7TeC2w5ifmsCQQCN/6nxxJVcb67LL08U
+PrsPrEgcAqZtqA4pEQ7jz15ZVqUVR6CPzV13L7rtOzhL70OrfqNhKoPdLwGaufC
KrXpAkEAuSa/1fpHwi2PcbMbqdc5gWPMUGJ5/IEik1jkrl83/yk0HJXQgLxdSzCT
VzAwjljy5Xd9mf7UbhNAWxMK3KfOfQJALIM4gtFdAN0Bri/mWmyyO9xrKf/1Uros
/5R+zyzX2HYtLtJ//dRSrd/E+Z59oxmT6kYfhL1RkgbF6j0Y6YT6AQJAR/HHgWG2
9Clwx+bwIvsJLKckYYmZudTMKNrCnwUbUt8b5DVpKtXYyUc5hRVWNTI58ns82N2A
YrAwXqqgIZ5fXQ==
-----END PRIVATE KEY-----
```

* `注意! PEM格式的头部必须是BEGIN PRIVATE KEY, 尾部必须是END PRIVATE KEY. 不能是BEGIN/END RSA PRIVATE KEY`