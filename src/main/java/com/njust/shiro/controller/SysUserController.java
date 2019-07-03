package com.njust.shiro.controller;


import com.njust.shiro.config.shiro.ShiroUser;
import com.njust.shiro.entity.PageBean;
import com.njust.shiro.entity.SysRole;
import com.njust.shiro.entity.SysUser;
import com.njust.shiro.service.ISysUserService;
import com.njust.shiro.entity.common.Result;
import lombok.AllArgsConstructor;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

import static com.njust.shiro.util.BaseUtil.renderError;
import static com.njust.shiro.util.BaseUtil.renderSuccess;

/**
 * <p>
 * 用户表 前端控制器
 * </p>
 *
 * @author 修身 since 2019/05/30
 */
@RestController
@AllArgsConstructor
@RequestMapping("/user")
public class SysUserController {

    private final ISysUserService userService;

    /**
     * 进入user.html页面
     */
    @RequiresPermissions("sys:user:manager")
    @GetMapping("/manager")
    public ModelAndView manager() {
        return new ModelAndView("sys/user/user");
    }

    /**
     * 分页
     */
    @RequiresPermissions("sys:user:dataGrid")
    @RequestMapping("/dataGrid")
    public String dataGrid(SysUser user,
                           @RequestParam(value = "page", defaultValue = "1") int page,
                           @RequestParam(value = "rows", defaultValue = "10") int rows,
                           @RequestParam(value = "sort", defaultValue = "updateTime") String sort,
                           @RequestParam(value = "order", defaultValue = "desc") String order) {
        return userService.listOfPage(user, new PageBean<>(page, rows, sort, order));
    }

    /**
     * 进入添加页
     */
    @RequiresPermissions("sys:user:add")
    @GetMapping("/addPage")
    public ModelAndView addPage() {
        return new ModelAndView("sys/user/userAdd");
    }

    @PostMapping("/add")
    public Result add(@Valid SysUser user) {
        return userService.saveUser(user);
    }

    /**
     * 进入编辑页
     */
    @RequiresPermissions("sys:user:edit")
    @GetMapping("/editPage")
    public ModelAndView editPage(@RequestParam(value = "id") int id, Model model) {
        SysUser user = userService.selectSysUserById(id);
        List<SysRole> rolesList = user.getRolesList();
        List<Integer> ids = new ArrayList<>();
        for (SysRole role : rolesList) {
            ids.add(role.getId());
        }
        model.addAttribute("roleIds", ids);
        model.addAttribute("user", user);
        return new ModelAndView("sys/user/userEdit");
    }

    /**
     * 编辑用户
     */
    @PostMapping("/edit")
    public Result edit(@Valid SysUser user) {
        return userService.editUser(user);
    }

    /**
     * 进入修改密码页
     */
    @GetMapping("/editPwdPage")
    public ModelAndView editPwdPage() {
        return new ModelAndView("sys/user/userEditPwd");
    }

    /**
     * 修改密码
     */
    @PostMapping("/editUserPwd")
    public Result editUserPwd(String oldPwd, String pwd) {
        return userService.editUserPwd(oldPwd, pwd);
    }

    /**
     * 删除用户
     * 注：仅超级管理员有此权限
     */
    @RequiresRoles("admin")
    @RequiresPermissions("sys:user:delete")
    @PostMapping("/delete")
    public Result delete(int id) {
        int currentUserId = ShiroUser.getShiroUser().getId();
        if (id == currentUserId) {
            return renderError("不可以删除自己！");
        }
        return userService.removeById(id) ? renderSuccess("修改成功！") : renderError("修改失败！请稍后重试");
    }

}
