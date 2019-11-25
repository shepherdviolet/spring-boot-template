package template.demo.apollo.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import sviolet.slate.common.helper.apollo.ApolloRefreshableProperties;

/**
 * Apollo配置中心的示例
 * @author S.Violet
 */
@RestController
@RequestMapping("/apollo")
public class ApolloController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    @Qualifier("demoRefreshableProperties")
    private ApolloRefreshableProperties demoRefreshableProperties;

    /**
     * http://localhost:8080/apollo/refreshable?key=key1
     */
    @RequestMapping("/refreshable")
    public String service1(@RequestParam String key){
        logger.info("refreshable " + key);
        return demoRefreshableProperties.get(key);
    }

}
