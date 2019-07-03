package com.njust.shiro.mapper;

import com.njust.shiro.entity.SysResource;
import com.njust.shiro.entity.SysRole;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 角色表 Mapper 接口
 * </p>
 *
 * @author 修身 since 2019/05/30
 */
public interface SysRoleMapper extends BaseMapper<SysRole> {

    List<Integer> selectResourceIdListByRoleId(@Param("id") int id);

    List<SysResource> selectResourceListByRoleIdList(@Param("list") List<Integer> list);

    List<Map<String, String>> selectResourceListByRoleId(@Param("id") Integer id);

}
