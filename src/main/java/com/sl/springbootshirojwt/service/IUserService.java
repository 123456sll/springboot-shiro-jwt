package com.sl.springbootshirojwt.service;

import com.sl.springbootshirojwt.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 用户表 服务类
 * </p>
 *
 * @author sl
 * @since 2020-01-05
 */
public interface IUserService extends IService<User> {

    boolean register(User user);

}
