package com.sl.springbootshirojwt.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.sl.springbootshirojwt.entity.User;
import com.sl.springbootshirojwt.mapper.UserMapper;
import com.sl.springbootshirojwt.service.IUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sl.springbootshirojwt.util.MD5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户表 服务实现类
 * </p>
 *
 * @author sl
 * @since 2020-01-05
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public boolean register(User user) {
        if (userMapper.selectOne(new QueryWrapper<User>().eq("username", user.getUsername())) != null) {
            return false;
        }
        user.setSalt(MD5Util.getSalt());
        user.setPassword(MD5Util.encryptBySalt(user.getPassword(), user.getSalt()));
        userMapper.insert(user);
        return true;
    }
}
