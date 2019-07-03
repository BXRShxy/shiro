package com.njust.shiro.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.njust.shiro.entity.SysDept;
import com.njust.shiro.entity.SysUser;
import com.njust.shiro.mapper.SysDeptMapper;
import com.njust.shiro.mapper.SysUserMapper;
import com.njust.shiro.service.ISysDeptService;
import com.njust.shiro.util.BaseUtil;
import com.njust.shiro.entity.common.Result;
import com.njust.shiro.entity.common.Tree;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 组织表 服务实现类
 * </p>
 *
 * @author 修身 since 2019/05/30
 */
@Service
@AllArgsConstructor
public class SysDeptServiceImpl extends ServiceImpl<SysDeptMapper, SysDept> implements ISysDeptService {

    private final SysDeptMapper deptMapper;
    private final SysUserMapper userMapper;

    @Override
    public List<Tree> selectTree() {
        List<SysDept> deptList = selectTreeGrid();
        List<Tree> trees = new ArrayList<>();
        if (deptList != null) {
            for (SysDept dept : deptList) {
                Tree tree = new Tree();
                tree.setId(dept.getId());
                tree.setText(dept.getDeptName());
                tree.setIconCls(dept.getIcon());
                tree.setPid(dept.getPid());
                trees.add(tree);
            }
        }
        return trees;
    }

    @Override
    public List<SysDept> selectTreeGrid() {
        QueryWrapper<SysDept> wrapper = new QueryWrapper<>();
        wrapper.orderByAsc("order_num");
        return deptMapper.selectList(wrapper);
    }

    @Override
    public Result deleteDeptById(Integer id) {
        try {
            //先删除子部门，需判断用户表中是否存在将删除部门之下，若存在，不得删除。
            QueryWrapper<SysDept> wrapper = new QueryWrapper<>();
            wrapper.eq("pid", id);
            List<SysDept> deptList = deptMapper.selectList(wrapper);
            Map<String, Object> map = new HashMap<>();
            if (deptList != null) {
                for (SysDept dept : deptList) {
                    map.put("dept_id", dept.getId());
                    List<SysUser> userList1 = userMapper.selectByMap(map);
                    if (userList1 != null && userList1.size() > 0) {
                        return BaseUtil.renderError(dept.getDeptName() + "部门下尚存有人员，无法删除！请先将相关人员删除或调至其他部门。");
                    } else {
                        deptMapper.deleteById(dept.getId());
                    }
                }
            }
            //最后删除当前节点的部门
            map.put("dept_id", id);
            List<SysUser> userList2 = userMapper.selectByMap(map);
            if (userList2 != null && userList2.size() > 0) {
                return BaseUtil.renderError(deptMapper.selectById(id).getDeptName() + "部门下尚存有人员，无法删除！请先将相关人员删除或调至其他部门。");
            } else {
                deptMapper.deleteById(id);
            }
            return BaseUtil.renderSuccess("删除成功！");
        } catch (Exception e) {
            e.printStackTrace();
            return BaseUtil.renderError("删除失败！请稍后重试");
        }
    }

}
