package com.sl.springbootshirojwt.service.impl;

import com.sl.springbootshirojwt.entity.Permission;
import com.sl.springbootshirojwt.mapper.PermissionMapper;
import com.sl.springbootshirojwt.service.IPermissionService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 资源表 服务实现类
 * </p>
 *
 * @author sl
 * @since 2020-01-05
 */
@Service
public class PermissionServiceImpl extends ServiceImpl<PermissionMapper, Permission> implements IPermissionService {

    @Autowired
    private PermissionMapper permissionMapper;

    @Override
    public List<Permission> getRolePermissions(Integer roleId) {
        return permissionMapper.selectRolePermissions(roleId);
    }
}
