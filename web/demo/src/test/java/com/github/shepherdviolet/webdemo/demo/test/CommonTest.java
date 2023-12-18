package com.github.shepherdviolet.webdemo.demo.test;

import com.github.shepherdviolet.webdemo.TestApplication;
import com.github.shepherdviolet.webdemo.demo.test.service.InnerService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Spring boot test 通用示例
 */
@SpringBootTest( // 多个SpringBootTest类, 如果classes和webEnvironment等参数一致, 则实际上下文是同一个; 如果不一致, 则会启动多个上下文. (不过, 测试类里的配置和MockBean是相互独立的)
        classes = TestApplication.class, // 指定上下文配置类, 等同于@ContextConfiguration(classes = TestApplication.class)
        webEnvironment = SpringBootTest.WebEnvironment.MOCK // 加载WebApplicationContext并提供一个Mock的Servlet环境, Servlet容器实际没有启动
//        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT // 加载WebApplicationContext并提供一个真实的Servlet环境, 监听随机端口
//        webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT // 加载WebApplicationContext并提供一个真实的Servlet环境, 监听指定端口(默认8080)
//        webEnvironment = SpringBootTest.WebEnvironment.NONE // 加载ApplicationContext不提供Servlet环境)
)
@ExtendWith(
        SpringExtension.class // 测试运行器
)
//@TestExecutionListeners(listeners = {
//        SpringBootDependencyInjectionTestExecutionListener.class,  // 处理注入
//        ServletTestExecutionListener.class // Servlet测试
//})
public class CommonTest {

    @Autowired
    private InnerService innerServiceRaw;

    @Test
    public void testCommon(){
        assertEquals("raw data ccc",
                innerServiceRaw.getData("ccc"));
    }

}
