package template.demo.mybatis.config;

import org.apache.ibatis.plugin.Interceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import sviolet.slate.common.helper.mybatis.monitor.MybatisTxTimerPlugin;

/**
 * <p>Mybatis示例</p>
 *
 * <p>
 *     1.数据源datasource通过yaml配置 (见application.yaml) <br>
 *     2.SqlSessionFactory在MybatisAutoConfiguration里自动配置 (依赖org.mybatis.spring.boot:mybatis-spring-boot-starter) <br>
 *     3.SqlSessionFactory的mapper-locations和config-location通过yaml配置 (见application.yaml) <br>
 *     4.SqlSessionFactory的插件可以通过Configuration(以这里的mybatisTxTimerInterceptor方法为例)或mybatis-config.xml添加 <br>
 *     5.事务管理器TransactionManagement由EnableTransactionManagement自动配置, 当然也可以自己创建事务管理器 <br>
 *     6.@Transactional(transactionManager = "这里可以指定用的事务管理器,不设置就是默认的PlatformTransactionManager")
 * </p>
 */
@Configuration
@ComponentScan({
        "template.demo.mybatis.controller",
        "template.demo.mybatis.service",
})
//启用@Transactional注解
@EnableTransactionManagement(proxyTargetClass = true)
//扫描Mapper类
@MapperScan({
        "template.demo.mybatis.dao"
})
public class MyBatisConfiguration {

    /**
     * 用这个的话, YAML中的数据源全部注释掉
     * 自定义为动态数据源, 并设置数据源为首选类型(Primary, 防止MybatisAutoConfiguration不启用报sqlSessionFactory不存在的错)
     */
//    @Bean
//    @Primary
//    public DataSource dataSource(){
//        return new DynamicDataSource("dataSource1");
//    }

//    @Bean("dataSource1")
//    public DataSource dataSource1(){
//        //H2数据源
//        //DriverClass org.h2.Driver
//        //Url jdbc:h2:mem:dataSource1;DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=false
//        //Username sa
//        //Password ''
//        return new EmbeddedDatabaseBuilder()
//                .setType(EmbeddedDatabaseType.H2)
//                .setName("dataSource1")
//                .addScripts(
//                        "classpath:config/demo/mybatis/h2init/schema.sql",
//                        "classpath:config/demo/mybatis/h2init/data.sql"
//                )
//                .build();
//    }

//    @Bean("dataSource2")
//    public DataSource dataSource2(){
//        //TOMCAT数据源
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
