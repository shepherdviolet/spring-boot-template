package com.github.shepherdviolet.webdemo.demo.test;

import com.github.shepherdviolet.webdemo.TestApplication;
import com.github.shepherdviolet.webdemo.demo.test.service.InnerService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * <p>Spring boot test 中注入MockMvc实现对Controller层的测试.
 * 注意: SpringBootTest和WebMvcTest注解是不能一起用的哦.</p>
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
@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc // 在SpringBootTest中使用MockMvc
public class CommonWithMvcTest {

    @Autowired
    private MockMvc mockMvc;

    /**
     * 对上下文中的InnerService做代理; 会替换上下文中的Bean, 因此下面用@Autowired注入的innerServiceRaw也是代理对象
     * (如果不用when/thenReturn的话, 方法返回都是null)
     */
    @SuppressWarnings("removal")
    @MockBean
    private InnerService innerServiceMock;

    @Test
    public void testMvcInCommon1() throws Exception {

        // Mock
        when(innerServiceMock.getData(anyString())).thenReturn("MvcMock in spring boot test");

        // 调用/test/service的Controller, 判断返回结果
        mockMvc.perform(get("/test/service").accept(MediaType.APPLICATION_JSON).param("id", "any"))
                .andExpect(status().isOk())
                .andExpect(content().string("MvcMock in spring boot test"));

    }

    /**
     * 由于是Spring集成方式, 所以这里用@Autowired注入的innerServiceRaw也是代理对象 (因为上面用@MockBean标记了innerServiceMock)
     */
    @Autowired
    private InnerService innerServiceRaw;

    @Test
    public void testMvcInCommon2(){

        // Mock (每个@Test都是独立的, 所以上面testMvcInCommon1里的when/thenReturn对这个方法的没用!
        when(innerServiceRaw.getData(anyString())).thenReturn("MvcMock in spring boot test 2");

        // 注意! 这里
        assertEquals("MvcMock in spring boot test 2",
                innerServiceRaw.getData("ccc"));
    }

}
