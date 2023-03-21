# MyBatis示例

* 修改build.gradle
* 选择依赖的JDBC驱动

```text
dependencies {

    ......

    //DB
    compile 'com.h2database:h2:1.4.197'//H2, for test
//    compile 'mysql:mysql-connector-java:5.1.46'//mysql
//    compile 'com.oracle:ojdbc6:11.2.0.3'//oracle, 在jcenter有
}

```

* 修改BootApplication
* 注释掉exclude
* 将"com.github.shepherdviolet.webdemo.demo.mybatis.config"加入扫包路径

```text
@SpringBootApplication(
//        exclude = {
//                DataSourceAutoConfiguration.class//排除数据库配置(可选)
//        }
)
@ComponentScan(
        {
                ......
                "com.github.shepherdviolet.webdemo.demo.mybatis.config",
        }
)
```

* 修改application.yaml
* 配置mybatis

```text
mybatis:
  mapper-locations: classpath:config/demo/mybatis/mapper/*.xml
  config-location: classpath:config/demo/mybatis/mybatis-config.xml
```

* 配置数据源spring.datasource

```text
  ## datasource config
  datasource:
    url: jdbc:h2:mem:h2db1
    username: sa
    password: sa
    driver-class-name: org.h2.Driver
    schema: classpath:config/demo/mybatis/h2init/schema.sql
    data: classpath:config/demo/mybatis/h2init/data.sql
    initialization-mode: always
#  datasource:
#    url: jdbc:mysql://127.0.0.1:3306/test
#    username: test
#    password: test
#    driver-class-name: com.mysql.jdbc.Driver
#  datasource:
#    url: jdbc:oracle:thin:@127.0.0.1:1521:ORCL
#    username: sviolet
#    password: sviolet
#    driver-class-name: oracle.jdbc.OracleDriver
```

# H2说明

* 开启WEB控制台(可选)
* 修改application.yaml

```text
  h2:
    console:
      enabled: true
      path: /h2-console
      settings:
        web-allow-others: true
```

* 登录WEB控制台: http://localhost:8000/h2-console
* `JDBC URL`设置为`jdbc:h2:~/test`可查看系统表
* `JDBC URL`设置为`jdbc:h2:mem:h2db1`可查看用户表
* 用户名`sa`密码`sa`
