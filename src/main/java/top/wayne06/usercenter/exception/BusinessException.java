package top.wayne06.usercenter.exception;

import top.wayne06.usercenter.common.ResultCode;

/**
 *
 *
 * @author wayne
 */
public class BusinessException extends RuntimeException {

    private final int code;

    public int getCode() {
        return code;
    }

    public BusinessException(int code, String message) {
        super(message);
        this.code = code;
    }
    public BusinessException(ResultCode code) {
        super(code.getMessage());
        this.code = code.getCode();
    }

    public BusinessException(ResultCode code, String message) {
        super(message);
        this.code = code.getCode();
    }

}
