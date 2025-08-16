package com.darkyellowcat.usercenter.model.domain.request;

import lombok.Data;

/**
 * 用户登录请求
 * 用于接收用户登录时的请求参数
 * @author darkcarrot
 */

@Data
public class UserLoginRequest {
    private static final  long serialVersionUID = 1L;
    /**
     * 账户
     */
    private String userAccount;
    /**
     * 密码
     */
    private String userPassword;
}
