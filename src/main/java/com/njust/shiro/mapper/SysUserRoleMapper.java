package com.njust.shiro.mapper;

import com.njust.shiro.entity.SysUserRole;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 * 用户-角色关联 Mapper 接口
 * </p>
 *
 * @author 修身 since 2019/05/30
 */
public interface SysUserRoleMapper extends BaseMapper<SysUserRole> {

    @Select("select id, role_id, user_id from sys_user_role where user_id = #{userId}")
    List<SysUserRole> selectByUserId(@Param("userId") int userId);

    @Select("select role_id AS roleId from sys_user_role where user_id = #{userId}")
    List<Integer> selectRoleIdListByUserId(@Param("userId") int userId);

}
