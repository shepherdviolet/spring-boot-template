package com.github.shepherdviolet.webdemo.demo.test;

import com.github.shepherdviolet.webdemo.TestApplication;
import com.github.shepherdviolet.webdemo.demo.test.controller.TestController;
import com.github.shepherdviolet.webdemo.demo.test.service.InnerService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * <p>Mvc测试示例.
 * 用于测试Controller层, 并不会加载其他的Bean, 并不会启动Servlet容器.</p>
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
@WebMvcTest(
        controllers = {
                TestController.class // 指定要测试的Controller
        },
        excludeAutoConfiguration = {
                SecurityAutoConfiguration.class // 排除安全自动配置, WebMvcTest不会完整加载上下文, 所以TestController里的排除没用 (未依赖spring-boot-starter-security则无需排除)
        }
)
@ContextConfiguration(classes = TestApplication.class) // 指定上下文配置类, 否则会报错重复
@ExtendWith(SpringExtension.class)
public class MvcTest {

    @Autowired
    private MockMvc mockMvc;

    /**
     * 对上下文中的InnerService做代理; 会替换上下文中的Bean
     */
    @SuppressWarnings("removal")
    @MockBean
    private InnerService innerServiceMock;

    @Test
    public void testMvc() throws Exception {

        // Mock
        when(innerServiceMock.getData("a")).thenReturn("Mock mock a");
        when(innerServiceMock.getData("b")).thenReturn("Mock mock b");

        // 调用/test/service的Controller, 判断返回结果
        mockMvc.perform(get("/test/service").accept(MediaType.APPLICATION_JSON).param("id", "a"))
                .andExpect(status().isOk())
                .andExpect(content().string("Mock mock a"));

        // 调用/test/service的Controller, 获取返回结果
        MvcResult mvcResult = mockMvc.perform(get("/test/service").accept(MediaType.APPLICATION_JSON).param("id", "a"))
                .andReturn();
        assertEquals("Mock mock a", mvcResult.getResponse().getContentAsString());

        // 调用/test/service的Controller, 在回调中处理结果
//        mockMvc.perform(get("/test/service").accept(MediaType.APPLICATION_JSON).param("id", "a"))
//                .andDo(result -> {
//                    System.out.println(result.getResponse().getContentAsString());
//                });87\


    }

}
