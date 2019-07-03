package com.njust.shiro.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 资源表
 * </p>
 *
 * @author 修身 since 2019/05/30
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("sys_resource")
@ApiModel(value="Resource对象", description="资源表")
public class SysResource implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "权限")
    @TableField("permission")
    private String permission;

    @ApiModelProperty(value = "资源名称")
    @TableField("resource_name")
    private String resourceName;

    @ApiModelProperty(value = "资源路径")
    @TableField("url")
    private String url;

    @ApiModelProperty(value = "打开方式 ajax,iframe")
    @TableField("open_mode")
    private String openMode;

    @ApiModelProperty(value = "资源介绍")
    @TableField("description")
    private String description;

    @ApiModelProperty(value = "资源图标")
    @TableField("icon")
    private String icon;

    @ApiModelProperty(value = "父级资源id")
    @TableField("pid")
    private Integer pid;

    @ApiModelProperty(value = "打开状态")
    @TableField("opened")
    private Integer opened;

    @ApiModelProperty(value = "资源类别")
    @TableField("resource_type")
    private Integer resourceType;

    @ApiModelProperty(value = "状态")
    @TableField("status")
    private Integer status;

    @ApiModelProperty(value = "排序")
    @TableField("order_num")
    private Integer orderNum;

    @ApiModelProperty(value = "备注")
    @TableField("remark")
    private String remark;

    @ApiModelProperty(value = "更新时间")
    @TableField("update_time")
    private LocalDateTime updateTime;

    @ApiModelProperty(value = "创建时间")
    @TableField("create_time")
    private LocalDateTime createTime;


}
