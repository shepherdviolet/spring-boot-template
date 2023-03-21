package com.github.shepherdviolet.webdemo.demo.mybatis.config;

import com.github.shepherdviolet.glacimon.spring.x.monitor.txtimer.plugin.MybatisTxTimerPlugin;
import org.apache.ibatis.plugin.Interceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * <p>Mybatis配置示例</p>
 *
 * <p></p>
 * <p>笔记:</p>
 *
 * <p>
 *      1.单数据源(DataSource)通过yaml配置 (见application.yaml) <br>
 *      2.SqlSessionFactory在MybatisAutoConfiguration里自动配置 (依赖org.mybatis.spring.boot:mybatis-spring-boot-starter) <br>
 *      3.SqlSessionFactory的mapper-locations和config-location通过yaml配置 (见application.yaml) <br>
 *      4.SqlSessionFactory的插件可以通过Configuration(以这里的mybatisTxTimerInterceptor方法为例)或mybatis-config.xml添加 <br>
 *      5.事务管理器TransactionManagement由EnableTransactionManagement自动配置, 当然也可以自己创建事务管理器 <br>
 *      6.标记一个方法启用事务: @Transactional(transactionManager = "这里可以指定用的事务管理器,不设置就是默认的PlatformTransactionManager") <br>
 * </p>
 *
 * <p></p>
 * <p>多数据源:</p>
 *
 * <p>
 *      1.Druid配置多数据源(推荐): 见 DruidMultiDataSourceConfiguration <br>
 *      2.手动配置多数据源: 见 ManualMultiDataSourceConfiguration <br>
 * </p>
 *
 */
@Configuration
@ComponentScan({
        "com.github.shepherdviolet.webdemo.demo.mybatis.controller",
        "com.github.shepherdviolet.webdemo.demo.mybatis.service",
})
//启用@Transactional注解(Springboot2以上默认开启CGLIB, 即使这里是false)
@EnableTransactionManagement(proxyTargetClass = true)
//扫描Mapper类
@MapperScan({
        "com.github.shepherdviolet.webdemo.demo.mybatis.dao"
})
public class MyBatisConfiguration {

    /**
     * [SqlSession插件]使用TxTimer统计数据库耗时
     */
    @Bean
    public Interceptor mybatisTxTimerInterceptor(){
        return new MybatisTxTimerPlugin();
    }

//    /**
//     * 自定义事务管理器, 可以通过@Transactional(transactionManager = "myTransactionManager")使用, 默认数据源名称为dataSource
//     */
//    @Bean
//    public PlatformTransactionManager myTransactionManager(@Qualifier("dataSource") DataSource dataSource){
//        return new DataSourceTransactionManager(dataSource);
//    }

//    /**
//     * 传统的SQL执行器(直接调用形式, 非Mapper), 默认数据源名称为dataSource
//     */
//    @Bean
//    public JdbcOperations jdbcOperations(@Qualifier("dataSource") DataSource dataSource){
//        return new JdbcTemplate(dataSource);
//    }
//
//    /**
//     * 传统的带事务的SQL执行器(直接调用形式, 非Mapper), 默认事务管理器名称为transactionManager
//     */
//    @Bean
//    public TransactionOperations transactionOperations(@Qualifier("transactionManager") PlatformTransactionManager transactionManager) {
//        return new TransactionTemplate(transactionManager);
//    }

}
