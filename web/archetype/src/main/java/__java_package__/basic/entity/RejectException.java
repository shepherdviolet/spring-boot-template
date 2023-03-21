// <replace-by> package ${java_package}.basic.entity;
package __java_package__.basic.entity;

/**
 * 拒绝异常
 *
 * @author foo
 */
public class RejectException extends RuntimeException {

    public static final String UNDEFINED_ERROR_CODE = "undefined-error";
    public static final String ILLEGAL_REQUEST_FIELD = "illegal-request-field";

    private String code;
    private String description;
    private Object[] args;

    /**
     * @param errorCode 错误码
     * @param errorDescription 错误描述
     */
    public RejectException(String errorCode, String errorDescription) {
        super(errorCode + " : " + errorDescription);
        this.code = errorCode;
        this.description = errorDescription;
    }

    /**
     * @param errorCode 错误码
     * @param errorDescription 错误描述
     * @param cause cause
     */
    public RejectException(String errorCode, String errorDescription, Throwable cause) {
        super(errorCode + " : " + errorDescription, cause);
        this.code = errorCode;
        this.description = errorDescription;
    }

    /**
     * 追加错误参数(可以用于翻译)
     * @param args 错误参数
     */
    public RejectException withArgs(Object... args) {
        this.args = args;
        return this;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Object[] getArgs() {
        return args;
    }

    public void setArgs(Object[] args) {
        this.args = args;
    }

}
