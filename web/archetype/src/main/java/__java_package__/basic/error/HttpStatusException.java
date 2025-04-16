// <replace-by> package ${java_package}.basic.error;
package __java_package__.basic.error;

import org.springframework.http.HttpStatus;

/**
 * 给前端返回HTTP状态码 (HTTP_CODE != 200, 无响应报文体)
 *
 * @author foo
 */
public class HttpStatusException extends RuntimeException {

    public static final int CODE_570 = 570;
    public static final int CODE_571 = 571;

    private final int httpStatusCode;

    /**
     * @param httpStatusCode 返回HTTP状态码
     */
    public HttpStatusException(int httpStatusCode) {
        super("Return HTTP status code " + (httpStatusCode >= 0 ? httpStatusCode : CODE_570) + " to the frontend");
        this.httpStatusCode = (httpStatusCode >= 0 ? httpStatusCode : CODE_570);
    }

    /**
     * @param httpStatus 返回HTTP状态码
     */
    public HttpStatusException(HttpStatus httpStatus) {
        this(httpStatus != null ? httpStatus.value() : CODE_571);
    }

    public int getHttpStatusCode() {
        return httpStatusCode;
    }
}
