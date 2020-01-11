package com.sl.springbootshirojwt.controller;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.sl.springbootshirojwt.common.bean.ResultBean;
import com.sl.springbootshirojwt.common.enums.ResultCode;
import com.sl.springbootshirojwt.entity.User;
import com.sl.springbootshirojwt.service.IUserService;
import com.sl.springbootshirojwt.util.JWTUtil;
import com.sl.springbootshirojwt.util.MD5Util;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.subject.Subject;
import org.hibernate.validator.constraints.Length;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotEmpty;

@RestController
@RequestMapping("/sys")
@Validated
public class SysController {

    @Autowired
    private IUserService userService;

    @PostMapping("/login")
    public ResultBean<JSONObject> login(@NotEmpty(message = "用户名不能为空") String username,
                                        @Length(min = 6, message = "密码长度不能低于6位") @NotEmpty(message = "密码不能为空") String password,
                                        HttpServletResponse response) {
        UsernamePasswordToken token = new UsernamePasswordToken(username, password);
        // 获取Subject对象
        Subject subject = SecurityUtils.getSubject();
        try {
            subject.login(token);
            User user=(User)subject.getPrincipal();
            response.setHeader("Authorization", JWTUtil.sign(username, user.getId()));
            response.setHeader("Access-Control-Expose-Headers", "Authorization");
            return new ResultBean<>(ResultCode.SUCCESS.getCode(), "登录成功！", new JSONObject());
        } catch (UnknownAccountException e) {
            return new ResultBean<>(ResultCode.FAIL.getCode(), "账号或密码错误！", new JSONObject());
        } catch (IncorrectCredentialsException e) {
            return new ResultBean<>(ResultCode.FAIL.getCode(), "账号或密码错误！", new JSONObject());
        } catch (LockedAccountException e) {
            return new ResultBean<>(ResultCode.FAIL.getCode(), "账号已锁定，请联系管理员！", new JSONObject());
        } catch (AuthenticationException e) {
            return new ResultBean<>(ResultCode.FAIL.getCode(), "认证失败！", new JSONObject());
        }
    }

    /*@PostMapping("/login")
    public ResultBean<JSONObject> login(@NotEmpty(message = "用户名不能为空") String username,
                                        @Length(min = 6, message = "密码长度不能低于6位") @NotEmpty(message = "密码不能为空") String password,
                                        HttpServletResponse response) {
        ResultBean<JSONObject> resultBean = new ResultBean<>();
        User user = userService.getOne(new QueryWrapper<User>().eq("username", username));
        if (user == null || !user.getPassword().equals(MD5Util.encryptBySalt(password, user.getSalt()))) {
            resultBean.setCode(ResultCode.FAIL.getCode()).setMsg("用户名或密码错误！");
        }
        response.setHeader("Authorization", JWTUtil.sign(user.getUsername(), user.getId()));
        response.setHeader("Access-Control-Expose-Headers", "Authorization");
        return resultBean;
    }*/

    @PostMapping("/register")
    public ResultBean<JSONObject> register(@Validated User user) {
        ResultBean<JSONObject> resultBean = new ResultBean<>();
        if (!userService.register(user)) {
            resultBean.setCode(ResultCode.FAIL.getCode()).setMsg("用户名重复！");
        }
        return resultBean;
    }

}
