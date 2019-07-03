package com.njust.shiro.mapper;

import com.njust.shiro.entity.SysUser;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 用户表 Mapper 接口
 * </p>
 *
 * @author 修身 since 2019/05/30
 */
public interface SysUserMapper extends BaseMapper<SysUser> {

    /**
     * 查询总记录
     *
     * @param map 集合
     * @return List<SysUser>
     **/
    List<SysUser> listOfPage(Map<String, Object> map);

    /**
     * 查询总数
     *
     * @param map 集合
     * @return Long
     **/
    Long totalOfList(Map<String, Object> map);


    SysUser selectSysUserById(@Param("id") int id);
}
