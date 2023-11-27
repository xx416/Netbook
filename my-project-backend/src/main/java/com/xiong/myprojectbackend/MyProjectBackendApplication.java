package com.xiong.myprojectbackend;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.xiong.myprojectbackend.mapper")
public class MyProjectBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(MyProjectBackendApplication.class, args);
    }

}
