package com.sl.springbootshirojwt.mapper;

import com.sl.springbootshirojwt.entity.Permission;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 资源表 Mapper 接口
 * </p>
 *
 * @author sl
 * @since 2020-01-05
 */
public interface PermissionMapper extends BaseMapper<Permission> {

    List<Permission> selectRolePermissions(@Param("role_id") Integer roleId);

}
