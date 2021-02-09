// <replace-by> package ${java_package}.basic.controller;
package __java_package__.basic.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import sviolet.thistle.x.util.trace.Trace;

import java.util.HashMap;
import java.util.Map;

// <replace-by> // Remove me
import __java_package__.Stub;

/**
 * RestController
 *
 * @author foo
 */
@RestController
public class BasicRestController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * Rest Sample
     * http://localhost:8080?param=hello
     */
    @RequestMapping({"", "/"})
    public Map<String, Object> get(@RequestParam(required = false) String param) {
        Map<String, Object> responseMap = new HashMap<>(16);
        responseMap.put("code", "ok");
        responseMap.put("description", "ok");
        responseMap.put("traceId", Trace.getTraceId());
        responseMap.put("param", param);
        // <replace-by>         // Remove me
        responseMap.put("stub", Stub.stub());
        return responseMap;
    }

}
