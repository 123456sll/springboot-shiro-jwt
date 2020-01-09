package com.sl.springbootshirojwt.service;

import com.sl.springbootshirojwt.entity.Role;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 角色表 服务类
 * </p>
 *
 * @author sl
 * @since 2020-01-05
 */
public interface IRoleService extends IService<Role> {

    List<Role> getUserRoles(Integer id);

}
