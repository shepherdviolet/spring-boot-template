package com.github.shepherdviolet.webdemo.basic.error;

import com.github.shepherdviolet.glacimon.java.conversion.StringUtils;

/**
 * <p>给前端返回拒绝报文 (HTTP_CODE == 200)</p>
 *
 * <pre>
 *     throw RejectException.create("illegal-arg").description("Illegal argument {}").args("foo").cause(cause).build();
 * </pre>
 *
 * <p>错误描述可以使用占位符, 参数值按顺序填充至错误描述中.
 * 例如: .description("Illegal argument {}").args("foo"), 最终错误描述为"Illegal argument foo"</p>
 *
 * @author S.Violet
 */
public class RejectException extends RuntimeException {

    private static final long serialVersionUID = 5319635695343417296L;

    /**
     * <p>给前端返回拒绝报文 (HTTP_CODE == 200)</p>
     *
     * <pre>
     *     throw RejectException.create("illegal-arg").description("Illegal argument {}").args("foo").cause(cause).build();
     * </pre>
     *
     * <p>错误描述可以使用占位符, 参数值按顺序填充至错误描述中.
     * 例如: .description("Illegal argument {}").args("foo"), 最终错误描述为"Illegal argument foo"</p>
     *
     */
    public static Builder create(String errorCode) {
        return new Builder(errorCode);
    }

    private final String code;
    private final String description;
    private final Object[] args;

    /**
     * <p>给前端返回拒绝报文 (HTTP_CODE == 200)</p>
     *
     * <pre>
     *     throw RejectException.create("illegal-arg").description("Illegal argument {}").args("foo").cause(cause).build();
     * </pre>
     *
     * <p>错误描述可以使用占位符, 参数值按顺序填充至错误描述中.
     * 例如: .description("Illegal argument {}").args("foo"), 最终错误描述为"Illegal argument foo"</p>
     *
     * @param errorCode 错误码
     * @param errorDescription 错误描述
     * @param cause cause
     * @param args 参数(用于格式化错误描述)
     */
    public RejectException(String errorCode, String errorDescription, Throwable cause, Object... args) {
        super(errorCode + " : " + StringUtils.replacePlaceholdersByArgs(errorDescription, "{}", args), cause);
        this.code = errorCode;
        this.description = StringUtils.replacePlaceholdersByArgs(errorDescription, "{}", args);
        this.args = args;
    }

    public String getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }

    public Object[] getArgs() {
        return args;
    }

    public static class Builder {

        private String code;
        private String description;
        private Throwable cause;
        private Object[] args;

        private Builder(String errorCode) {
            this.code = errorCode;
            this.description = errorCode;
        }

        public Builder description(String errorDescription) {
            this.description = errorDescription;
            return this;
        }

        public Builder cause(Throwable cause) {
            this.cause = cause;
            return this;
        }

        public Builder args(Object... args) {
            this.args = args;
            return this;
        }

        public RejectException build() {
            return new RejectException(code, description, cause, args);
        }

    }

}
