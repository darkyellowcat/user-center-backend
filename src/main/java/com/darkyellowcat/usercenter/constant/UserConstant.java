package com.darkyellowcat.usercenter.constant;

/**
 * 用户常量接口
 * 用于定义用户相关的常量
 * @author darkcarrot
 */

public interface UserConstant {
    String USER_LOGIN_STATE = "userLoginState";

    // 用户状态
    int DEFAULT_ROLE = 0; // 普通用户
    int ADMIN_ROLE = 1; // 管理员
}
