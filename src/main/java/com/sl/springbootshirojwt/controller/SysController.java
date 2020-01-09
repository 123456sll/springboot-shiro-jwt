package com.sl.springbootshirojwt.controller;

import com.alibaba.fastjson.JSONObject;
import com.sl.springbootshirojwt.common.bean.ResultBean;
import com.sl.springbootshirojwt.common.enums.ResultCode;
import com.sl.springbootshirojwt.entity.User;
import com.sl.springbootshirojwt.service.IUserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.subject.Subject;
import org.hibernate.validator.constraints.Length;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotEmpty;

@RestController
@RequestMapping("/sys")
@Validated
public class SysController {

    @Autowired
    private IUserService userService;

    @PostMapping("/login")
    public ResultBean<JSONObject> login(@NotEmpty(message = "用户名不能为空") String username,
                                        @Length(min = 6, message = "密码长度不能低于6位") @NotEmpty(message = "密码不能为空") String password) {
        UsernamePasswordToken token = new UsernamePasswordToken(username, password);
        // 获取Subject对象
        Subject subject = SecurityUtils.getSubject();
        try {
            subject.login(token);
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

    @PostMapping("/register")
    public ResultBean<JSONObject> register(@Validated User user) {
        ResultBean<JSONObject> resultBean = new ResultBean<>();
        if (!userService.register(user)) {
            resultBean.setCode(ResultCode.FAIL.getCode()).setMsg("用户名重复！");
        }
        return resultBean;
    }

}
