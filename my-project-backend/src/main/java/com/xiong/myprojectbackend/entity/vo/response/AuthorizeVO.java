package com.xiong.myprojectbackend.entity.vo.response;

import lombok.Data;

import java.util.Date;

/**
 * @author 10371
 * @Description
 * @create 2023/9/26 17:01:15
 */
@Data
public class AuthorizeVO {
    String username;
    String role;
    String token;
    Date expire;
}
