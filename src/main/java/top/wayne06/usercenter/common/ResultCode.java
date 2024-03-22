package top.wayne06.usercenter.common;

/**
 * 结果枚举类
 *
 * @author wayne
 */
public enum ResultCode {

    /*** 通用部分：100-599 ***/

    /**
     * 成功请求
     */
    SUCCESS(200, "successful"),

    /**
     * 重定向：需要进一步的操作以完成请求
     */
    REDIRECT(301, "redirect"),

    /**
     * 资源未找到
     */
    NOT_FOUND(404, "not found"),

    /**
     * 服务端错误
     */
    SERVER_ERROR(500, "server error"),

    /*** 定制部分 ***/
    // 1000~1999 用户模块错误
    // 2000~2999 订单模块错误
    // 3000~3999 商品模块错误

    PARAMS_ERROR(1001, "params error"),
    NOT_LOGIN_ERROR(1002, "not login"),
    NO_AUTH_ERROR(1003, "no auth"),
    FORBIDDEN(1004, "forbidden"),
    OPERATION_ERROR(1005, "operation error"),
    SYSTEM_ERROR(1006, "system error");

    /**
     * 响应状态码
     */
    private final Integer code;

    /**
     * 响应信息
     */
    private final String message;

    ResultCode(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
