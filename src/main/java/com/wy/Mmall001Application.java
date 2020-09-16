package com.wy;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@SpringBootApplication
@MapperScan("com.wy.mapper")
@ServletComponentScan
public class Mmall001Application {

    public static void main(String[] args) {
        SpringApplication.run(Mmall001Application.class, args);
    }

}
