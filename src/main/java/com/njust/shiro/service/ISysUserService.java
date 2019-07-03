package com.njust.shiro.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.njust.shiro.entity.PageBean;
import com.njust.shiro.entity.SysUser;
import com.njust.shiro.entity.common.Result;

/**
 * <p>
 * 用户表 服务类
 * </p>
 *
 * @author 修身 since 2019/05/30
 */
public interface ISysUserService extends IService<SysUser> {

    String listOfPage(SysUser user, PageBean<SysUser> pageBean);

    SysUser selectSysUserById(int id);

    Result saveUser(SysUser user);

    Result editUser(SysUser user);

    Result editUserPwd(String oldPwd, String pwd);

}
