package com.sl.springbootshirojwt.service;

import com.sl.springbootshirojwt.entity.Permission;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 资源表 服务类
 * </p>
 *
 * @author sl
 * @since 2020-01-05
 */
public interface IPermissionService extends IService<Permission> {

    List<Permission> getRolePermissions(Integer roleId);

}
