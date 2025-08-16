package com.darkyellowcat.usercenter.model.domain.request;

import lombok.Data;

import java.io.Serializable;

/**
 * 用户注册请求
 * 用于接收用户注册时的请求参数
 * @author darkcarrot
 */
@Data
public class UserRegisterRequest implements Serializable {

    private static final  long serialVersionUID = 1L;
    /**
     * 账户
     */
    private String userAccount;
    /**
     * 密码
     */
    private String userPassword;
    /**
     * 确认密码
     */
    private String checkPassword;
    /**
     * 星球
     */
    private String planetCode;
}
