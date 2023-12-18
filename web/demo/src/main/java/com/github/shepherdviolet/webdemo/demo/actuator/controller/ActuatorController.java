package com.github.shepherdviolet.webdemo.demo.actuator.controller;

import com.github.shepherdviolet.webdemo.demo.actuator.component.DemoHealthIndicator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Actuator端点示例
 *
 * 设置完可以去http://localhost:8000/admin看状态
 */
@RestController
@RequestMapping("/actuator")
public class ActuatorController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private DemoHealthIndicator demoHealthIndicator;

    /**
     * http://localhost:8000/actuator/up
     */
    @RequestMapping("/up")
    public String up() {
        demoHealthIndicator.setState(DemoHealthIndicator.STATE_UP);
        return "Set health to up";
    }

    /**
     * http://localhost:8000/actuator/down
     */
    @RequestMapping("/down")
    public String down() {
        demoHealthIndicator.setState(DemoHealthIndicator.STATE_DOWN);
        return "Set health to down";
    }

    /**
     * http://localhost:8000/actuator/unknown
     */
    @RequestMapping("/unknown")
    public String unknown() {
        demoHealthIndicator.setState(DemoHealthIndicator.STATE_UNKNOWN);
        return "Set health to unknown";
    }

}
