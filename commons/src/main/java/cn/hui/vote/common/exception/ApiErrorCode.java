package cn.hui.vote.common.exception;

/**
 * 错误码
 */
public enum ApiErrorCode implements IExceptionCode {

            SUCCESS(200, "成功"),

            SYSTEM_ERROR(999999, "系统异常"),

            SYSTEM_BUSY_ERROR(919999, "系统繁忙，请稍后"),

            API_INPUT_PARAM_ERORR(910000, "请求参数错误"),

            VOTE_NOT_ALLOW_MULTI_ERORR(910001, "not allow multi"),

            API_AUTH_ERORR(990000, "api auth failed"),

            API_AUTH_EXPIRED_ERORR(990001, "request is expired"),

            API_FREQUENCY_HIGH_ERORR(990002, "request frequency too high"),

    ;

    ApiErrorCode(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    private int    code;
    private String msg;

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    @Override
    public String toString() {
        final StringBuilder sbr = new StringBuilder();
        sbr.append("{");
        sbr.append("\"code\":").append(getCode());
        sbr.append(",\"msg\":\"").append(getMsg()).append("\"");
        sbr.append("}");

        return sbr.toString();
    }
}
