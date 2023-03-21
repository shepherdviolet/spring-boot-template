// <replace-by> package ${java_package}.basic.error;
package __java_package__.basic.error;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.web.servlet.error.AbstractErrorController;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.ServletWebRequest;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * 异常统一处理
 */
@Controller
@RequestMapping("${server.error.path:${error.path:/error}}")
public class ErrorController extends AbstractErrorController {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Value("${server.error.path:${error.path:/error}}")
    private String errorPath;

    @Autowired
    private ErrorHandler errorHandler;

    private ErrorAttributes errorAttributes;

    public ErrorController(ErrorAttributes errorAttributes) {
        super(errorAttributes);
        this.errorAttributes = errorAttributes;
    }

    @Override
    public String getErrorPath() {
        return errorPath;
    }

    /* ****************************************************************************************************
     * 异常处理
     */

    /**
     * 未捕获的异常处理, 这里返回值也可以是byte[], 用byte[]返回泛用性好一点, 但是ResponseHeader需要自己设置(Content-Type等)
     */
    @RequestMapping
    @ResponseBody
    public Map<String, Object> error(HttpServletRequest request, HttpServletResponse response) {

        //获取异常
        Throwable throwable = errorAttributes.getError(new ServletWebRequest(request));
        while (throwable instanceof ServletException && throwable.getCause() != null && throwable.getCause() != throwable) {
            throwable = throwable.getCause();
        }

        //响应码覆盖为200
        response.setStatus(HttpStatus.OK.value());

        try {
            //处理错误
            if (throwable != null) {
                return errorHandler.handleThrowable(request, response, throwable);
            } else {
                return errorHandler.handleError(request, response);
            }
        } catch (Exception e) {
            //如果错误报文打包失败, 返回HTTP500
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            logger.error("Error while building error response, return HTTP status 500", e);
            return null;
        }
    }

}
