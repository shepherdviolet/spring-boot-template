package com.github.shepherdviolet.webdemo.demo.mybatis.config;

import org.springframework.context.annotation.Configuration;

/**
 * <p>Druid配置多数据源(推荐)</p>
 *
 * <p></p>
 * <p>配置步骤:</p>
 *
 * <p>
 *     1.在web/demo/build.gradle中修改依赖, 启用第7套数据源依赖 "[Datasource 7]" <br>
 *     2.在application.yaml中, 启用第7套数据源配置 "[Datasource 7]" <br>
 *     3.解除本配置类中代码的注释
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
public class DruidMultiDataSourceConfiguration {

//    /**
//     * 自定义为动态数据源, 并设置数据源为首选类型(Primary, 防止MybatisAutoConfiguration不启用报sqlSessionFactory不存在的错)
//     */
//    @Bean
//    @Primary
//    public DataSource dataSource(){
//        // 可以选择默认数据源
//        return new DynamicDataSource("dataSource1");
//    }
//
//    /**
//     * 数据源 1
//     */
//    @Bean("dataSource1") // 对应数据源选择: DynamicDataSource.selectDataSource("dataSource1");
//    @ConfigurationProperties("spring.datasource.druid.datasource1") // 对应yaml中的配置名称
//    public DataSource dataSource1(){
//        return DruidDataSourceBuilder.create().build();
//    }
//
//    /**
//     * 数据源 2
//     */
//    @Bean("dataSource2") // 对应数据源选择: DynamicDataSource.selectDataSource("dataSource2");
//    @ConfigurationProperties("spring.datasource.druid.datasource2") // 对应yaml中的配置名称
//    public DataSource dataSource2(){
//        return DruidDataSourceBuilder.create().build();
//    }

}
