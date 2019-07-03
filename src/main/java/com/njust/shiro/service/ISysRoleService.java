package com.njust.shiro.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.njust.shiro.entity.PageBean;
import com.njust.shiro.entity.SysRole;
import com.njust.shiro.entity.SysUser;
import com.njust.shiro.entity.common.Result;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * <p>
 * 角色表 服务类
 * </p>
 *
 * @author 修身 since 2019/05/30
 */
public interface ISysRoleService extends IService<SysRole> {

    String listOfPage(SysRole role, PageBean<SysUser> pageBean);

    Map<String, Set<String>> selectResourceMapByUserId(int userId);

    Object selectTree();

    void updateRoleResource(int id, String resourceIds);

    List<Integer> selectResourceIdListByRoleId(int id);

    Result deleteRoleById(int id);

}
