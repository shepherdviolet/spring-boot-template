package com.github.shepherdviolet.webdemo.demo.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import javax.sql.DataSource;

/**
 * SpringSecurity示例
 */
@Configuration("MySecurityConfiguration")
@EnableWebSecurity // 启用Web安全配置（Spring Boot 3.x建议显式添加）
@EnableMethodSecurity(prePostEnabled = true) // 允许@PreAuthorize
@ComponentScan({
        "com.github.shepherdviolet.webdemo.demo.security.controller",
})
public class SecurityConfiguration {

    /**
     * 配置用户认证服务（替代原AuthenticationManagerBuilder配置）
     * 基于JDBC的用户信息管理，对应原jdbcAuthentication()配置, 你也可以替换成其他实现
     */
    @Bean
    public JdbcUserDetailsManager jdbcUserDetailsManager(DataSource dataSource) {
        JdbcUserDetailsManager manager = new JdbcUserDetailsManager();
        manager.setDataSource(dataSource);
        manager.setUsersByUsernameQuery("select username, password, enabled from users where username=?");
        manager.setAuthoritiesByUsernameQuery("select username, authority from user_auth where username=?");
        return manager;
    }

    /**
     * 密码编码器（替代原AuthenticationManagerBuilder中配置的passwordEncoder, 并且无需设置给UserDetailsManager）
     * 注意：NoOpPasswordEncoder不安全，仅示例用，生产环境需替换为: return new BCryptPasswordEncoder();
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        //noinspection deprecation
        return NoOpPasswordEncoder.getInstance();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        /*
         * 注意: Eureka服务端/SpringBootAdmin服务端在引入security包后, 要禁用csrf防御 (默认开启), 否则注册失败.
         * 纯Eureka/Admin服务端不用依赖security, 或者直接 http.csrf(csrf -> csrf.disable())... 就行.
         * Admin客户端开放actuator端点的, 根据"/actuator/**"路径禁用csrf防御, 像 http.csrf(csrf -> csrf.ignoringRequestMatchers("/actuator/**"))... 就行.
         * 本示例既作为Admin客户端又作为Admin服务端的, 根据"/admin/**"和"/actuator/**"路径禁用csrf防御.
         * P.S.对浏览起来说, CSRF开着没事, 对程序请求来说就有关系(要传token), 不想要就 http.csrf(csrf -> csrf.disable())... 好了.
         *
         * 注意: Eureka服务端/SpringBootAdmin服务端在引入security包后, 要禁用相关路径的密码验证, 否则也会注册失败.
         * 要么默认不用密码验证, 指定业务相关路径需要密码.
         * 要么默认需要密码验证, 把类似于"/admin/**"和"/actuator/**"路径设置为permitAll.
         */

        /*
         * 示例1: 禁用admin相关路径的csrf防御, 只对"/security/**"路径验证密码.
         */

//        http
//                .csrf(csrf -> csrf.ignoringRequestMatchers("/admin/**", "/actuator/**")) // 禁用admin和actuator路径的csrf防御, 否则无法注册到Admin
//                .formLogin(form -> {}) // 允许表单登录（保持默认配置：登录页/login、成功后跳转等）
//                .httpBasic(basic -> {}) // 允许 HTTP Basic 登录 (POSTMAN里可以用，保持默认配置)
//                .logout(logout -> {}) // 登出: http://localhost:8000/logout（默认配置：GET /logout、成功跳转等）
//                .authorizeHttpRequests(auth -> auth
//                        .requestMatchers("/security/**").authenticated() // 对"/security/**"路径验证密码
//                        .anyRequest().permitAll() // 其他路径直接放行
//                );

        /*
         * 示例2: 禁用admin相关路径的csrf防御, 对除了"/admin/**"和"/actuator/**"以外的路径验证密码.
         */

//        http
//                .csrf(csrf -> csrf.ignoringRequestMatchers("/admin/**", "/actuator/**")) // 禁用admin和actuator路径的csrf防御, 否则无法注册到Admin
//                .formLogin(form -> {}) // 允许表单登录（保持默认配置）
//                .httpBasic(basic -> {}) // 允许 HTTP Basic 登录 (POSTMAN里可以用，保持默认配置)
//                .logout(logout -> {}) // 登出: http://localhost:8000/logout（默认配置：GET /logout、成功跳转等）
//                .authorizeHttpRequests(auth -> auth
//                        .requestMatchers("/admin/**", "/actuator/**").permitAll() // 不对"/admin/**"和"/actuator/**"路径验证密码
//                        .anyRequest().authenticated() // 其他路径都验证密码
//                );

        /*
         * 示例3: 禁用全部csrf防御, 只对security相关路径验证密码, 配套默认controller进行测试
         */

        http
                .csrf(csrf -> csrf.disable()) // 禁用全部csrf防御
                .formLogin(form -> {}) // 允许表单登录（默认配置：登录页/login、登录接口POST /login、成功跳转等）
                .httpBasic(basic -> {}) // 允许 HTTP Basic 登录 (POSTMAN里可以用，默认配置)
                .logout(logout -> {}) // 登出: http://localhost:8000/logout（默认配置：GET /logout、成功跳转等）
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/security/admin/**").hasAnyAuthority("ADMIN")
                        .requestMatchers("/security/user/**").hasAnyAuthority("USER")
                        .anyRequest().permitAll() // 其他路径直接放行
                );

        return http.build();
    }

}
