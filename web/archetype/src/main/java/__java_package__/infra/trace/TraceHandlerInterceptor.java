// <replace-by> package ${java_package}.infra.trace;
package __java_package__.infra.trace;

import com.github.shepherdviolet.glacimon.java.x.trace.Trace;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * MVC拦截器
 */
@Component
public class TraceHandlerInterceptor implements HandlerInterceptor {

    private static final Logger logger = LoggerFactory.getLogger(TraceHandlerInterceptor.class);

    @Value("${server.error.path:${error.path:/error}}")
    private String errorPath;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //路径
        String uri = request.getRequestURI();

        if (!errorPath.equals(uri)) {
            //生成跟踪号
            Trace.start();
            //打印请求路径
            logger.info("New request with URI: " + uri);
        } else {
            //请求处理异常, 进入异常处理, 打印日志
            logger.info("To error handler: " + uri);
        }

        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        //请求处理结束, 打印日志
        logger.info("Request handled");
    }

}
