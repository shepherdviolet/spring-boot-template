package com.github.shepherdviolet.webdemo.infra.error;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * 异常处理器
 *
 * @author S.Violet
 */
public interface ErrorHandler {

    /**
     * 处理异常
     *
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @param throwable 异常
     * @return 返回报文
     */
    Map<String, Object> handleThrowable(HttpServletRequest request, HttpServletResponse response, Throwable throwable);

    /**
     * 处理Servlet错误
     *
     * 需要自己从request中获取:
     * request.getAttribute("jakarta.servlet.error.status_code")
     * request.getAttribute("jakarta.servlet.error.message")
     * request.getAttribute("jakarta.servlet.error.request_uri")
     *
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @return 返回报文
     */
    Map<String, Object> handleError(HttpServletRequest request, HttpServletResponse response);

}
