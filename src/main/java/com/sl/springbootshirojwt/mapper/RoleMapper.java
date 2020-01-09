package com.sl.springbootshirojwt.mapper;

import com.sl.springbootshirojwt.entity.Role;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 角色表 Mapper 接口
 * </p>
 *
 * @author sl
 * @since 2020-01-05
 */
public interface RoleMapper extends BaseMapper<Role> {

    List<Role> selectUserRoles(@Param("user_id") Integer userId);

}
