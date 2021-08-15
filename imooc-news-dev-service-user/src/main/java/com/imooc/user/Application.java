package com.imooc.user;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import tk.mybatis.spring.annotation.MapperScan;

/**
 * @author: shiwenwei
 * @date: 2021/5/30 1:01 AM
 * @since v1.0
 */
@SpringBootApplication
@MapperScan(basePackages = "com.imooc.user.mapper")
@ComponentScan(value = {"com.imooc", "org.n3r.idworker"})
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}
