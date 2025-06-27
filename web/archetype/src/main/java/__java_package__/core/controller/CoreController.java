// <replace-by> package ${java_package}.core.controller;
package __java_package__.core.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Controller
 *
 * @author foo
 */
@Controller
public class CoreController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * Thymeleaf Sample
     * http://localhost:8080/home
     */
    @RequestMapping("/home")
    public String home() {
        return "home";
    }

}
