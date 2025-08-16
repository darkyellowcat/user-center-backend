package com.darkyellowcat.usercenter.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.darkyellowcat.usercenter.ErrorCode;
import com.darkyellowcat.usercenter.common.BaseResponse;
import com.darkyellowcat.usercenter.common.ResultUtils;
import com.darkyellowcat.usercenter.exception.BusinessException;
import com.darkyellowcat.usercenter.model.domain.User;
import com.darkyellowcat.usercenter.model.domain.request.UserLoginRequest;
import com.darkyellowcat.usercenter.model.domain.request.UserRegisterRequest;
import com.darkyellowcat.usercenter.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.darkyellowcat.usercenter.constant.UserConstant.ADMIN_ROLE;
import static com.darkyellowcat.usercenter.constant.UserConstant.USER_LOGIN_STATE;

/**
 * 用户控制器
 * 提供用户相关的API接口
 * @author darkcarrot
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Resource
    private UserService userService;

    @PostMapping("/register")
    public BaseResponse<Long> userRegister(@RequestBody UserRegisterRequest userRegisterRequest) {
        if(userRegisterRequest == null) {
            return ResultUtils.error(ErrorCode.PARAMS_ERROR);
        }
        String userAccount = userRegisterRequest.getUserAccount();
        String userPassword = userRegisterRequest.getUserPassword();
        String checkPassword = userRegisterRequest.getCheckPassword();
        String planetCode = userRegisterRequest.getPlanetCode();
        if(StringUtils.isAllBlank(userAccount,userPassword,checkPassword,planetCode)) {
            throw new IllegalArgumentException("参数不能为空");
        }
        long result = userService.userRegister(userAccount, userPassword, checkPassword, planetCode);
        return ResultUtils.success(result);
    }

    /**
     * 用户注册接口
     * @param userLoginRequest 用户注册请求参数
     * @return 返回用户ID
     */
    @PostMapping("/login")
    public BaseResponse<User> userLogin(@RequestBody UserLoginRequest userLoginRequest,
                          HttpServletRequest request)  {
        if(userLoginRequest == null) {
            throw new IllegalArgumentException("请求参数不能为空");
        }
        String userAccount = userLoginRequest.getUserAccount();
        String userPassword = userLoginRequest.getUserPassword();
        if(StringUtils.isAllBlank(userAccount,userPassword)) {
            throw new IllegalArgumentException("参数不能为空");
        }
        User user = userService.userLogin(userAccount, userPassword, request);
        return ResultUtils.success(user);
    }

    /**
     * 用户注销接口
     * @param request HttpServletRequest对象
     * @return 返回当前登录用户信息
     */
    @PostMapping("/logout")
    public BaseResponse<Integer> userLogout(HttpServletRequest request)  {
        if(request == null) {
            throw new IllegalArgumentException("请求参数不能为空");
        }
        int result = userService.userLogout(request);
        return ResultUtils.success(result);
    }

    /**
     * 获取当前登录用户信息
     * @param request HttpServletRequest对象
     * @return 返回当前登录用户信息
     */
    @GetMapping("/search")
    public BaseResponse<List<User>> searchUsers(String username, HttpServletRequest request) {
        // 仅管理员访问
        if(!isAdmin(request)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }

        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        if(StringUtils.isNotBlank(username)) {
            queryWrapper.like("username", username);
        }
        List<User> userList = userService.list(queryWrapper);
        List<User> collect = userList.stream().map(user -> {
            user.setUserPassword(null);
            return user;
        }).collect(Collectors.toList());
        return ResultUtils.success(collect);
    }

    @PostMapping("/delete")
    public BaseResponse<Boolean> deleteUser(@RequestBody long id, HttpServletRequest request) {
        // 仅管理员访问
        if(!isAdmin(request)) {
            throw new IllegalArgumentException("无权限访问");
        }
        if(id <= 0) {
            throw new IllegalArgumentException("用户ID不能为空或小于等于0");
        }
        return ResultUtils.success(userService.removeById(id));
    }

    /**
     * 判断当前用户是否为管理员
     * @param request HttpServletRequest对象
     * @return 返回是否为管理员
     */
    private boolean isAdmin(HttpServletRequest request) {
        Object userObj = request.getSession().getAttribute(USER_LOGIN_STATE);
        if (userObj == null) {
            return false;
        }
        User user = (User) userObj;
        if (user.getUserRole() != ADMIN_ROLE) {
            return false;
        }
        return true;
    }
}
