package com.xiong.myprojectbackend;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xiong.myprojectbackend.entity.dto.DbBook;
import com.xiong.myprojectbackend.service.DbAddrService;
import com.xiong.myprojectbackend.service.DbBookService;
import com.xiong.myprojectbackend.service.DbOrderService;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Arrays;
import java.util.List;

@SpringBootTest
class MyProjectBackendApplicationTests {

    @Resource
    BCryptPasswordEncoder passwordEncoder;

    @Test
    void contextLoads() {
        passwordEncoder.encode("123456");
    }
}
