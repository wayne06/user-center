package top.wayne06.usercenter.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import top.wayne06.usercenter.common.GeneralResponse;
import top.wayne06.usercenter.common.ResultCode;

/**
 *
 *
 * @author wayne
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(BusinessException.class)
    public GeneralResponse<?> businessExceptionHandler(BusinessException e) {
        log.error("Business Exception", e);
        return GeneralResponse.failure(e.getCode(), e.getMessage());
    }

    @ExceptionHandler(RuntimeException.class)
    public GeneralResponse<?> runtimeExceptionHandler(RuntimeException e) {
        log.error("RuntimeException", e);
        return GeneralResponse.failure(ResultCode.SYSTEM_ERROR);
    }
}
