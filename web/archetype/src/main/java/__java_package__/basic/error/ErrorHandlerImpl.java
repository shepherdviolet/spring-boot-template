// <replace-by> package ${java_package}.basic.error;
package __java_package__.basic.error;

import com.github.shepherdviolet.glacimon.java.x.trace.Trace;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MissingServletRequestParameterException;

// <replace-by> import ${java_package}.basic.entity.RejectException;
import __java_package__.basic.entity.RejectException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 统一错误处理
 *
 * @author foo
 */
@Component
public class ErrorHandlerImpl implements ErrorHandler {

    private static final String SERVLET_ERROR_PREFIX = "javax.servlet.error.";
    private static final String STATUS_CODE = "status_code";
    private static final String MESSAGE = "message";
    private static final String REQUEST_URI = "request_uri";

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private MessageSource messageSource;

    @Override
    public Map<String, Object> handleThrowable(HttpServletRequest request, HttpServletResponse response, Throwable throwable) {
        //错误信息
        String errorCode = RejectException.UNDEFINED_ERROR_CODE;
        String errorDescription = RejectException.UNDEFINED_ERROR_CODE;
        Object[] args = null;

        if (throwable instanceof RejectException) {
            //从异常中获取错误信息
            errorCode = ((RejectException) throwable).getCode();
            errorDescription = ((RejectException) throwable).getDescription();
            args = ((RejectException) throwable).getArgs();
        } else if (throwable instanceof BindException) {
            //从Validation结果中获取错误信息
            BindingResult bindingResult = ((BindException) throwable).getBindingResult();
            if (bindingResult != null) {
                List<ObjectError> errors = bindingResult.getAllErrors();
                if (errors != null && errors.size() > 0) {
                    ObjectError error = errors.get(0);
                    if (error instanceof FieldError) {
                        errorCode = RejectException.ILLEGAL_REQUEST_FIELD;
                        errorDescription = error.getDefaultMessage();
                        args = new Object[]{((FieldError) error).getField()};
                    }
                }
            }
        } else if (throwable instanceof MissingServletRequestParameterException) {
            errorCode = RejectException.ILLEGAL_REQUEST_FIELD;
            errorDescription = "Missing required url parameter '" + ((MissingServletRequestParameterException) throwable).getParameterName() + "'";
        }

        //打印日志
        logger.error("Error occurred! The request uri is " + request.getAttribute(SERVLET_ERROR_PREFIX + REQUEST_URI), throwable);

        return buildResponse(request, errorCode, errorDescription, args);
    }

    @Override
    public Map<String, Object> handleError(HttpServletRequest request, HttpServletResponse response) {
        //错误信息
        String errorDescription;
        //从request中获取错误信息
        errorDescription = request.getAttribute(SERVLET_ERROR_PREFIX + STATUS_CODE) + ":" +
                request.getAttribute(SERVLET_ERROR_PREFIX + MESSAGE);

        //打印日志
        logger.error("Error occurred! The request uri is " + request.getAttribute(SERVLET_ERROR_PREFIX + REQUEST_URI) + ", error description: " + errorDescription,
                new Exception(errorDescription));

        return buildResponse(request, RejectException.UNDEFINED_ERROR_CODE, errorDescription, null);
    }

    /**
     * 组返回报文
     */
    private Map<String, Object> buildResponse(HttpServletRequest request, String errorCode, String errorDescription, Object[] args) {
        //翻译错误信息
        String translatedDescription = messageSource.getMessage(errorDescription, args, errorDescription, request.getLocale());
        logger.debug(errorDescription + " -> " + translatedDescription);

        //组报文返回
        Map<String, Object> responseMap = new HashMap<>(8);
        responseMap.put("code", errorCode);
        responseMap.put("description", translatedDescription);
        responseMap.put("traceId", Trace.getTraceId());
        return responseMap;
    }

}
