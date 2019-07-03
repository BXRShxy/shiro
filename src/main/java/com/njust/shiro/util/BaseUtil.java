package com.njust.shiro.util;


import com.google.common.base.CaseFormat;
import com.njust.shiro.entity.common.Result;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.Serializable;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.*;

/**
 * 基础工具类
 *
 * @author 修身 since 2018/10/23
 **/

public abstract class BaseUtil {

    /**
     * 利用UUID生成随机的32位字符串
     **/
    public static String generateUUID() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }

    /**
     * ajax成功
     *
     * @return {Object}
     */
    public static Result renderSuccess() {
        Result result = new Result();
        result.setSuccess(true);
        return result;
    }

    /**
     * ajax失败
     *
     * @param msg 失败的消息
     * @return {Object}
     */
    public static Result renderError(String msg) {
        Result result = new Result();
        result.setMsg(msg);
        return result;
    }

    /**
     * ajax成功
     *
     * @param msg 消息
     * @return {Object}
     */
    public static Result renderSuccess(String msg) {
        Result result = new Result();
        result.setSuccess(true);
        result.setMsg(msg);
        return result;
    }

    /**
     * ajax成功
     *
     * @param obj 成功时的对象
     * @return {Object}
     */
    public static Result renderSuccess(Object obj) {
        Result result = new Result();
        result.setSuccess(true);
        result.setObj(obj);
        return result;
    }

    /**
     * redirect跳转
     *
     * @param url 目标url
     */
    protected String redirect(String url) {
        return new StringBuilder("redirect:").append(url).toString();
    }

    /**
     * 下载文件
     *
     * @param file 文件
     */
    protected ResponseEntity<Resource> download(File file) {
        String fileName = file.getName();
        return download(file, fileName);
    }

    /**
     * 下载
     *
     * @param file     文件
     * @param fileName 生成的文件名
     * @return {ResponseEntity}
     */
    protected ResponseEntity<Resource> download(File file, String fileName) {
        Resource resource = new FileSystemResource(file);

        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
                .getRequestAttributes()).getRequest();
        String header = request.getHeader("LcbUser-Agent");
        // 避免空指针
        header = header == null ? "" : header.toUpperCase();
        HttpStatus status;
        if (header.contains("MSIE") || header.contains("TRIDENT") || header.contains("EDGE")) {
            fileName = URLUtils.encodeURL(fileName, Charsets.UTF_8);
            status = HttpStatus.OK;
        } else {
            fileName = new String(fileName.getBytes(Charsets.UTF_8), Charsets.ISO_8859_1);
            status = HttpStatus.CREATED;
        }
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        headers.setContentDispositionFormData("attachment", fileName);
        return new ResponseEntity<>(resource, headers, status);
    }

    /**
     * 获取删除Id集合 Integer型
     *
     * @param ids
     */
    public List<? extends Serializable> getIdList(String ids) {
        List<Integer> idList = new ArrayList<>();
        for (String s : ids.split(",")) {
            idList.add(Integer.parseInt(s));
        }
        return idList;
    }

    /**
     * 获取删除Id集合 String型
     *
     * @param ids String型Id集合
     */
    public Collection<? extends Serializable> getIds(String ids) {
        String[] str = ids.split(",");
        return Arrays.asList(str);
    }

    /**
     * 获取参数值
     *
     * @param key
     * @param defaultValue
     * @return
     */
    public Object getParam(String key, Object defaultValue) {
        ActionContext actionContext = ActionContext.getContext();
        return actionContext.getParam(key, defaultValue);
    }

    /**
     * 获取真实路径
     *
     * @param path 路径
     * @return String    真实路径
     */
    public String getRealPath(String path) {
        ActionContext actionContext = ActionContext.getContext();
        return actionContext.getRealPath(path);
    }

    /**
     * 获取真实路径
     *
     * @param path
     * @return
     */
    public String getRealPathByClass(String path) {
        String projectPath = null;
        //windows下
        if ("\\".equals(File.separator)) {
            projectPath = this.getClass().getClassLoader().getResource("/").getPath().substring(1);
        } else {
            projectPath = this.getClass().getClassLoader().getResource("/").getPath();
        }
        projectPath += path;
        return projectPath;
    }

    /**
     * 十进制数转度数
     *
     * @param gpsValue GPS值
     * @return String
     **/
    public String strToDouble(String gpsValue) {
        Integer gpsValue1 = Integer.parseInt(gpsValue);
        Integer a1 = (gpsValue1 / 100000);
        Integer b1 = (gpsValue1 % 100000);
        Double c1 = ((double) b1 / 1000) / 60;
        Double fValue = a1 + c1;
        NumberFormat formatter = new DecimalFormat("0.000000");
        return formatter.format(fValue);
    }

    /**
     * 驼峰法大写字母转下划线
     */
    public static String upperCharToUnderLine(String param) {
        return CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, param);
    }
}
