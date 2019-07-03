package com.njust.shiro.entity.common;

import lombok.Data;

import java.io.Serializable;

/**
 * 操作结果集
 *
 * @author 修身 since 2018/10/23
 **/

@Data
public class Result implements Serializable {

    public static final int SUCCESS = 1;
    public static final int FAILURE = -1;

    private static final long serialVersionUID = 5576237395711742681L;

    private boolean success = false;

    private String msg = "";

    private Object obj = null;

}
