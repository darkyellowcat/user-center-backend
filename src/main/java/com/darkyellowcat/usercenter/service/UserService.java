package com.darkyellowcat.usercenter.service;

import com.darkyellowcat.usercenter.model.domain.User;
import com.baomidou.mybatisplus.extension.service.IService;
import jakarta.servlet.http.HttpServletRequest;

/**
* @author darkcarrot
* @description 针对表【user(用户)】的数据库操作Service
* @createDate 2025-08-02 10:56:48
*/
public interface UserService extends IService<User> {

    /**
     * 用户登录状态
     */
    public static final String USER_LOGIN_STATE = "userLoginState";

    /**
     * 用户注册
     * @param userAccount
     * @param userPassword
     * @param checkPassword
     * @return
     */
    long userRegister(String userAccount, String userPassword, String checkPassword, String planetCode);

    /**
     * 用户登录
     *
     * @param userAccount
     * @param userPassword
     * @param request
     * @return
     */
    User userLogin(String userAccount, String userPassword, HttpServletRequest request);

    /**
     * 用户注销
     *
     * @param request
     * @return
     */
    int userLogout(HttpServletRequest request);
}
