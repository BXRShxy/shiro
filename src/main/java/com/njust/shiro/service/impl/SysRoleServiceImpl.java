package com.njust.shiro.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.njust.shiro.entity.*;
import com.njust.shiro.entity.common.Result;
import com.njust.shiro.entity.common.Tree;
import com.njust.shiro.mapper.SysRoleMapper;
import com.njust.shiro.mapper.SysRoleResourceMapper;
import com.njust.shiro.mapper.SysUserMapper;
import com.njust.shiro.mapper.SysUserRoleMapper;
import com.njust.shiro.service.ISysRoleService;
import com.njust.shiro.util.BaseUtil;
import lombok.AllArgsConstructor;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import java.util.*;

import static com.njust.shiro.util.BaseUtil.upperCharToUnderLine;

/**
 * <p>
 * 角色表 服务实现类
 * </p>
 *
 * @author 修身 since 2019/05/30
 */

@Service
@AllArgsConstructor
public class SysRoleServiceImpl extends ServiceImpl<SysRoleMapper, SysRole> implements ISysRoleService {

    private final SysRoleMapper roleMapper;
    private final SysUserMapper userMapper;
    private final SysUserRoleMapper userRoleMapper;
    private final SysRoleResourceMapper roleResourceMapper;

    @Override
    public String listOfPage(SysRole role, PageBean<SysUser> pageBean) {
        Page<SysRole> page = new Page<>(pageBean.getCurrPage(), pageBean.getPageSize());
//        List<Map<String, Object>> list = userMapper.selectUserPage(page, pageInfo.getCondition());
        QueryWrapper<SysRole> wrapper = new QueryWrapper<>();
        if (role.getRoleName() != null) {
            wrapper.like("role_name", role.getRoleName());
        }
        wrapper.orderBy(true, "asc".equals(pageBean.getOrder()), upperCharToUnderLine(pageBean.getSort()));
        IPage<SysRole> rolePage = roleMapper.selectPage(page, wrapper);
        JSONObject result = new JSONObject();
        result.put("rows", rolePage.getRecords());
        result.put("total", rolePage.getSize());
        return JSON.toJSONString(result);
    }

    @Override
    public Map<String, Set<String>> selectResourceMapByUserId(int userId) {
        Map<String, Set<String>> resourceMap = new HashMap<>();
        List<Integer> roleIdList = userRoleMapper.selectRoleIdListByUserId(userId);
        Set<String> urlSet = new HashSet<>();
        Set<String> roles = new HashSet<>();
        Set<String> permissions = new HashSet<>();
        for (Integer roleId : roleIdList) {
            List<Map<String, String>> resourceList = roleMapper.selectResourceListByRoleId(roleId);
            if (resourceList != null && !resourceList.isEmpty()) {
                for (Map<String, String> map : resourceList) {
                    if (map != null && StringUtils.isNotBlank(map.get("url"))) {
                        urlSet.add(map.get("url"));
                        permissions.add(map.get("permission"));
                    }
                }
            }
            SysRole sysRole = roleMapper.selectById(roleId);
            if (sysRole != null) {
                roles.add(sysRole.getRoleName());
            }
        }
        resourceMap.put("urls", urlSet);
        resourceMap.put("roles", roles);
        resourceMap.put("permissions", permissions);
        return resourceMap;
    }

    public List<SysRole> selectAll() {
        QueryWrapper<SysRole> wrapper = new QueryWrapper<>();
        wrapper.orderByAsc("order_num");
        return roleMapper.selectList(wrapper);
    }

    @Override
    public Object selectTree() {
        List<Tree> trees = new ArrayList<>();
        List<SysRole> roles = this.selectAll();
        for (SysRole role : roles) {
            Tree tree = new Tree();
            tree.setId(role.getId());
            tree.setText(role.getRoleName());

            trees.add(tree);
        }
        return trees;
    }

    @Override
    public void updateRoleResource(int roleId, String resourceIds) {
        // 先删除后添加,有点爆力
        SysRoleResource roleResource = new SysRoleResource();
        roleResource.setRoleId(roleId);
        roleResourceMapper.delete(new QueryWrapper<>(roleResource));

        // 如果资源id为空，判断为清空角色资源
        if (StringUtils.isBlank(resourceIds)) {
            return;
        }

        String[] resourceIdArray = resourceIds.split(",");
        for (String resourceId : resourceIdArray) {
            roleResource = new SysRoleResource();
            roleResource.setRoleId(roleId);
            roleResource.setResourceId(Integer.parseInt(resourceId));
            roleResourceMapper.insert(roleResource);
        }
    }

    @Override
    public List<Integer> selectResourceIdListByRoleId(int id) {
        return roleMapper.selectResourceIdListByRoleId(id);
    }

    @Override
    public Result deleteRoleById(int id) {
        try {
            //先判断角色是否被用户绑定，若存在，不得删除。
            QueryWrapper<SysUserRole> wrapper = new QueryWrapper<>();
            wrapper.eq("role_id", id);
            List<SysUserRole> userRoleList = userRoleMapper.selectList(wrapper);
            if (userRoleList != null && userRoleList.size() > 0) {
                return BaseUtil.renderError("当前角色已被用户绑定，无法删除！请先将相关人员删除或调至其他角色。");
            } else {
                roleMapper.deleteById(id);
            }
            return BaseUtil.renderSuccess("删除成功！");
        } catch (Exception e) {
            e.printStackTrace();
            return BaseUtil.renderError("删除失败！请稍后重试");
        }
    }


}
