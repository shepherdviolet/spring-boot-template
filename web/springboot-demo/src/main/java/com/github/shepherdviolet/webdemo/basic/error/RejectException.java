package com.github.shepherdviolet.webdemo.basic.error;

import com.github.shepherdviolet.glacimon.java.conversion.StringUtils;

/**
 * <p>给前端返回拒绝报文 (HTTP_CODE == 200)</p>
 *
 * <pre>
 *     // NOTICE: Hint is only output in the log for troubleshooting and is not returned to the front end.
 *     throw RejectException.create("illegal_arg")
 *                          .msg("Illegal argument {})
 *                          .hint("Illegal argument {} in processor {}")
 *                          .args(argName, processorName)
 *                          .cause(cause)
 *                          .build();
 * </pre>
 *
 * <p>错误描述可以使用占位符, 参数值按顺序填充至错误描述中.
 * 例如: .msg("Illegal argument {}").args("foo"), 最终错误描述为"Illegal argument foo"</p>
 * <p></p>
 * <p> code 错误码 (对前端展现) </p>
 * <p> msg 错误信息 (对前端展现, 可选) </p>
 * <p> hint 错误提示 (不对前端展现, 可选, 仅输出到日志, 用于排查问题) </p>
 * <p> cause cause </p>
 * <p> args 参数 (用于格式化错误信息和错误提示) </p>
 *
 * @author S.Violet
 */
public class RejectException extends RuntimeException {

    private static final long serialVersionUID = 5319635695343417296L;

    /**
     * <p>给前端返回拒绝报文 (HTTP_CODE == 200)</p>
     *
     * <pre>
     *     // NOTICE: Hint is only output in the log for troubleshooting and is not returned to the front end.
     *     throw RejectException.create("illegal_arg")
     *                          .msg("Illegal argument {})
     *                          .hint("Illegal argument {} in processor {}")
     *                          .args(argName, processorName)
     *                          .cause(cause)
     *                          .build();
     * </pre>
     *
     * <p>错误描述可以使用占位符, 参数值按顺序填充至错误描述中.
     * 例如: .msg("Illegal argument {}").args("foo"), 最终错误描述为"Illegal argument foo"</p>
     * <p></p>
     * <p> code 错误码 (对前端展现) </p>
     * <p> msg 错误信息 (对前端展现, 可选) </p>
     * <p> hint 错误提示 (不对前端展现, 可选, 仅输出到日志, 用于排查问题) </p>
     * <p> cause cause </p>
     * <p> args 参数 (用于格式化错误信息和错误提示) </p>
     */
    public static Builder create(String code) {
        return new Builder(code);
    }

    private final String code;
    private final String msg;
    private final String hint;
    private final Object[] args;

    /**
     * 建议用RejectException#create方法创建实例 (本构造函数不支持设置args, 不支持错误信息和错误提示格式化)
     *
     * @param code 错误码 (对前端展现)
     * @param msg 错误信息 (对前端展现, 可选)
     * @param hint 错误提示 (不对前端展现, 可选, 仅输出到日志, 用于排查问题)
     * @param cause cause
     */
    RejectException(String code, String msg, String hint, Throwable cause) {
        this(code, msg, hint, cause, (Object[]) null);
    }

    /**
     * @param code 错误码 (对前端展现)
     * @param msg 错误信息 (对前端展现, 可选)
     * @param hint 错误提示 (不对前端展现, 可选, 仅输出到日志, 用于排查问题)
     * @param cause cause
     * @param args 参数 (用于格式化错误信息和错误提示)
     */
    private RejectException(String code, String msg, String hint, Throwable cause, Object... args) {
        super(buildThrowableMessage(code, msg, hint), cause);
        this.code = code == null ? "" : code;
        this.msg = msg == null ? this.code : msg;
        this.hint = hint == null ? this.code : hint;
        this.args = args == null ? new Object[0] : args;
    }

    private static String buildThrowableMessage(String code, String msg, String hint) {
        StringBuilder stringBuilder = new StringBuilder((code != null ? code.length() : 0)
                + (msg != null ? msg.length() : 0)
                + (hint != null ? hint.length() : 0) + 16);

        if (code != null) {
            stringBuilder.append(code);
        }
        if (msg != null) {
            stringBuilder.append(" : ").append(msg);
        }
        if (hint != null) {
            stringBuilder.append(" : ").append(hint);
        }
        return stringBuilder.toString();
    }

    /**
     * 错误码 (对前端展现)
     */
    public String getCode() {
        return code;
    }

    /**
     * 错误信息 (对前端展现, 可选)
     */
    public String getMsg() {
        return msg;
    }

    /**
     * 错误提示 (不对前端展现, 可选, 仅输出到日志, 用于排查问题)
     */
    public String getHint() {
        return hint;
    }

    /**
     * 参数 (用于格式化错误信息和错误提示)
     */
    public Object[] getArgs() {
        return args;
    }

    /**
     * RejectException创建类, 建议用RejectException#create方法创建实例
     */
    public static class Builder {

        private String code;
        private String msg;
        private String hint;
        private Throwable cause;
        private Object[] args;

        /**
         * @param code 错误码 (对前端展现)
         */
        private Builder(String code) {
            this.code = code;
        }

        /**
         * 错误信息 (对前端展现, 可选)
         */
        public Builder msg(String msg) {
            this.msg = msg;
            return this;
        }

        /**
         * 错误提示 (不对前端展现, 可选, 仅输出到日志, 用于排查问题)
         */
        public Builder hint(String hint) {
            this.hint = hint;
            return this;
        }

        /**
         * cause
         */
        public Builder cause(Throwable cause) {
            this.cause = cause;
            return this;
        }

        /**
         * 参数 (用于格式化错误信息和错误提示)
         */
        public Builder args(Object... args) {
            this.args = args;
            return this;
        }

        /**
         * 创建异常实例
         */
        public RejectException build() {
            return new RejectException(code,
                    StringUtils.replacePlaceholdersByArgs(msg, "{}", args),
                    StringUtils.replacePlaceholdersByArgs(hint, "{}", args),
                    cause,
                    args);
        }

    }

}
