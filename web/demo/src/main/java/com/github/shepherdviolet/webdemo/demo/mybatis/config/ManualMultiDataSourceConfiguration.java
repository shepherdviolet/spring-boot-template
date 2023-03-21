package com.github.shepherdviolet.webdemo.demo.mybatis.config;

import org.springframework.context.annotation.Configuration;

/**
 * <p>手动配置多数据源</p>
 *
 * <p></p>
 * <p>配置步骤:</p>
 *
 * <p>
 *     1.在web/demo/build.gradle中修改依赖, 改为需要的数据源依赖 <br>
 *     2.在application.yaml中, 注释掉所有数据源的配置 (手动配, 不用yaml) <br>
 *     3.参考本配置类的代码进行手动数据源设置
 * </p>
 *
 * <p></p>
 * <p>访问数据库时, 如何选择数据源:</p>
 *
 * <p>
 *     1.方式1: 在调用Mapper类前, 调用DynamicDataSource.selectDataSource(datasourceName)方法, 选择数据源 <br>
 *     2.方式2: 将数据源和事务管理器绑定, 业务代码中指定不同的事务管理器, 参考MyBatisConfiguration <br>
 * </p>
 *
 */
@Configuration
public class ManualMultiDataSourceConfiguration {

//    /**
//     * 这个必须配置
//     * 自定义为动态数据源, 并设置数据源为首选类型(Primary, 防止MybatisAutoConfiguration不启用报sqlSessionFactory不存在的错)
//     */
//    @Bean
//    @Primary
//    public DataSource dataSource(){
//        return new DynamicDataSource("dataSource1");
//    }

//    @Bean("dataSource1")
//    public DataSource dataSource1(){
//        //H2数据源示例
//        //控制台(需在yml里启用): http://localhost:8000/h2-console
//        //驱动类: org.h2.Driver
//        //连接串: jdbc:h2:mem:dataSource1;DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=false
//        //用户: 'sa'
//        //密码: ''
//        return new EmbeddedDatabaseBuilder()
//                .setType(EmbeddedDatabaseType.H2)
//                .setName("dataSource1") // jdbc:h2:mem:dataSource1;DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=false
//                .addScripts(
//                        "classpath:config/demo/mybatis/h2init/schema.sql",
//                        "classpath:config/demo/mybatis/h2init/data.sql"
//                )
//                .build();
//    }

//    @Bean("dataSource2")
//    public DataSource dataSource2(){
//        //TOMCAT连接池 + ORACLE数据源 示例
//        PoolProperties configuration = new PoolProperties();
//        configuration.setDriverClassName("oracle.jdbc.driver.OracleDriver");
//        configuration.setUrl("jdbc:oracle:thin:@127.0.0.1:1521:ORCL");
//        configuration.setUsername("sviolet");
//        configuration.setPassword("sviolet");
//        configuration.setInitialSize(5);
//        configuration.setMinIdle(5);
//        configuration.setMaxIdle(50);
//        configuration.setMaxActive(50);
//        configuration.setMaxWait(10000);
//        configuration.setTimeBetweenEvictionRunsMillis(30000);
//        configuration.setMinEvictableIdleTimeMillis(1800000);
//        configuration.setValidationQuery("select 1 from dual");
//        configuration.setTestWhileIdle(true);
//        configuration.setTestOnBorrow(true);
//
//        org.apache.tomcat.jdbc.pool.DataSource dataSource = new org.apache.tomcat.jdbc.pool.DataSource();
//        dataSource.setPoolProperties(configuration);
//        return dataSource;
//    }

}
