package com.darkyellowcat.usercenter.common;

import com.darkyellowcat.usercenter.ErrorCode;
import lombok.Data;

import java.io.Serializable;

/**
 * 通用返回类
 *
 * @author darkcarrot
 */
@Data
public class BaseResponse<T> implements Serializable {

    private int code;

    private T data;

    private String message;

    private String description;

    public BaseResponse(T data, String message, int code, String description) {
        this.data = data;
        this.message = message;
        this.code = code;
        this.description = description;
    }

    public BaseResponse(T data, int code) {
        this(data, null, code, null);
    }

    public BaseResponse(T data, int code, String description) {
        this(data, null, code, null);
    }

    public BaseResponse(ErrorCode errorCode){
        this(null, errorCode.getMessage(), errorCode.getCode(), errorCode.getDescription());
    }

    public BaseResponse(ErrorCode errorCode, String description){
//        super(errorCode.getMessage());
        this.code = errorCode.getCode();
        this.description = description;
    }

    public BaseResponse(ErrorCode errorCode, String message, String description) {
        this(null, message, errorCode.getCode(), description);
    }

    public BaseResponse(int code, String message, String description) {
        this(null, message, code, description);
    }
}
