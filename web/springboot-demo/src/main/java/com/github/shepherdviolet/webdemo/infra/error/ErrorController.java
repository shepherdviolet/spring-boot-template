package com.github.shepherdviolet.webdemo.infra.error;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
 * <p>异常统一处理</p>
 *
 * <p>SpringMVC有两种异常处理方式, 一种是实现ErrorController(参考本类), 一种是用@ControllerAdvice+@ExceptionHandler.
 * 前者能够处理Servlet和Controller的异常或错误, 后者只能处理Controller抛出的异常(Servlet内部异常无法处理, 例如404错误/ViewRender错误等).
 * 两种方式同时使用时, Controller抛出的异常优先由@ExceptionHandler处理, @ExceptionHandler无法处理或者处理时的异常由ErrorController处理. </p>
 */
@Controller
@RequestMapping("${server.error.path:${error.path:/error}}")
public class ErrorController extends AbstractErrorController {

    private ErrorAttributes errorAttributes;

    public ErrorController(ErrorAttributes errorAttributes) {
        super(errorAttributes);
        this.errorAttributes = errorAttributes;
    }

    // springboot 2.3.x 删除了该方法, 只能通过server.error.path参数设置错误页面路径
//    @Value("${server.error.path:${error.path:/error}}")
//    private String errorPath;
//    @Override
//    public String getErrorPath() {
//        return errorPath;
//    }

    /* ****************************************************************************************************
     * 异常处理
     */

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private ErrorHandler errorHandler;

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

        /*
            响应码覆盖为200
            已知BUG:
                1.在spring-boot=2.0.9.RELEASE(spring=5.0.x)时, 这个设置无效! 只有2.0.9.RELEASE这个版本有问题, 其他好的(和spring也没关系)
         */
        response.setStatus(HttpStatus.OK.value());

        try {
            //交由ErrorHandler处理
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
