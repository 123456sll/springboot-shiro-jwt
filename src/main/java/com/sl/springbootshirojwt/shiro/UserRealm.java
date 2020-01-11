package com.sl.springbootshirojwt.shiro;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.sl.springbootshirojwt.entity.Permission;
import com.sl.springbootshirojwt.entity.Role;
import com.sl.springbootshirojwt.entity.User;
import com.sl.springbootshirojwt.service.IPermissionService;
import com.sl.springbootshirojwt.service.IRoleService;
import com.sl.springbootshirojwt.service.IUserService;
import com.sl.springbootshirojwt.util.JWTUtil;
import com.sl.springbootshirojwt.util.MD5Util;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class UserRealm extends AuthorizingRealm {

    @Autowired
    private IUserService userService;

    @Autowired
    private IRoleService roleService;

    @Autowired
    private IPermissionService permissionService;

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        List<Role> roles = roleService.getUserRoles(user.getId());
        /*Integer userId = JWTUtil.getUserId(principalCollection.toString());
        List<Role> roles = roleService.getUserRoles(userId);*/
        for (Role r : roles) {
            simpleAuthorizationInfo.addRole(r.getName());
            List<Permission> permissions = permissionService.getRolePermissions(r.getId());
            for (Permission p : permissions) {
                simpleAuthorizationInfo.addStringPermission(p.getPerCode());
            }
        }

        return simpleAuthorizationInfo;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        String username = (String) token.getPrincipal();
        String password = new String((char[]) token.getCredentials());
        User user = userService.getOne(new QueryWrapper<User>().eq("username", username));
        if (user == null) {
            throw new UnknownAccountException();
        }
        if (!user.getPassword().equals(MD5Util.encryptBySalt(password, user.getSalt()))) {
            throw new IncorrectCredentialsException();
        }
        return new SimpleAuthenticationInfo(user, password, getName());
    }
}
