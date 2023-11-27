package com.xiong.myprojectbackend.config;

import com.xiong.myprojectbackend.Utils.JwtUtil;
import com.xiong.myprojectbackend.entity.RestBean;
import com.xiong.myprojectbackend.entity.dto.DbUser;
import com.xiong.myprojectbackend.entity.vo.response.AuthorizeVO;
import com.xiong.myprojectbackend.filter.JwtAuthorizeFilter;
import com.xiong.myprojectbackend.service.DbUserService;
import jakarta.annotation.Resource;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.BeanUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;
import java.io.PrintWriter;

/**
 * @author 10371
 * @Description
 * @create 2023/9/25 20:46:35
 */
@Configuration
public class SecurityConfiguration {

    @Resource
    JwtUtil jwtUtil;
    @Resource
    JwtAuthorizeFilter jwtAuthorizeFilter;

    @Resource
    DbUserService dbUserService;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                .authorizeHttpRequests(conf -> conf
                        .requestMatchers(
                                "/images/head/**",
                                "/images/cover/**",
                                "/api/auth/**",
                                "/error",
                                "/api/book/getPageList",
                                "/api/book/getPagination",
                                "/api/book/getBookById",
                                "/api/book/getTagList",
                                "/api/book/getBookByName",
                                "/api/news/getList",
                                "/api/user/getListById").permitAll()
                        .anyRequest().authenticated()
                )

                .formLogin(conf -> conf
                        .loginProcessingUrl("/api/auth/login")
                        .failureHandler(this::onAuthenticationFailure)
                        .successHandler(this::onAuthenticationSuccess)
                )
                .logout(conf -> conf
                        .logoutUrl("/api/auth/logout")
                        .logoutSuccessHandler(this::onLogoutSuccess)
                )
                .exceptionHandling(conf -> conf
                        .authenticationEntryPoint(this::onUnauthorized)
                        .accessDeniedHandler(this::onAccessDeny)
                )
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(conf -> conf
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .addFilterBefore(jwtAuthorizeFilter, UsernamePasswordAuthenticationFilter.class)
                .build();

    }

    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {
        response.setContentType("application/json");
        response.setCharacterEncoding("utf-8");
        User user = (User)authentication.getPrincipal();
        DbUser dbUser = dbUserService.findAccountByNameOrEmail(user.getUsername());//查询用户数据
        String token = jwtUtil.createToken(user, dbUser.getUserId(), dbUser.getUsername());//生成ToKen
        AuthorizeVO vo = new AuthorizeVO();
        vo.setExpire(jwtUtil.expireTime());
        BeanUtils.copyProperties(dbUser,vo);
        vo.setToken(token);
        response.getWriter().write(RestBean.success(vo).asJsonString());
    }

    public void onAuthenticationFailure(HttpServletRequest request,
                                        HttpServletResponse response,
                                        AuthenticationException exception) throws IOException, ServletException {
        response.setContentType("application/json");
        response.setCharacterEncoding("utf-8");
        response.getWriter().write(RestBean.unauthorized(exception.getMessage()).asJsonString());
    }

    public void onLogoutSuccess(HttpServletRequest request,
                                HttpServletResponse response,
                                Authentication authentication) throws IOException, ServletException {
        response.setContentType("application/json");
        response.setCharacterEncoding("utf-8");
        PrintWriter writer = response.getWriter();
        String authorize = request.getHeader("Authorization");
        if (jwtUtil.invalidateJwt(authorize)){
            writer.write(RestBean.success().asJsonString());
        }else{
            writer.write(RestBean.failure(400,"退出登录失败").asJsonString());
        }
    }

    public void onAccessDeny(HttpServletRequest request,
                             HttpServletResponse response,
                             AccessDeniedException accessDeniedException) throws IOException, ServletException {
        response.setContentType("application/json");
        response.getWriter().write(RestBean.forbidden(accessDeniedException.getMessage()).asJsonString());
    }

    public void onUnauthorized(HttpServletRequest request,
                               HttpServletResponse response,
                               AuthenticationException exception) throws IOException, ServletException{
        response.setContentType("application/json");
        response.getWriter().write(RestBean.unauthorized(exception.getMessage()).asJsonString());
    }

}
