package com.darkyellowcat.usercenter.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.darkyellowcat.usercenter.ErrorCode;
import com.darkyellowcat.usercenter.exception.BusinessException;
import com.darkyellowcat.usercenter.model.domain.User;
import com.darkyellowcat.usercenter.service.UserService;
import com.darkyellowcat.usercenter.mapper.UserMapper;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import javax.annotation.Resource;
import java.nio.charset.StandardCharsets;

/**
* @author darkcarrot
* @description 针对表【user(用户)】的数据库操作Service实现
* @createDate 2025-08-02 10:56:48
*/
@Service
@Slf4j
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
    implements UserService{

    @Resource
    private UserMapper userMapper;

    private final static String salt = "darkyellowcat";

    @Override
    public long userRegister(String userAccount, String userPassword, String checkPassword, String planetCode) {
        // 参数校验
        if(StringUtils.isAllBlank(userAccount, userPassword, checkPassword, planetCode)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "参数不能为空");
        }
        if(userAccount.length() < 4) {
//            throw new IllegalArgumentException("用户账号长度不能小于4");
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "用户账号长度不能小于4");
        }
        if(userPassword.length() < 8) {
//            throw new IllegalArgumentException("用户密码长度不能小于8");
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "用户密码长度不能小于8");
        }
        if(!userPassword.equals(checkPassword)) {
//            throw new IllegalArgumentException("两次输入的密码不一致");
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "两次输入的密码不一致");
        }
        if(planetCode.length() > 5) {
//            throw new IllegalArgumentException("星球编号长度不能大于5");
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "星球编号长度不能大于5");
        }
        // 校验用户账号是否已存在
        QueryWrapper<User> queryWrapper = new QueryWrapper<User>();
        queryWrapper.eq("userAccount", userAccount);
        long count = userMapper.selectCount(queryWrapper);
        if(count > 0) {
            throw new IllegalArgumentException("用户账号已存在");
        }
        // 校验星球编号是否已存在
        queryWrapper = new QueryWrapper<User>();
        queryWrapper.eq("planetCode", planetCode);
        count = userMapper.selectCount(queryWrapper);
        if(count > 0) {
            throw new IllegalArgumentException("星球编号已存在");
        }
        // 校验用户账号格式
        String validUserAccountRegex = "^[a-zA-Z0-9_]+$";
        if(!userAccount.matches(validUserAccountRegex)) {
            throw new IllegalArgumentException("用户账号只能包含字母、数字和下划线");
        }
        // 密码加密
        String newPassword = DigestUtils.md5DigestAsHex((userPassword + salt).getBytes(StandardCharsets.UTF_8));
        // 插入数据
        User user = new User();
        user.setUserAccount(userAccount);
        user.setUserPassword(newPassword);
        boolean save = this.save(user);
        if(!save) {
            throw new RuntimeException("用户注册失败");
        }
        return user.getId();

    }

    @Override
    public User userLogin(String userAccount, String userPassword, HttpServletRequest request) {
        if(StringUtils.isAllBlank(userAccount, userPassword)) {
            throw new IllegalArgumentException("参数不能为空");
        }
        if(userAccount.length() < 4) {
            throw new IllegalArgumentException("用户账号长度不能小于4");
        }
        if(userPassword.length() < 8) {
            throw new IllegalArgumentException("用户密码长度不能小于8");
        }
        // 校验用户账号格式
        String validUserAccountRegex = "^[a-zA-Z0-9_]+$";
        if(!userAccount.matches(validUserAccountRegex)) {
            throw new IllegalArgumentException("用户账号只能包含字母、数字和下划线");
        }
        // 密码加密
        String newPassword = DigestUtils.md5DigestAsHex((userPassword + salt).getBytes(StandardCharsets.UTF_8));
        // 查询用户是否存在
        QueryWrapper<User> queryWrapper = new QueryWrapper<User>();
        queryWrapper.eq("userAccount", userAccount);
        queryWrapper.eq("userPassword", newPassword);
        User user = userMapper.selectOne(queryWrapper);
        if(user == null) {
            log.info("user login failed, userAccount: {}, userPassword: {}", userAccount, newPassword);
            throw new IllegalArgumentException("用户账号或密码错误");
        }

        long count = userMapper.selectCount(queryWrapper);
        if(count > 0) {
            throw new IllegalArgumentException("用户账号已存在");
        }
        // 设置用户脱敏信息
        User safeUser = new User();
        safeUser.setId(user.getId());
        safeUser.setUsername(user.getUsername());
        safeUser.setUserAccount(user.getUserAccount());
        safeUser.setAvatarUrl(user.getAvatarUrl());
        safeUser.setGender(user.getGender());
        safeUser.setUserPassword(user.getUserPassword());
        safeUser.setPhone(user.getPhone());
        safeUser.setEmail(user.getEmail());
        safeUser.setPlanetCode(user.getPlanetCode());
        safeUser.setUserRole(user.getUserRole());
        safeUser.setUserStatus(user.getUserStatus());
        safeUser.setCreateTime(user.getCreateTime());
        // 记录登录日志
        request.getSession().setAttribute(USER_LOGIN_STATE, user);

        // 返回用户信息
        return safeUser;
    }

    @Override
    public int userLogout(HttpServletRequest request) {
        request.getSession().removeAttribute(USER_LOGIN_STATE);
        return 1;
    }
}




