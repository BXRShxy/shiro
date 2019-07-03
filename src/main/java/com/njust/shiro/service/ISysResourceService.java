package com.njust.shiro.service;

import com.njust.shiro.config.shiro.ShiroUser;
import com.njust.shiro.entity.SysResource;
import com.baomidou.mybatisplus.extension.service.IService;
import com.njust.shiro.entity.common.Tree;

import java.util.List;

/**
 * <p>
 * 资源表 服务类
 * </p>
 *
 * @author 修身 since 2019/05/30
 */
public interface ISysResourceService extends IService<SysResource> {

    List<SysResource> selectAll();

    List<Tree> selectAllMenu();

    List<Tree> selectAllTree();

    List<Tree> selectTree(ShiroUser shiroUser);

}
