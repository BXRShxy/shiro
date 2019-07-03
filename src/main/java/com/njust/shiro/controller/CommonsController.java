package com.njust.shiro.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * 通用控制器
 *
 * @author 修身 since 2019/6/5
 */

@Controller
public class CommonsController {
//    @Autowired
//    private DreamCaptcha dreamCaptcha;

    /**
     * 图标页
     */
    @GetMapping("icons.html")
    public String icons() {
        return "common/icons";
    }

//    /**
//     * 图形验证码
//     */
//    @GetMapping("captcha.jpg")
//    public void captcha(HttpServletRequest request, HttpServletResponse response) {
//        dreamCaptcha.generate(request, response);
//    }

}
