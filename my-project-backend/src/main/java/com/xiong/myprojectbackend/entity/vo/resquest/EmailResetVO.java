package com.xiong.myprojectbackend.entity.vo.resquest;

import jakarta.validation.constraints.Email;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

/**
 * @author 10371
 * @Description
 * @create 2023/10/11 00:03:39
 */
@Data
public class EmailResetVO {
    @Email
    String email;
    @Length(max = 6, min = 6)
    String code;
    @Length(min = 6, max = 20)
    String password;

}
