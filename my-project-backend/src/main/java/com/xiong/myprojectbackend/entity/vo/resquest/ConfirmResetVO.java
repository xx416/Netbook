package com.xiong.myprojectbackend.entity.vo.resquest;

import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

/**
 * @author 10371
 * @Description
 * @create 2023/10/11 00:00:31
 */
@Data
@AllArgsConstructor
public class ConfirmResetVO {
    @Email
    String email;
    @Length(max = 6, min = 6)
    String code;
}
