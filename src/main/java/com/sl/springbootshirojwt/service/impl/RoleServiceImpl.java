package com.sl.springbootshirojwt.service.impl;

import com.sl.springbootshirojwt.entity.Role;
import com.sl.springbootshirojwt.mapper.RoleMapper;
import com.sl.springbootshirojwt.service.IRoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 角色表 服务实现类
 * </p>
 *
 * @author sl
 * @since 2020-01-05
 */
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements IRoleService {

    @Autowired
    private RoleMapper roleMapper;

    @Override
    public List<Role> getUserRoles(Integer userId) {
        return roleMapper.selectUserRoles(userId);
    }
}
