package com.njust.shiro.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.util.List;

/**
 * 分页工具
 *
 * @author 修身 since 2018/10/23
 **/

@Data
public class PageBean<T> {

    /**
     * 总记录数
     **/
    private Long total;

    /**
     * 分页查询的结果
     **/
    private List<T> result = null;

    /**
     * 当前页数
     *
     * "@JsonIgnore" 一般标记在属性或者方法上，返回的json数据即不包含该属性
     **/
    @JsonIgnore
    private Integer currPage = 1;

    /**
     * 每页显示的个数
     **/
    @JsonIgnore
    private Integer pageSize = 10;

    /**
     * 起始页
     **/
    @JsonIgnore
    private Integer start = 1;

    /**
     * 末页
     **/
    @JsonIgnore
    private Integer end = 10;

    /**
     * 排序字段
     **/
    @JsonIgnore
    private String sort = "createTime";

    /**
     * 顺序 asc /倒数 desc
     **/
    @JsonIgnore
    private String order = "asc";

    public PageBean(int currPage, int pageSize) {
        this.currPage = currPage;
        this.pageSize = pageSize;
        this.start = (currPage - 1) * pageSize;
        if (start > 0) {
            start++;
        }
        this.end = currPage * pageSize;
        if (start > end) {
            Integer temp = this.end;
            this.end = start;
            this.start = temp;
        }
    }

    public PageBean(int currPage, int pageSize, String sort, String order) {
        this.currPage = currPage;
        this.pageSize = pageSize;
        this.start = (currPage - 1) * pageSize;
        this.end = currPage * pageSize - this.start;
        this.sort = sort;
        this.order = order;
    }
}