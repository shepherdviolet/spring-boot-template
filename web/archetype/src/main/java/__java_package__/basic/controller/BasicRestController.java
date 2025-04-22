// <replace-by> package ${java_package}.basic.controller;
package __java_package__.basic.controller;

// <replace-by> import ${java_package}.basic.error.HttpStatusException;
import __java_package__.basic.error.HttpStatusException;
// <replace-by> import ${java_package}.basic.error.RejectException;
import __java_package__.basic.error.RejectException;
import com.github.shepherdviolet.glacimon.java.x.trace.Trace;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

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
     * GET请求示例
     * http://localhost:8080?param=hello
     */
    @RequestMapping("/get")
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

    /**
     * POST请求示例
     * http://localhost:8080/post
     */
    @RequestMapping("/post")
    public Map<String, Object> post(@RequestBody String body) {
        Map<String, Object> responseMap = new HashMap<>(2);
        responseMap.put("result", "ok");
        responseMap.put("body", body);
        return responseMap;
    }

    /**
     * 抛出异常示例
     * 抛出RejectException异常后, 异常处理器会将错误码映射为你需要的错误码和错误信息, 然后组装成报文返回前端, 详见ErrorHandlerImpl.
     * 可在 msg/error-code.properties msg/error-desc.properties 中配置映射关系.
     *
     * http://localhost:8080/reject
     */
    @RequestMapping("/reject")
    public Map<String, Object> reject() {
        throw RejectException.create("missing_request_field")
                // hint信息不会返回给前端, 仅在系统日志中打印, 用于排查错误
                .hint("Missing request {}, The error information here is not returned to the front end, only displayed in the system log")
                // 参数, 可以替换错误信息(msg/error-desc.properties)中的{0} {1}, 也可以替换hint中的{}
                .args("FieldName")
                // 设置引发此异常的异常
//                .cause(exception)
                .build();
    }

    /**
     * 直接返回HTTP状态码
     *
     * http://localhost:8080/returnStatus
     */
    @RequestMapping("/returnStatus")
    public Map<String, Object> returnStatus() {
        // 直接返回500
        throw new HttpStatusException(500);
    }

}
