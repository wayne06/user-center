package top.wayne06.usercenter.common;

import lombok.Data;

import java.io.Serializable;

/**
 * 统一返回类
 *
 * @author wayne
 */
@Data
public class GeneralResponse<T> implements Serializable {

    private Boolean success;

    private Integer code;

    private T data;

    private String message;

    private GeneralResponse() {
        this.code = 200;
        this.success = true;
    }

    private GeneralResponse(T data) {
        this.code = 200;
        this.success = true;
        this.data = data;
    }

    private GeneralResponse(Integer code, String message) {
        this.code = code;
        this.success = false;
        this.message = message;
    }

    private GeneralResponse(ResultCode resultCode) {
        this.code = resultCode.getCode();
        this.success = false;
        this.message = resultCode.getMessage();
    }

    public static <T> GeneralResponse<T> success() {
        return new GeneralResponse<>();
    }

    public static <T> GeneralResponse<T> success(T data) {
        return new GeneralResponse<T>(data);
    }

    public static <T> GeneralResponse<T> failure(ResultCode resultCode) {
        return new GeneralResponse<>(resultCode);
    }

    public static <T> GeneralResponse<T> failure(Integer code, String message) {
        return new GeneralResponse<>(code, message);
    }

}

