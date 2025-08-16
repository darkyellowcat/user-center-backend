package com.darkyellowcat.usercenter.common;

import com.darkyellowcat.usercenter.ErrorCode;

/**
 * 工具类，用于生成通用的响应结果
 *
 * @author darkcarrot
 */
public class ResultUtils {
    /**
     * 成功响应
     *
     * @param data 响应数据
     * @param <T>  数据类型
     * @return BaseResponse<T>
     */
    public static <T> BaseResponse<T> success(T data) {
        return new BaseResponse<>(data, 0, null);
    }
    /**
     * 失败
     */
    public static BaseResponse error(ErrorCode errorCode) {
        return new BaseResponse<>(errorCode);
    }

    public static BaseResponse error(ErrorCode errorCode, String message, String description) {
        return new BaseResponse<>(errorCode, message, description);
    }

    public static BaseResponse error(int code, String message, String description) {
        return new BaseResponse<>(code, message, description);
    }

    public static BaseResponse error(ErrorCode errorCode, String description) {
        return new BaseResponse<>(errorCode, description);
    }
}
