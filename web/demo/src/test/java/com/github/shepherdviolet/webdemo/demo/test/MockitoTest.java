package com.github.shepherdviolet.webdemo.demo.test;

import com.github.shepherdviolet.webdemo.TestApplication;
import com.github.shepherdviolet.webdemo.demo.test.service.InnerService;
import com.github.shepherdviolet.webdemo.demo.test.service.OuterService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

/**
 * <p>Mockito使用示例</p>
 *
 * <p>[注意] Mockito的两种方式:</p>
 *
 * <p>1.Mockito原始方式: @SpringBootTest + MockitoAnnotations.initMocks + @Mock</p>
 * <p>这种方式不会侵入Spring上下文, 只是通过initMocks方法对当前Test类中标有@Mock/@InjectMocks注解的对象进行代理, 替换实现.
 * 适合测试简单的情况(测试某个Bean, 包括它依赖的Bean). </p>
 *
 * <p>2.Spring集成方式: @WebMvcTest + @MockBean 或者 @SpringBootTest + @AutoConfigureMockMvc + @MockBean</p>
 * <p>这种方式侵入Spring上下文, 会对标有@MockBean注解的对象进行代理, 并替换Spring上下文中的对象.
 * 适合测试复杂的情况(用到上下文中很多Bean). </p>
 */
@SpringBootTest(classes = TestApplication.class) // 多个SpringBootTest类, 如果classes和webEnvironment等参数一致, 则实际上下文是同一个; 如果不一致, 则会启动多个上下文. (不过, 测试类里的配置和MockBean是相互独立的)
@RunWith(SpringRunner.class)
public class MockitoTest {

    /**
     * 启用Mock注解 (对当前Test类中标有@Mock/@InjectMocks注解的对象进行代理, 不会侵入Spring上下文)
     */
    @Before
    public void before(){
        MockitoAnnotations.initMocks(this);
    }

    /**
     * 该服务会被Mockito代理, 模拟返回结果; 不会替换上下文中的Bean
     */
    @Mock
    private InnerService innerServiceMocked;

    /**
     * 注入模拟的OuterService, 其内部的InnerService是被模拟的innerServiceMocked; 不会替换上下文中的Bean
     */
    @InjectMocks
    private OuterService outerService;

    /**
     * 注入上下文中普通的InnerService
     */
    @Autowired
    private InnerService innerServiceRaw;

    @Test
    public void testMockito(){
        /*
         * 模拟innerServiceMocked的返回结果
         */
        when(innerServiceMocked.getData(anyString()))
                .thenReturn("mocked data");

        /*
         * 测试MOCK后的结果
         */
        assertEquals("mocked data wrapped",
                outerService.doSomething(anyString()));

        /*
         * 测试普通返回结果
         */
        assertEquals("raw data 1",
                innerServiceRaw.getData("1"));
    }

}
