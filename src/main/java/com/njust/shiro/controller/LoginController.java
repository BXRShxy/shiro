package com.njust.shiro.controller;

import com.njust.shiro.entity.common.Result;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.subject.Subject;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import static com.njust.shiro.util.BaseUtil.renderSuccess;

/**
 * 登录 控制器
 *
 * @author 修身 since 2019/5/23
 **/

@RestController
public class LoginController {

    @GetMapping("/login")
    public ModelAndView loginPage() {
        boolean isAu = SecurityUtils.getSubject().isAuthenticated();
        System.out.println("是否已认证：" + isAu);
        boolean isRem = SecurityUtils.getSubject().isRemembered();
        System.out.println("是否记住我：" + isRem);
        if (isAu || isRem) {
            return new ModelAndView("redirect:/index");
        }
        return new ModelAndView("login");
    }

    @PostMapping("/login")
    public Object checkLogin(@RequestParam(value = "loginName") String loginName,
                             @RequestParam(value = "password") String password,
                             @RequestParam(value = "rememberMe", defaultValue = "false") boolean rememberMe) {
        try {
            Subject subject = SecurityUtils.getSubject();
            UsernamePasswordToken token = new UsernamePasswordToken(loginName, password, rememberMe);
            subject.login(token);
        } catch (UnknownAccountException e) {
            e.printStackTrace();
            return "用户不存在，请重新输入！";
        } catch (IncorrectCredentialsException e) {
            e.printStackTrace();
            return "账号或密码不正确，请重新输入！";
        } catch (LockedAccountException e) {
            e.printStackTrace();
            return "当前用户已被锁定，不允许访问！";
        } catch (AuthenticationException e) {
            e.printStackTrace();
            return "账户验证失败，请稍后重试！";
        }
        return renderSuccess();
    }

    @GetMapping("/index")
    public ModelAndView index() {
        return new ModelAndView("index");
    }

    /**
     * 退出
     */
    @PostMapping("/logout")
    public Result logout() {
        Subject subject = SecurityUtils.getSubject();
        subject.logout();
        return renderSuccess();
    }

    /**
     * 未认证跳转
     */
    @GetMapping("/unauthorized")
    public ModelAndView unauthorized() {
        return new ModelAndView("error/403");
    }

}
