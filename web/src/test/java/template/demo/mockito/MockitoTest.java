package template.demo.mockito;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.SpringBootDependencyInjectionTestExecutionListener;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.ServletTestExecutionListener;
import template.TestApplication;
import template.demo.mockito.service.InnerService;
import template.demo.mockito.service.OuterService;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

/**
 * Mockito示例
 *
 * @author S.Violet
 */
@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
@TestExecutionListeners(listeners = {SpringBootDependencyInjectionTestExecutionListener.class, ServletTestExecutionListener.class})
@ContextConfiguration(classes = TestApplication.class)
public class MockitoTest {

    /**
     * 启用注解
     */
    @Before
    public void before(){
        MockitoAnnotations.initMocks(this);
    }

    /**
     * 该服务会被Mockito模拟返回结果
     */
    @Mock
    private InnerService innerServiceMocked;

    /**
     * 该服务内部引用innerServiceMocked
     */
    @InjectMocks
    private OuterService outerService;

    /**
     * Spring上下文中普通的InnerService
     */
    @Autowired
    private InnerService innerServiceRaw;

    @Test
    public void test1(){
        /*
         * 模拟返回结果
         */
        when(innerServiceMocked.getData(anyString()))
                .thenReturn("mocked data");

        /*
         * 返回模拟后的结果
         */
        assertEquals("mocked data wrapped",
                outerService.doSomething(anyString()));

        /*
         * 返回普通结果
         */
        assertEquals("raw data",
                innerServiceRaw.getData(anyString()));
    }

}
