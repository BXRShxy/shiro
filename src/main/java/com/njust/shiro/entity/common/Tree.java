package com.njust.shiro.entity.common;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 *
 *
 * @author 修身 since 2018/10/23
 **/

@Data
public class Tree implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer id;
    private String text;
    private String state = "open";// open,closed
    private boolean checked = false;
    private Object attributes;
    @JsonInclude(Include.NON_NULL)
    private List<Tree> children; // null不输出
    private String iconCls;
    private Integer pid;
    /**
     * ajax,iframe,
     */
    private String openMode;

    public void setState(Integer opened) {
        this.state = (null != opened && opened == 1) ? "open" : "closed";
    }

}
