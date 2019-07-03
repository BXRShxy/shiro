package com.njust.shiro.service.impl;

import com.njust.shiro.entity.SysUserRole;
import com.njust.shiro.mapper.SysUserRoleMapper;
import com.njust.shiro.service.ISysUserRoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户-角色关联 服务实现类
 * </p>
 *
 * @author 修身 since 2019/05/30
 */
@Service
@AllArgsConstructor
public class SysUserRoleServiceImpl extends ServiceImpl<SysUserRoleMapper, SysUserRole> implements ISysUserRoleService {

}
