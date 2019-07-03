package com.njust.shiro.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.njust.shiro.config.shiro.ShiroUser;
import com.njust.shiro.entity.SysResource;
import com.njust.shiro.mapper.SysResourceMapper;
import com.njust.shiro.mapper.SysRoleMapper;
import com.njust.shiro.mapper.SysUserRoleMapper;
import com.njust.shiro.service.ISysResourceService;
import com.njust.shiro.entity.common.Tree;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * <p>
 * 资源表 服务实现类
 * </p>
 *
 * @author 修身 since 2019/05/30
 */

@Service
@AllArgsConstructor
public class SysResourceServiceImpl extends ServiceImpl<SysResourceMapper, SysResource> implements ISysResourceService {

    private static final int RESOURCE_MENU = 0; // 菜单

    private final SysResourceMapper sysResourceMapper;
    private final SysUserRoleMapper sysUserRoleMapper;
    private final SysRoleMapper sysRoleMapper;

    private static List<Tree> treeList(List<SysResource> sysResourceLists) {
        List<Tree> trees = new ArrayList<>();
        for (SysResource sysResource : sysResourceLists) {
            Tree tree = new Tree();
            tree.setId(sysResource.getId());
            tree.setPid(sysResource.getPid());
            tree.setText(sysResource.getResourceName());
            tree.setIconCls(sysResource.getIcon());
            tree.setAttributes(sysResource.getUrl());
            tree.setOpenMode(sysResource.getOpenMode());
            tree.setState(sysResource.getOpened());
            trees.add(tree);
        }
        return trees;
    }

    @Override
    public List<SysResource> selectAll() {
        return selectAllByStatus(null);
    }

    private List<SysResource> selectAllByStatus(Integer status) {
        SysResource sysResource = new SysResource();
        if (status != null) {
            sysResource.setStatus(status);
        }
        QueryWrapper<SysResource> wrapper = new QueryWrapper<>(sysResource);
        wrapper.orderByAsc("order_num");
        return sysResourceMapper.selectList(wrapper);
    }

    private List<SysResource> selectByType(Integer type) {
        SysResource sysResource = new SysResource();
        sysResource.setResourceType(type);
        sysResource.setStatus(1);
        QueryWrapper<SysResource> wrapper = new QueryWrapper<>(sysResource);
        wrapper.orderByAsc("order_num");
        return sysResourceMapper.selectList(wrapper);
    }

    @Override
    public List<Tree> selectAllMenu() {
        List<Tree> trees = new ArrayList<>();
        // 查询所有菜单
        List<SysResource> sysResources = this.selectByType(RESOURCE_MENU);
        if (sysResources == null) {
            return trees;
        }
        for (SysResource sysResource : sysResources) {
            Tree tree = new Tree();
            tree.setId(sysResource.getId());
            tree.setPid(sysResource.getPid());
            tree.setText(sysResource.getResourceName());
            tree.setIconCls(sysResource.getIcon());
            tree.setAttributes(sysResource.getUrl());
            tree.setState(sysResource.getOpened());
            trees.add(tree);
        }
        return trees;
    }

    @Override
    public List<Tree> selectAllTree() {
        // 获取所有的资源 tree形式，展示
        List<Tree> trees = new ArrayList<>();
        List<SysResource> sysResources = this.selectAllByStatus(1);
        if (sysResources == null) {
            return trees;
        }
        return treeList(sysResources);
    }

    @Override
    public List<Tree> selectTree(ShiroUser shiroUser) {
        List<Tree> trees = new ArrayList<>();
        // shiro中缓存的用户角色
        Set<String> roles = shiroUser.getRoles();
        if (roles == null) {
            return trees;
        }
        // 如果有超级管理员权限
        if (roles.contains("admin")) {
            List<SysResource> sysResourceList = this.selectByType(RESOURCE_MENU);
            if (sysResourceList == null) {
                return trees;
            }
            return treeList(sysResourceList);
        }
        // 普通用户
        List<Integer> roleIdList = sysUserRoleMapper.selectRoleIdListByUserId(shiroUser.getId());
        if (roleIdList == null) {
            return trees;
        }
        List<SysResource> sysResourceLists = sysRoleMapper.selectResourceListByRoleIdList(roleIdList);
        if (sysResourceLists == null) {
            return trees;
        }
        return treeList(sysResourceLists);
    }


}
