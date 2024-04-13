package com.github.shepherdviolet.webdemo.demo.security.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.sql.DataSource;

/**
 * SpringSecurity示例
 */
@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true) // 允许@PreAuthorize
@ComponentScan({
        "com.github.shepherdviolet.webdemo.demo.security.controller",
})
public class SecurityConfiguration {

    /**
     * 自定义安全配置
     */
    @Configuration(proxyBeanMethods = false)
    public static class CustomWebSecurityConfigurerAdapter extends WebSecurityConfigurerAdapter {

        @Autowired
        private DataSource dataSource;

        /**
         * 用户认证配置 (这里配置后, application.yaml里默认的用户名密码设置就没用了)
         */
        @Override
        protected void configure(AuthenticationManagerBuilder auth) throws Exception {
            // 加密器, 对储存的密码做加密, 验证密码是否正确 (不加密的加密器NoOpPasswordEncoder.getInstance())
//            PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            PasswordEncoder passwordEncoder = NoOpPasswordEncoder.getInstance();

            // 在内存中储存用户名密码, 注意密码需要用passwordEncoder手动加密一下, 不然内存里的密码是明文, 但前端送上来的密码会被加密, 导致验证失败
//            auth.inMemoryAuthentication()
//                    .passwordEncoder(passwordEncoder)
//                    .withUser("admin").password(passwordEncoder.encode("admin")).roles("USER", "ADMIN")
//                    .and()
//                    .withUser("user").password(passwordEncoder.encode("user")).roles("USER");

            // 在数据库中储存用户名密码, 注意数据库中的password字段要加密(加密方式要匹配passwordEncoder), 本示例偷懒用了NoOpPasswordEncoder, 生产不能这样!
            auth.jdbcAuthentication().dataSource(dataSource)
                    .usersByUsernameQuery("select username, password, enabled from users where username=?")
                    .authoritiesByUsernameQuery("select username, authority from user_auth where username=?")
                    .passwordEncoder(passwordEncoder);
        }

        @Override
        protected void configure(HttpSecurity http) throws Exception {

            /*
             * 注意: Eureka服务端/SpringBootAdmin服务端在引入security包后, 要禁用csrf防御 (默认开启), 否则注册失败.
             * 纯Eureka/Admin服务端不用依赖security, 或者直接 http.csrf().disable()... 就行.
             * Admin客户端开放actuator端点的, 根据"/actuator/**"路径禁用csrf防御, 像 http.csrf().ignoringAntMatchers("/actuator/**").and()... 就行.
             * 本示例既作为Admin客户端又作为Admin服务端的, 根据"/admin/**"和"/actuator/**"路径禁用csrf防御.
             * P.S.对浏览起来说, CSRF开着没事, 对程序请求来说就有关系(要传token), 不想要就 http.csrf().disable()... 好了.
             *
             * 注意: Eureka服务端/SpringBootAdmin服务端在引入security包后, 要禁用相关路径的密码验证, 否则也会注册失败.
             * 要么默认不用密码验证, 指定业务相关路径需要密码.
             * 要么默认需要密码验证, 把类似于"/admin/**"和"/actuator/**"路径设置为permitAll.
             */

            /*
             * 示例1: 禁用admin相关路径的csrf防御, 只对"/security/**"路径验证密码.
             */

//            http.csrf().ignoringAntMatchers("/admin/**", "/actuator/**").and() // 禁用admin和actuator路径的csrf防御, 否则无法注册到Admin
//                    .formLogin().and() // 允许表单登录
//                    .httpBasic().and() // 允许 HTTP Basic 登录 (POSTMAN里可以用)
//                    .authorizeRequests()
//                    .antMatchers("/security/**").authenticated() // 对"/security/**"路径验证密码
//                    .anyRequest().permitAll(); // 其他路径直接放行 (其实这个不配也一样)

            /*
             * 示例2: 禁用admin相关路径的csrf防御, 对除了"/admin/**"和"/actuator/**"以外的路径验证密码.
             */

//            http.csrf().ignoringAntMatchers("/admin/**", "/actuator/**").and() // 禁用admin和actuator路径的csrf防御, 否则无法注册到Admin
//                    .formLogin().and() // 允许表单登录
//                    .httpBasic().and() // 允许 HTTP Basic 登录 (POSTMAN里可以用)
//                    .authorizeRequests()
//                    .antMatchers("/admin/**", "/actuator/**").permitAll() // 不对"/admin/**"和"/actuator/**"路径验证密码
//                    .anyRequest().authenticated(); // 其他路径都验证密码

            /*
             * 示例3: 禁用全部csrf防御, 只对security相关路径验证密码, 配套默认controller进行测试
             */

            http.csrf().disable() // 禁用全部csrf防御
                    .formLogin().and() // 允许表单登录
                    .httpBasic().and() // 允许 HTTP Basic 登录 (POSTMAN里可以用)
                    .logout().and() // 登出: http://localhost:8000/logout
                    .authorizeRequests()
                    .antMatchers("/security/admin/**").hasAnyAuthority("ADMIN")
                    .antMatchers("/security/user/**").hasAnyAuthority("USER");

        }

    }

}
