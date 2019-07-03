package com.njust.shiro.service.impl;

import com.njust.shiro.entity.SysRoleResource;
import com.njust.shiro.mapper.SysRoleResourceMapper;
import com.njust.shiro.service.ISysRoleResourceService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 角色-资源关联 服务实现类
 * </p>
 *
 * @author 修身 since 2019/05/30
 */
@Service
@AllArgsConstructor
public class SysRoleResourceServiceImpl extends ServiceImpl<SysRoleResourceMapper, SysRoleResource> implements ISysRoleResourceService {

}
