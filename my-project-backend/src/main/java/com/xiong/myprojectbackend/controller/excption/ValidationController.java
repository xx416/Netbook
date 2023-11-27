package com.xiong.myprojectbackend.controller.excption;

import com.xiong.myprojectbackend.entity.RestBean;
import jakarta.validation.ValidationException;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.internal.constraintvalidators.hv.LengthValidator;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author 10371
 * @Description
 * @create 2023/10/9 19:49:27
 */
@Slf4j
@RestControllerAdvice
public class ValidationController {

    @ExceptionHandler(ValidationException.class)
    public RestBean<Void> validateException(ValidationException exception){
        log.warn("Resolve [{}: {}]", exception.getClass().getName(), exception.getMessage());
        return RestBean.failure(400,"请求参数有误");
    }
}
