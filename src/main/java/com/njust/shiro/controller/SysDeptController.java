package com.njust.shiro.controller;


import com.njust.shiro.entity.SysDept;
import com.njust.shiro.service.ISysDeptService;
import com.njust.shiro.entity.common.Result;
import com.njust.shiro.entity.common.Tree;
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
 * 组织表 前端控制器
 * </p>
 *
 * @author 修身 since 2019/05/30
 */
@RestController
@AllArgsConstructor
@RequestMapping("/dept")
public class SysDeptController {

    private final ISysDeptService deptService;

    /**
     * 进入dept.html页面
     */
    @RequiresPermissions("sys:dept:manager")
    @GetMapping("/manager")
    public ModelAndView manager() {
        return new ModelAndView("sys/dept/dept");
    }

    /**
     * 部门树
     */
    @PostMapping("/tree")
    public List<Tree> tree() {
        return deptService.selectTree();
    }

    /**
     * 部门列表
     */
    @RequiresPermissions("sys:dept:treeGrid")
    @PostMapping("/treeGrid")
    public List<SysDept> treeGrid() {
        return deptService.selectTreeGrid();
    }

    /**
     * 添加部门页
     */
    @RequiresPermissions("sys:dept:add")
    @GetMapping("/addPage")
    public ModelAndView addPage() {
        return new ModelAndView("sys/dept/deptAdd");
    }

    /**
     * 添加部门
     */
    @PostMapping("/add")
    public Result add(@Valid SysDept dept) {
        return deptService.save(dept) ? renderSuccess("添加成功！") : renderError("添加失败！请稍后重试");
    }

    /**
     * 编辑部门页
     */
    @RequiresPermissions("sys:dept:edit")
    @GetMapping("/editPage")
    public ModelAndView editPage(Model model, Integer id) {
        SysDept dept = deptService.getById(id);
        model.addAttribute("dept", dept);
        return new ModelAndView("sys/dept/deptEdit");
    }

    /**
     * 编辑部门
     */
    @RequestMapping("/edit")
    public Result edit(@Valid SysDept dept) {
        return deptService.updateById(dept) ? renderSuccess("编辑成功！") : renderError("编辑失败！请稍后重试");
    }

    /**
     * 删除部门
     */
    @RequiresPermissions("sys:dept:delete")
    @RequestMapping("/delete")
    public Result delete(Integer id) {
        return deptService.deleteDeptById(id);
    }
}
