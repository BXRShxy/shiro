package com.njust.shiro.controller;


import com.njust.shiro.config.shiro.ShiroUser;
import com.njust.shiro.entity.SysResource;
import com.njust.shiro.entity.common.Tree;
import com.njust.shiro.service.ISysResourceService;
import com.njust.shiro.entity.common.Result;
import lombok.AllArgsConstructor;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

import java.util.List;

import static com.njust.shiro.util.BaseUtil.renderError;
import static com.njust.shiro.util.BaseUtil.renderSuccess;

/**
 * <p>
 * 资源表 前端控制器
 * </p>
 *
 * @author 修身 since 2019/05/30
 */

@RestController
@AllArgsConstructor
@RequestMapping("/resource")
public class SysResourceController {

    private final ISysResourceService resourceService;

    /**
     * 进入resource.html页面
     */
    @RequiresPermissions("sys:resource:manager")
    @GetMapping("/manager")
    public ModelAndView manager() {
        return new ModelAndView("sys/resource/resource");
    }

    /**
     * 菜单树
     */
    @PostMapping("/tree")
    public List<Tree> tree() {
        ShiroUser shiroUser = ShiroUser.getShiroUser();
        return resourceService.selectTree(shiroUser);
    }

    /**
     * 资源管理列表
     */
    @RequiresPermissions("sys:resource:treeGrid")
    @PostMapping("/treeGrid")
    public Object treeGrid() {
        return resourceService.selectAll();
    }

    /**
     * 添加资源页
     */
    @RequiresPermissions("sys:resource:add")
    @GetMapping("/addPage")
    public ModelAndView addPage(Model model, Integer pid) {
        model.addAttribute("pid", pid);
        return new ModelAndView("sys/resource/resourceAdd");
    }

    /**
     * 添加资源
     */
    @PostMapping("/add")
    public Result add(@Valid SysResource resource) {
        // 选择菜单时将openMode设置为null
        Integer type = resource.getResourceType();
        if (null != type && type == 0) {
            resource.setOpenMode(null);
        }
        return resourceService.save(resource) ? renderSuccess("添加成功！") : renderError("添加失败！请稍后重试");
    }

    /**
     * 查询所有的菜单
     */
    @PostMapping("/allTree")
    public Object allMenu() {
        return resourceService.selectAllMenu();
    }

    /**
     * 查询所有的资源tree
     */
    @PostMapping("/allTrees")
    public Object allTree() {
        return resourceService.selectAllTree();
    }

    /**
     * 编辑资源页
     */
    @RequiresPermissions("sys:resource:edit")
    @GetMapping("/editPage")
    public ModelAndView editPage(Model model, Integer id) {
        SysResource resource = resourceService.getById(id);
        model.addAttribute("resource", resource);
        return new ModelAndView("sys/resource/resourceEdit");
    }

    /**
     * 编辑资源
     */
    @PostMapping("/edit")
    public Result edit(@Valid SysResource resource) {
        return resourceService.updateById(resource) ? renderSuccess("编辑成功！") : renderError("编辑失败！请稍后重试");
    }

    /**
     * 删除资源
     */
    @RequiresPermissions("sys:resource:delete")
    @PostMapping("/delete")
    public Result delete(Integer id) {
        return resourceService.removeById(id) ? renderSuccess("删除成功！") : renderError("删除失败！请稍后重试");
    }

}
