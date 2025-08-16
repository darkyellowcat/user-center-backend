package com.darkyellowcat.usercenter.exception;

import com.darkyellowcat.usercenter.ErrorCode;
import com.darkyellowcat.usercenter.common.BaseResponse;
import com.darkyellowcat.usercenter.common.ResultUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 全局异常处理器
 * 处理业务异常和运行时异常
 *
 * @author darkcarrot
 */
@RestControllerAdvice
@Slf4j
public class GolbalExceptionHandler {

    @ExceptionHandler(BusinessException.class)
    public BaseResponse businessExceptionHandler(BusinessException e) {
        log.error("business exception:" + e.getMessage() , e);
        return ResultUtils.error(e.getCode(), e.getMessage(), e.getDescription());
    }

    @ExceptionHandler(RuntimeException.class)
    public  BaseResponse runtimeExceptionHandler(RuntimeException e) {
        log.error("runtime exception: ", e);
        return ResultUtils.error(ErrorCode.PARAMS_ERROR, e.getMessage(), "");
    }
}
