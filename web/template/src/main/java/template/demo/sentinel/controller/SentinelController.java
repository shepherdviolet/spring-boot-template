package template.demo.sentinel.controller;

import com.alibaba.csp.sentinel.Entry;
import com.alibaba.csp.sentinel.SphU;
import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 熔断限流Sentinel示例
 *
 * @author S.Violet
 */
@RestController
@RequestMapping("/sentinel")
public class SentinelController {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * 注解声明资源
     * GET
     * http://localhost:8080/sentinel/annotation?arg=1
     */
    @SentinelResource(value = "test-annotation", blockHandler = "onBlock")
    @RequestMapping(value = "/annotation")
    public String testAnnotation(@RequestParam(required = false) String arg) {
        logger.info("testAnnotation | Request pass, arg:" + arg);
        return "pass";
    }

    public String onBlock(String arg, BlockException ex) {
        logger.info("testAnnotation | Request blocked, arg:" + arg);
        return "blocked";
    }

    /**
     * API声明资源
     * GET
     * http://localhost:8080/sentinel/api?arg=1
     */
    @RequestMapping(value = "/api")
    public String testApi(@RequestParam(required = false) String arg) {
        try (Entry entry = SphU.entry("test-api")) {
            logger.info("testApi | Request pass, arg:" + arg);
            return "pass";
        } catch (BlockException ex) {
            logger.info("testApi | Request blocked, arg:" + arg);
            return "blocked";
        }
    }

}
