package com.njust.shiro.service;

import com.njust.shiro.entity.SysDept;
import com.baomidou.mybatisplus.extension.service.IService;
import com.njust.shiro.entity.common.Result;
import com.njust.shiro.entity.common.Tree;

import java.util.List;

/**
 * <p>
 * 组织表 服务类
 * </p>
 *
 * @author 修身 since 2019/05/30
 */
public interface ISysDeptService extends IService<SysDept> {

    List<Tree> selectTree();

    List<SysDept> selectTreeGrid();

    Result deleteDeptById(Integer id);

}
