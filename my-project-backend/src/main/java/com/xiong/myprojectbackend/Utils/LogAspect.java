package com.xiong.myprojectbackend.Utils;

import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.xiong.myprojectbackend.entity.dto.DbSyslog;
import com.xiong.myprojectbackend.service.DbSyslogService;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.HttpRequestHandler;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.StringJoiner;

/**
 * @author 10371
 * @Description
 * @create 2023/10/21 20:31:30
 */
@Aspect
@Slf4j
@Component
public class LogAspect {

    @Resource
    DbSyslogService dbSyslogService;

    @Resource
    JwtUtil jwtUtil;

    @Resource
    HttpServletRequest request;

    private Logger logger = LoggerFactory.getLogger(this.getClass());
    ThreadLocal<Long> startTime = new ThreadLocal<>();
    //定义切点
    @Pointcut("execution(* com.xiong.myprojectbackend.controller.*.*(..))")
    public void cutController(){
    }

    @Pointcut(value = "cutController()")
    public void aopWebLog() {
    }
    //使用环绕通知
    @Around("aopWebLog()")
    public Object myLogger(ProceedingJoinPoint pjp) throws Throwable {
        String username = "";
        startTime.set(System.currentTimeMillis());
        //使用ServletRequestAttributes请求上下文获取方法更多
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();

        DecodedJWT jwt = jwtUtil.getTokenInfo(request.getHeader("Authorization"));//获取JWT信息
        if (jwt == null){
            username = "未登录";
        }else{
            Map<String, Claim> claims = jwt.getClaims();
            username = claims.get("name").asString();//获取用户名
        }

        String className = pjp.getSignature().getDeclaringTypeName();
        String methodName = pjp.getSignature().getName();
        //使用数组来获取参数
        Object[] array = pjp.getArgs();
        ObjectMapper mapper = new ObjectMapper();
        //执行函数前打印日志
        log.info("URL:{}", request.getRequestURL().toString());
        log.info("IP地址：{}", request.getRemoteAddr());
        //调用整个目标函数执行
        Object obj = pjp.proceed();
        //执行函数后打印日志
        logger.info("耗时：{}ms", System.currentTimeMillis() - startTime.get());
        //录入日志表
        Date date = new Date();
        SimpleDateFormat dateFormat= new SimpleDateFormat("yyyy-MM-dd :hh:mm:ss");
        DbSyslog dbSyslog = new DbSyslog();
        String url = request.getRequestURL().toString();
        String temp = url.substring(0,url.lastIndexOf("/"));
        String moduleName = "/api"+temp.substring(temp.lastIndexOf("/"));
        String actionName = url.substring(url.lastIndexOf("/"));
        dbSyslog.setModuleName(moduleName);
        dbSyslog.setActionName(actionName);
        dbSyslog.setIp(request.getRemoteAddr());
        dbSyslog.setAddTime(dateFormat.format(date));
        if (!(moduleName.equals("/api/log") || moduleName.equals("/api/file") || moduleName.equals("/api/addr") || moduleName.equals("/api/card") || actionName.equals("/getUserByToken") || actionName.equals("/getListByUid") || actionName.equals("/confirmOrder"))){
            dbSyslog.setMessage(obj.toString());
            dbSyslog.setRequest(mapper.writeValueAsString(array));
        }
        dbSyslog.setUsername(username);
        dbSyslogService.save(dbSyslog);
        return obj;
    }
}
