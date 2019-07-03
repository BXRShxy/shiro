package com.njust.shiro.config.shiro;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * ShiroFilterMapFactory
 *
 * @author 修身 since 2019/5/23
 */

class ShiroFilterMapFactory {

    /**
     * anon:例子/admins/**=anon 没有参数，表示可以匿名使用。
     * <p>
     * authc:例如/admins/user/**=authc表示需要认证(登录)才能使用，没有参数
     * <p>
     * roles(角色)：例子/admins/user/**=roles[admin],参数可以写多个，多个时必须加上引号，并且参数之间用逗号分割，
     * 当有多个参数时，例如admins/user/**=roles["admin,guest"],每个参数通过才算通过，相当于hasAllRoles()方法。
     * <p>
     * perms（权限）：例子/admins/user/**=perms[user:add:*],参数可以写多个，多个时必须加上引号，并且参数之间用逗号分割，
     * 例如/admins/user/**=perms["user:add:*,user:modify:*"]，当有多个参数时必须每个参数都通过才通过，想当于isPermitedAll()方法。
     * <p>
     * rest：例子/admins/user/**=rest[user],根据请求的方法，相当于/admins/user/**=perms[user:method] ,其中method为post，get，delete等。
     * <p>
     * port：例子/admins/user/**=port[8081],当请求的url的端口不是8081是跳转到schemal://serverName:8081?queryString,其中schmal是协议http或https等，
     * serverName是你访问的host,8081是url配置里port的端口，queryString是你访问的url里的？后面的参数。
     * <p>
     * authcBasic：例如/admins/user/**=authcBasic没有参数表示httpBasic认证
     * <p>
     * ssl:例子/admins/user/**=ssl没有参数，表示安全的url请求，协议为https
     * <p>
     * user:例如/admins/user/**=user没有参数表示必须存在用户，当登入操作时不做检查
     */

    // 配置访问权限 最好是LinkedHashMap，因为它必须保证有序
    // 过滤链定义，从上向下顺序执行，一般将 /**放在最为下边 --> : 这是一个坑，一不小心代码就不好使了

    /**
     * 过滤规则（注意优先级）
     * —anon 无需认证(登录)可访问
     * —authc 必须认证才可访问
     * —perms[标识] 拥有资源权限才可访问
     * —role 拥有角色权限才可访问
     * —user 已认证和自动登录可访问
     */
    static Map<String, String> shiroFilterMap() {
        //设置路径映射，注意这里要用LinkedHashMap 保证有序
        LinkedHashMap<String, String> filterChainDefinitionMap = new LinkedHashMap<>();
        //对所有用户认证
        filterChainDefinitionMap.put("/login", "anon");
        filterChainDefinitionMap.put("/unauthorized", "anon");
        filterChainDefinitionMap.put("/logout", "logout");
        filterChainDefinitionMap.put("/static/**", "anon");
        //放验证码
        filterChainDefinitionMap.put("/captcha/captchaImage**", "anon");
        //释放 druid 监控画面
        filterChainDefinitionMap.put("/druid/**", "anon");
        //释放websocket请求
        //filterChainDefinitionMap.put("/websocket", "anon");

        //对所有页面进行认证
        filterChainDefinitionMap.put("/**", "authc");
        return filterChainDefinitionMap;
    }
}
