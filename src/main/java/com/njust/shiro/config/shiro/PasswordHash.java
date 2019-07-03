package com.njust.shiro.config.shiro;

import com.njust.shiro.util.DigestUtils;
import lombok.Data;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

/**
 * shiro密码加密配置
 */

@Data
@Component
public class PasswordHash implements InitializingBean {
    private static String algorithmName = "MD5";
    private static int hashIterations = 2;

    @Override
    public void afterPropertiesSet() throws Exception {
        //Assert.hasLength(algorithmName, "algorithmName mast be MD5、SHA-1、SHA-256、SHA-384、SHA-512");
    }

    public static String toHex(Object source, Object salt) {
        return DigestUtils.hashByShiro(algorithmName, source, salt, hashIterations);
    }

    public static void main(String[] args) {
        System.out.println(toHex("test", "test"));
        System.out.println(toHex("lyw123456", "test"));
    }

}
