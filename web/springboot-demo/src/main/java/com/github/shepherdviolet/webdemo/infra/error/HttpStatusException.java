package com.github.shepherdviolet.webdemo.infra.error;

import org.springframework.http.HttpStatus;

/**
 * 给前端返回HTTP状态码 (HTTP_CODE != 200, 无响应报文体), 继承RejectException
 *
 * @author S.Violet
 */
public class HttpStatusException extends RejectException {

    private static final long serialVersionUID = 3579879803213460806L;

    public static final String ERROR_CODE_PREFIX = "http_status_code_";

    public static final int CODE_500 = 500;

    private final int httpStatusCode;

    /**
     * @param httpStatusCode 返回HTTP状态码
     */
    public HttpStatusException(int httpStatusCode) {
        super(ERROR_CODE_PREFIX + (httpStatusCode >= 0 ? httpStatusCode : CODE_500),
                "Return http status code " + (httpStatusCode >= 0 ? httpStatusCode : CODE_500),
                "Return http status code " + (httpStatusCode >= 0 ? httpStatusCode : CODE_500),
                null);
        this.httpStatusCode = (httpStatusCode >= 0 ? httpStatusCode : CODE_500);
    }

    /**
     * @param httpStatus 返回HTTP状态码
     */
    public HttpStatusException(HttpStatus httpStatus) {
        this(httpStatus != null ? httpStatus.value() : CODE_500);
    }

    public int getHttpStatusCode() {
        return httpStatusCode;
    }
}
