package com.xiong.myprojectbackend.Utils;

import jakarta.annotation.Resource;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * @author 10371
 * @Description
 * @create 2023/10/8 15:55:30
 */
@Component
public class FlowUtils {

    @Resource
    StringRedisTemplate stringRedisTemplate;

    public boolean limitOnceCheck(String key, int blockTime){
        if (Boolean.TRUE.equals(stringRedisTemplate.hasKey(key))){
            return false;
        } else {
            stringRedisTemplate.opsForValue().set(key, "", blockTime, TimeUnit.SECONDS);
            return true;
        }
    }

}
