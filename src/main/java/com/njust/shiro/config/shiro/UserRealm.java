package com.njust.shiro.config.shiro;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.njust.shiro.entity.SysUser;
import com.njust.shiro.service.ISysRoleService;
import com.njust.shiro.service.ISysUserService;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.authz.UnauthorizedException;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.SimplePrincipalCollection;

import javax.annotation.Resource;
import java.util.Map;
import java.util.Set;

/**
 * 身份校验核心类
 *
 * @author 修身 since 2019/5/24
 **/

public class UserRealm extends AuthorizingRealm {

    @Resource
    private ISysUserService userService;
    @Resource
    private ISysRoleService roleService;

    /**
     * 授权查询回调函数, 进行鉴权但缓存中无用户的授权信息时调用.
     **/
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) throws UnauthorizedException {
        if (principals == null) {
            throw new AuthorizationException("principals should not be null");
        }
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        ShiroUser user = (ShiroUser) principals.getPrimaryPrincipal();
        System.out.println(user.getUserName() + "进行授权操作");
        info.setRoles(user.getRoles());
        info.setStringPermissions(user.getPermissions());
        return info;
    }

    /**
     * 身份认证
     **/
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authcToken) throws AuthenticationException {
        //加这一步的目的是在Post请求的时候会先进认证，然后再到请求
        if (authcToken.getPrincipal() == null) {
            throw new RuntimeException("登录失败，请联系管理员！");
        }
        //1. 把 AuthenticationToken 转换为 UsernamePasswordToken
        UsernamePasswordToken token = (UsernamePasswordToken) authcToken;
        //2. 从 UsernamePasswordToken 中来获取 username
        String userName = token.getUsername();
        System.out.println(userName + "进行身份认证");
        //3. 调用数据库的方法, 从数据库中查询 username 对应的用户记录
        QueryWrapper<SysUser> wrapper = new QueryWrapper<>();
        wrapper.eq("login_name", userName);
        SysUser sysUser = userService.getOne(wrapper);
        if (sysUser == null) {
            throw new UnknownAccountException("账号或密码不正确");
        }
        //根据当前用户id获取用户的权限信息
        Map<String, Set<String>> resourceMap = roleService.selectResourceMapByUserId(sysUser.getId());
        Set<String> urls = resourceMap.get("urls");
        Set<String> roles = resourceMap.get("roles");
        Set<String> permissions = resourceMap.get("permissions");
        ShiroUser shiroUser = new ShiroUser(sysUser.getId(), sysUser.getLoginName(), sysUser.getUserName(), urls, roles, permissions);
        return new SimpleAuthenticationInfo(shiroUser, sysUser.getPassword().toCharArray(), ShiroByteSource.of(sysUser.getSalt()), getName());
    }

    /**
     * 清除用户缓存
     */
    public void removeUserCache(String loginName) {
        SimplePrincipalCollection principals = new SimplePrincipalCollection();
        ShiroUser shiroUser = new ShiroUser(loginName);
        principals.add(shiroUser, super.getName());
        super.clearCache(principals);
    }
}
