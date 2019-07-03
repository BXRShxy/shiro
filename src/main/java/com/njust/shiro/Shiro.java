package com.njust.shiro;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * shiro工程启动类
 *
 * @author 修身 created on 2019/5/23 19:32
 **/

@SpringBootApplication
@EnableSwagger2
@MapperScan("com.njust.shiro.mapper")
public class Shiro {

    public static void main(String[] args) {
        SpringApplication.run(Shiro.class, args);
    }

}
