package com.darkyellowcat.usercenter;

/**
 * 错误码枚举类
 * 用于定义系统中可能出现的错误码
 * 每个错误码都应该有一个唯一的标识符和描述信息
 * @author darkcarrot
 */
public enum ErrorCode {

    SUCCESS("操作成功", 0, "操作成功"),
    PARAMS_ERROR("请求参数错误", 40000, "请求参数不合法或缺失"),
    PARAMS_NULL_ERROR("请求参数不能为空", 40001, "请求参数不能为空"),
    NO_AUTH("无权限", 40101, "用户无权限"),
    NOT_LOGIN("未登录", 40100, "用户未登录"),
    SERVER_ERROR("服务器内部错误", 50000, "服务器发生未知错误"),;

    /**
     * 错误码
     * 用于唯一标识错误类型
     */
    private final int code;
    private final String message;
    private final String description;

    ErrorCode(String message, int code, String description) {
        this.message = message;
        this.code = code;
        this.description = description;
    }

    public String getMessage() {
        return message;
    }

    public int getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }
}
