package com.github.shepherdviolet.webdemo.demo.test;

import com.github.shepherdviolet.webdemo.TestApplication;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.Environment;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Environment设置/测试 示例
 */
@SpringBootTest(
        classes = TestApplication.class,
        properties = {
                "info.test=hello",
        }
) // 多个SpringBootTest类, 如果classes和webEnvironment等参数一致, 则实际上下文是同一个; 如果不一致, 则会启动多个上下文. (不过, 测试类里的配置和MockBean是相互独立的)
@ExtendWith(SpringExtension.class)
public class EnvironmentTest {

    @Autowired
    private Environment environment;

    @Test
    public void testEnvValue() {
        assertEquals("I'm spring boot demo", environment.getProperty("info.comment"));
        assertEquals("hello", environment.getProperty("info.test"));
    }

}
