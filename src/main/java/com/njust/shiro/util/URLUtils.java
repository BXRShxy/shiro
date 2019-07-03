package com.njust.shiro.util;

import java.nio.charset.Charset;

/**
 * url处理工具类
 *
 * @author 修身 since 2018/10/23
 **/

public class URLUtils extends org.springframework.web.util.UriUtils {

    /**
     * url 编码
     *
     * @param source  url
     * @param charset 字符集
     * @return 编码后的url
     */
    public static String encodeURL(String source, Charset charset) {
        return URLUtils.encode(source, charset.name());
    }


}
