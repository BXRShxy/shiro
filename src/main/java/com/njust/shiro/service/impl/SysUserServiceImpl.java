package com.njust.shiro.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.njust.shiro.config.shiro.ShiroUser;
import com.njust.shiro.config.shiro.UserRealm;
import com.njust.shiro.entity.PageBean;
import com.njust.shiro.entity.SysUser;
import com.njust.shiro.entity.SysUserRole;
import com.njust.shiro.mapper.SysUserMapper;
import com.njust.shiro.mapper.SysUserRoleMapper;
import com.njust.shiro.service.ISysUserService;
import com.njust.shiro.util.BaseUtil;
import com.njust.shiro.config.shiro.PasswordHash;
import com.njust.shiro.entity.common.Result;
import com.njust.shiro.util.StringUtils;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import static com.njust.shiro.util.BaseUtil.renderError;
import static com.njust.shiro.util.BaseUtil.renderSuccess;

/**
 * <p>
 * 用户表 服务实现类
 * </p>
 *
 * @author 修身 since 2019/05/30
 */

@Service
@AllArgsConstructor
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements ISysUserService {

    private final SysUserMapper userMapper;
    private final SysUserRoleMapper userRoleMapper;
    private final UserRealm userRealm;

    @Override
    public String listOfPage(SysUser user, PageBean<SysUser> pageBean) {
        try {
            Map<String, Object> map = new HashMap<>(5);
            map.put("userName", user.getUserName());
            map.put("deptId", user.getDeptId());
            //查询记录总数
            pageBean.setTotal(userMapper.totalOfList(map));
            map.put("start", pageBean.getStart());
            map.put("end", pageBean.getEnd());
            map.put("sort", pageBean.getSort());
            map.put("order", pageBean.getOrder());
            //查询总记录结果
            pageBean.setResult(userMapper.listOfPage(map));
            JSONObject result = new JSONObject();
            result.put("rows", pageBean.getResult());
            result.put("total", pageBean.getTotal());
            return JSON.toJSONString(result);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public SysUser selectSysUserById(int id) {
        return userMapper.selectSysUserById(id);
    }

    @Override
    public Result saveUser(SysUser user) {
        try {
            if (StringUtils.isNull(user)) {
                return renderError("没有数据，请填写数据！");
            }
            QueryWrapper<SysUser> wrapper = new QueryWrapper<>();
            wrapper.eq("login_name", user.getLoginName());
            if (null != user.getId()) {
                wrapper.eq("id", user.getId());
            }
            List<SysUser> list = userMapper.selectList(wrapper);
            if (list != null && !list.isEmpty()) {
                return renderError("登录名已存在!");
            }
            String salt = BaseUtil.generateUUID();
            String pwd = PasswordHash.toHex(user.getPassword(), salt);
            user.setSalt(salt);
            user.setPassword(pwd);
            userMapper.insert(user);
            //插入roles
            insertRoles(user);
            return renderSuccess("添加成功！");
        } catch (Exception e) {
            e.printStackTrace();
            return renderError("添加失败！请稍后重试");
        }
    }

    @Override
    public Result editUser(SysUser user) {
        try {
            QueryWrapper<SysUser> wrapper = new QueryWrapper<>();
            if (null != user.getId()) {
                wrapper.eq("id", user.getId());
            }
            wrapper.eq("login_name", user.getLoginName());
            wrapper.eq("user_name", user.getUserName());
            wrapper.eq("user_type", user.getUserType());
            wrapper.eq("dept_id", user.getDeptId());
            wrapper.eq("phone", user.getPhone());
            wrapper.eq("age", user.getAge());
            wrapper.eq("sex", user.getSex());
            wrapper.eq("status", user.getStatus());
            wrapper.eq("remark", user.getRemark());
            List<SysUser> list = userMapper.selectList(wrapper);
            String roleIds = user.getRoleIds();
            List list1 = new LinkedList();
            if (roleIds != null && !"".equals(roleIds)) {
                String[] roles = roleIds.split(",");
                Map<String, Object> map = new HashMap<>();
                map.put("user_id", user.getId());
                List<SysUserRole> userRoleList1 = userRoleMapper.selectByMap(map);
                int size = roles.length > userRoleList1.size() ? roles.length : userRoleList1.size();
                for (String roleId : roles) {
                    map.put("role_id", roleId);
                    List<SysUserRole> userRoleList2 = userRoleMapper.selectByMap(map);
                    if (userRoleList2 != null && userRoleList2.size() > 0) {
                        list1.add(userRoleList2);
                    }
                }
                if (list1.size() == size && list != null && !list.isEmpty()) {
                    return renderError("登录名已存在!");
                }
            }
            // 更新密码
            if (StringUtils.isNotBlank(user.getPassword())) {
                SysUser sysUser = userMapper.selectById(user.getId());
                String salt = sysUser.getSalt();
                String pwd = PasswordHash.toHex(user.getPassword(), salt);
                user.setPassword(pwd);
            } else {
                user.setPassword(null);
            }
            //先删除，再添加，比较野蛮
            List<SysUserRole> userRoles = userRoleMapper.selectByUserId(user.getId());
            if (userRoles != null && !userRoles.isEmpty()) {
                for (SysUserRole userRole : userRoles) {
                    userRoleMapper.deleteById(userRole.getId());
                }
            }
            //更新roles
            insertRoles(user);
            userMapper.updateById(user);
            return renderSuccess("修改成功！");
        } catch (Exception e) {
            e.printStackTrace();
            return renderError("修改失败！请稍后重试");
        }
    }

    private void insertRoles(SysUser user) {
        String[] roles = user.getRoleIds().split(",");
        SysUserRole userRole = new SysUserRole();
        for (String string : roles) {
            userRole.setUserId(user.getId());
            userRole.setRoleId(Integer.valueOf(string));
            userRoleMapper.insert(userRole);
        }
    }

    @Override
    public Result editUserPwd(String oldPwd, String pwd) {
        int currentUserId = ShiroUser.getShiroUser().getId();
        SysUser user = userMapper.selectById(currentUserId);
        String salt = user.getSalt();
        if (!user.getPassword().equals(PasswordHash.toHex(oldPwd, salt))) {
            return renderError("老密码不正确!");
        }
        // 修改密码时清理用户的缓存
        userRealm.removeUserCache(user.getLoginName());
        user.setPassword(PasswordHash.toHex(pwd, salt));
        userMapper.updateById(user);
        return renderSuccess("密码修改成功！");
    }
}
