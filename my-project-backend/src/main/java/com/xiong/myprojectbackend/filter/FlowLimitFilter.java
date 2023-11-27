package com.xiong.myprojectbackend.filter;

import com.xiong.myprojectbackend.Utils.Const;
import com.xiong.myprojectbackend.entity.RestBean;
import jakarta.annotation.Resource;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.core.annotation.Order;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

/**
 * @author 10371
 * @Description
 * @create 2023/10/11 16:22:51
 */
@Order(Const.ORDER_LIMIT)
@Component
public class FlowLimitFilter extends HttpFilter {

    @Resource
    StringRedisTemplate stringRedisTemplate;

    @Override
    protected void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        String ip = request.getRemoteAddr();
        if (this.tryCount(ip)){
            chain.doFilter(request,response);
        } else {
            writeBlockMessage(response);
        }

    }

    private void writeBlockMessage(HttpServletResponse response) throws IOException {
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        response.setContentType("application/json;charset=utf-8");
        response.getWriter().write(RestBean.forbidden("操作频繁，请稍后再试").asJsonString());
    }

    private boolean tryCount(String ip){
        synchronized (ip.intern()){
            if (Boolean.TRUE.equals(stringRedisTemplate.hasKey(Const.FLOW_LIMIT_BLOCK + ip))) return false;
            return this.limitPeriodCheck(ip);
        }
    }

    private boolean limitPeriodCheck(String ip){//限制一秒访问次数超过30次的ip地址(冷却30s)
        if (Boolean.TRUE.equals(stringRedisTemplate.hasKey(Const.FLOW_LIMIT_COUNTER + ip))){
            Long increment = Optional.ofNullable(stringRedisTemplate.opsForValue().increment(Const.FLOW_LIMIT_COUNTER + ip)).orElse(0L);
            if (increment > 30){
                stringRedisTemplate.opsForValue().set(Const.FLOW_LIMIT_BLOCK + ip, "" , 30, TimeUnit.SECONDS);
                return false;
            }
        } else {
            stringRedisTemplate.opsForValue().set(Const.FLOW_LIMIT_COUNTER + ip, "1", 1, TimeUnit.SECONDS);
        }
        return true;
    }


}
