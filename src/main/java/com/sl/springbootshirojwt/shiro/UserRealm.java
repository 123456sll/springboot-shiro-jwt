package com.sl.springbootshirojwt.shiro;

import com.auth0.jwt.exceptions.TokenExpiredException;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.sl.springbootshirojwt.entity.Permission;
import com.sl.springbootshirojwt.entity.Role;
import com.sl.springbootshirojwt.entity.User;
import com.sl.springbootshirojwt.service.IPermissionService;
import com.sl.springbootshirojwt.service.IRoleService;
import com.sl.springbootshirojwt.service.IUserService;
import com.sl.springbootshirojwt.shiro.jwt.JwtToken;
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
import java.util.Map;

public class UserRealm extends AuthorizingRealm {

    @Autowired
    private IUserService userService;

    @Autowired
    private IRoleService roleService;

    @Autowired
    private IPermissionService permissionService;



    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof JwtToken;
    }

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
        Integer userId = JWTUtil.getUserId(principalCollection.toString());
        List<Role> roles = roleService.getUserRoles(userId);
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
        String jwtToken = (String) token.getCredentials();
        Map<String, Object> map = JWTUtil.verifyToken(jwtToken);
        Exception exception = (Exception) map.get("exception");
        if (exception == null) {
            SimpleAuthenticationInfo simpleAuthenticationInfo = new SimpleAuthenticationInfo(jwtToken, jwtToken, "userRealm");
            return simpleAuthenticationInfo;
        }else  {
            throw new AuthenticationException("token 非法！");
        }
    }
}
