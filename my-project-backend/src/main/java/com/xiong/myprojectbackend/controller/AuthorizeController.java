package com.xiong.myprojectbackend.controller;

import com.xiong.myprojectbackend.entity.RestBean;
import com.xiong.myprojectbackend.entity.vo.resquest.ConfirmResetVO;
import com.xiong.myprojectbackend.entity.vo.resquest.EmailRegisterVO;
import com.xiong.myprojectbackend.entity.vo.resquest.EmailResetVO;
import com.xiong.myprojectbackend.service.DbUserService;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.function.Supplier;

/**
 * @author 10371
 * @Description
 * @create 2023/10/8 16:38:06
 */
@Validated
@RestController
@RequestMapping("/api/auth")
public class AuthorizeController {

    @Resource
    DbUserService dbUserService;

    @GetMapping("/ask-code")
    public RestBean<Void> askVerifyCode(@RequestParam @Email String email,
                                        @RequestParam @Pattern(regexp = "(register|reset)") String type,
                                        HttpServletRequest request){
        return this.messageHandle(() ->
                dbUserService.registerEmailVerifCode(type,email,request.getRemoteAddr()));
    }

    @PostMapping("/register")
    public RestBean<Void> register(@RequestBody @Valid EmailRegisterVO vo){
        return this.messageHandle(() -> dbUserService.registerEmailAccount(vo));
    }

    @PostMapping("/reset-confirm")
    public RestBean<Void> resetConfirm(@RequestBody @Valid ConfirmResetVO confirmResetVO){
        return this.messageHandle(() -> dbUserService.resetConfirm(confirmResetVO));
    }

    @PostMapping("/reset-password")
    public RestBean<Void> resetPassword(@RequestBody @Valid EmailResetVO emailResetVO){
        return this.messageHandle(() -> dbUserService.resetEmailAccountPassword(emailResetVO));
    }

    private RestBean<Void> messageHandle(Supplier<String> action){
        String message = action.get();
        return message == null ? RestBean.success() : RestBean.failure(400,message);
    }
}
