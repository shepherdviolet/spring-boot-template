package com.github.shepherdviolet.webdemo.demo.actuator.component;

import com.github.shepherdviolet.glacimon.java.collections.StreamingBuildable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.actuate.endpoint.annotation.Endpoint;
import org.springframework.boot.actuate.endpoint.annotation.ReadOperation;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * 自定义端点示例: /actuator/custom
 */
@SuppressWarnings("removal")
@Component
@Endpoint(id = "custom", enableByDefault = true)
public class CustomActuatorEndpoint implements StreamingBuildable {

    private Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * 读
     * http://localhost:8000/actuator/custom
     */
    @ReadOperation
    public Map<String, Object> read() {
        return buildHashMap()
                .put("author", "shepherd violet")
                .put("license", "APACHE 2.0")
                .build();
    }

    /*
     * 写
     */
//    @WriteOperation
//    public ResponseEntity<?> write(String msg) {
//        logger.info("Write: " + msg);
//        return ResponseEntity.ok().build();
//    }

}
