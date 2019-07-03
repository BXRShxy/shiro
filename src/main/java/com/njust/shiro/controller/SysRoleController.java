package com.njust.shiro.controller;


import com.njust.shiro.entity.PageBean;
import com.njust.shiro.entity.SysRole;
import com.njust.shiro.service.ISysRoleService;
import com.njust.shiro.entity.common.Result;
import lombok.AllArgsConstructor;
import org.apache.shiro.authz.UnauthorizedException;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.List;

import static com.njust.shiro.util.BaseUtil.renderError;
import static com.njust.shiro.util.BaseUtil.renderSuccess;

/**
 * <p>
 * 角色表 前端控制器
 * </p>
 *
 * @author 修身 since 2019/05/30
 */
@RestController
@AllArgsConstructor
@RequestMapping("/role")
public class SysRoleController {

    private final ISysRoleService roleService;

    /**
     * 进入role.html页面
     */
    @RequiresPermissions("sys:role:manager")
    @GetMapping("/manager")
    public ModelAndView manager() {
        return new ModelAndView("sys/role/role");
    }

    /**
     * 权限列表
     */
    @RequiresPermissions("sys:role:dataGrid")
    @PostMapping("/dataGrid")
    public Object dataGrid(SysRole role,
                           @RequestParam(value = "page", defaultValue = "1") int page,
                           @RequestParam(value = "rows", defaultValue = "10") int rows,
                           @RequestParam(value = "sort", defaultValue = "updateTime") String sort,
                           @RequestParam(value = "order", defaultValue = "desc") String order) {
        return roleService.listOfPage(role, new PageBean<>(page, rows, sort, order));
    }

    /**
     * 权限树
     */
    @PostMapping("/tree")
    public Object tree() {
        return roleService.selectTree();
    }

    /**
     * 进入添加权限页
     */
    @RequiresPermissions("sys:role:add")
    @GetMapping("/addPage")
    public ModelAndView addPage() {
        return new ModelAndView("sys/role/roleAdd");
    }

    /**
     * 添加权限
     */
    @PostMapping("/add")
    public Result add(@Valid SysRole role) {
        return roleService.save(role) ? renderSuccess("添加成功！") : renderError("添加失败！请稍后重试");
    }

    /**
     * 编辑权限页
     */
    @RequiresPermissions("sys:role:edit")
    @RequestMapping("/editPage")
    public ModelAndView editPage(Model model, Long id) {
        SysRole role = roleService.getById(id);
        model.addAttribute("role", role);
        return new ModelAndView("sys/role/roleEdit");
    }

    /**
     * 编辑权限
     */
    @RequestMapping("/edit")
    public Object edit(@Valid SysRole role) {
        return roleService.updateById(role) ? renderSuccess("编辑成功！") : renderError("编辑失败！请稍后重试");
    }

    /**
     * 删除权限
     */
    @RequiresPermissions("sys:role:delete")
    @RequestMapping("/delete")
    public Result delete(int id) {
        return roleService.deleteRoleById(id);
    }

    /**
     * 授权页面
     */
    @RequiresRoles("admin")
    @RequiresPermissions("sys:role:grant")
    @GetMapping("/grantPage")
    public ModelAndView grantPage(Model model, Long id) {
        model.addAttribute("id", id);
        return new ModelAndView("sys/role/roleGrant");
    }

    /**
     * 授权页面根据角色查询资源
     */
    @RequestMapping("/findResourceIdListByRoleId")
    public Result findResourceByRoleId(Integer id) {
        List<Integer> resources = roleService.selectResourceIdListByRoleId(id);
        return renderSuccess(resources);
    }

    /**
     * 授权
     */
    @RequiresRoles("admin")
    @RequestMapping("/grant")
    public Result grant(Integer id, String resourceIds) throws UnauthorizedException {
        roleService.updateRoleResource(id, resourceIds);
        return renderSuccess("授权成功！");
    }

}
