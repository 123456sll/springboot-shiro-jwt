package com.sl.springbootshirojwt.controller;


import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.sl.springbootshirojwt.common.bean.ResultBean;
import com.sl.springbootshirojwt.common.enums.ResultCode;
import com.sl.springbootshirojwt.entity.User;
import com.sl.springbootshirojwt.service.IUserService;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 用户表 前端控制器
 * </p>
 *
 * @author sl
 * @since 2020-01-05
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private IUserService userService;

    @GetMapping("/{id}")
    // @RequiresRoles(value = {"admin"},logical = Logical.AND)
    @RequiresPermissions(value = {"user:view"}, logical = Logical.AND)
    public ResultBean<User> getUserById(@PathVariable("id") Integer id) {
        User user = userService.getOne(new QueryWrapper<User>().eq("id", id).select("id", "username", "nickname"));
        if (user == null) {
            return new ResultBean<>(ResultCode.SUCCESS.getCode(), "用户不存在！", null);
        }
        return new ResultBean<>(ResultCode.SUCCESS.getCode(), "操作成功！", user);
    }

    @PostMapping
    @RequiresPermissions(value = {"user:edit"})
    public ResultBean<JSONObject> addUser(@Validated User user) {
        ResultBean<JSONObject> resultBean = new ResultBean<>();
        if (!userService.register(user)) {
            resultBean.setCode(ResultCode.FAIL.getCode()).setMsg("用户名重复！");
        }
        return resultBean;
    }

}
