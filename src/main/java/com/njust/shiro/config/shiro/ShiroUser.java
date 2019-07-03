package com.njust.shiro.config.shiro;

import lombok.Data;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;

import java.io.Serializable;
import java.util.Set;

/**
 * 系统用户
 *
 * @author 修身 since 2019/5/24
 **/

@Data
public class ShiroUser implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer id;
    private String loginName;
    //private String password;
    //private String salt;
    public String userName;
    //是否管理员
    public boolean isAdmin;

    private Set<String> permissions;

    private Set<String> urlSet;
    private Set<String> roles;

    public ShiroUser(String loginName) {
        this.loginName = loginName;
    }

    public ShiroUser(Integer id, String loginName, String userName, Set<String> urlSet, Set<String> roles, Set<String> permissions) {
        this.id = id;
        this.loginName = loginName;
        this.userName = userName;
        this.urlSet = urlSet;
        this.roles = roles;
        this.permissions = permissions;
    }

    /**
     * 本函数输出将作为默认的<shiro:principal/>输出.
     */
    @Override
    public String toString() {
        return userName;
    }

    /**
     * 获取当前登录用户对象
     */
    public static ShiroUser getShiroUser() {
        return (ShiroUser) SecurityUtils.getSubject().getPrincipal();
    }

    public static String getCurrentHost() {
        return SecurityUtils.getSubject().getSession().getHost();
    }

    public static Subject getSecurity() {
        return SecurityUtils.getSubject();
    }

}
