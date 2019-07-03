package com.njust.shiro.config.shiro;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.njust.shiro.entity.SysRole;
import com.njust.shiro.entity.SysUser;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import java.io.Serializable;
import java.util.List;

/**
 * User表现层对象
 **/

@Data
public class UserVo implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer id;

    @Length(min = 1, max = 64)
    private String loginName;

    @JsonIgnore
    private String password;
    @JsonIgnore
    private String salt;
    private String userName;
    private boolean isAdmin;

    private String phone;

    private Integer userType;
    private Integer status;
    private List<SysRole> rolesList;
    private String roleIds;

    private String updateTime;
    private String createTime;

    private Integer deptId;
    private String deptName;

    /**
     * 比较vo和数据库中的用户是否同一个user，采用id比较
     *
     * @param sysUser 用户
     * @return 是否同一个人
     */
    public boolean equalsUser(SysUser sysUser) {
        if (sysUser == null) {
            return false;
        }
        Integer userId = sysUser.getId();
        if (id == null || userId == null) {
            return false;
        }
        return id.equals(userId);
    }

}