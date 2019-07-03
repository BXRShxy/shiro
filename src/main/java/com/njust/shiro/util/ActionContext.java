package com.njust.shiro.util;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

/**
 * 请求上下文
 *
 * @author 修身 since 2018/10/23
 **/

public class ActionContext<T> {

    public static final String USER_ID = "USER_ID";

    /**
     * 线程变量
     */
    private static ThreadLocal<ActionContext> local = new ThreadLocal();

    /**
     * 请求
     */
    private HttpServletRequest request = null;

    /**
     * 响应
     */
    private HttpServletResponse response;

    /**
     * 参数
     */
    private Map<String, Object> dataMap;

    /**
     * 私有构造
     */
    private ActionContext() {
        this.dataMap = new HashMap<>();
    }


    /**
     * 获取上下文
     */
    public static ActionContext getContext() {
        ActionContext context = local.get();
        if (context == null) {
            context = new ActionContext();
            local.set(context);
        }
        return context;
    }

    /**
     * 初始化上下文
     */
    public void init(HttpServletRequest request, HttpServletResponse response) {
        this.request = request;
        this.response = response;

        Enumeration pNames = request.getParameterNames();
        while (pNames.hasMoreElements()) {
            String name = (String) pNames.nextElement();
            if (request.getParameter(name) != null) {
                this.dataMap.put(name, request.getParameter(name));
            }
        }
    }

    /**
     * 设置参数值
     */
    public void setData(String key, Object value) {
        this.dataMap.put(key, value);
    }


    /**
     * 获取参数值
     */
    public Object getParam(String key) {
        return getParam(key, null);
    }


    /**
     * 获取参数值
     */
    public String getString(String key) {
        return request.getParameter(key);
    }

    /**
     * 获取参数值
     */
    public Object getParam(String key, Object defaultValue) {
        Object value = this.dataMap.get(key);
        if (value == null) {
            return defaultValue;
        }
        return value;
    }

    /**
     * 销毁
     */
    public void destory() {
        local.remove();
    }


    /**
     * 获取当前页
     */
    public int getCurrentPage() {
        int currentPage = 1;
        try {
            currentPage = TextUtil.getInt(request.getParameter("page"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return currentPage;
    }

    /**
     * 获取分页大小
     */
    public int getPageSize() {
        int pageSize = 20;
        try {
            pageSize = TextUtil.getInt(request.getParameter("rows"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return pageSize;
    }

    /**
     * 获取偏移位置
     */
    public int getOffset() {
        return (getCurrentPage() - 1) * getPageSize();
    }

    /**
     * 获取分页对象
     */
    public Page<T> getPage() {
        return new Page<>(getCurrentPage(), getPageSize());
    }

    /**
     * 获取参数集合
     */
    public Map<String, Object> getDataMap() {
        return this.dataMap;
    }

    /**
     * 获取真实路径
     *
     * @param path 路径
     * @return String    真实路径
     */
    public String getRealPath(String path) {
        return request.getSession().getServletContext().getRealPath(path);
    }

}
