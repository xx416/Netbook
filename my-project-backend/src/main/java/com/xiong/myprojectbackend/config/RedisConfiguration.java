package com.xiong.myprojectbackend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;

/**
 * @author 10371
 * @Description
 * @create 2023/10/21 02:09:01
 */
@Configuration
public class RedisConfiguration {
    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory connectionFactory){
        // key   采用StringRedisSerializer
        // value 采用GenericJackson2JsonRedisSerializer
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        // 关闭启用默认配置
        template.setEnableDefaultSerializer(false);
        // 连接工厂
        template.setConnectionFactory(connectionFactory);
        // key 序列化方式
        template.setKeySerializer(RedisSerializer.string());
        // value 序列化方式
        template.setValueSerializer(RedisSerializer.json());
        // hash key 序列化方式
        template.setHashKeySerializer(RedisSerializer.string());
        // hash value 序列化方式
        template.setHashValueSerializer(RedisSerializer.json());
        // 配置完成
        template.afterPropertiesSet();
        return template;
    }
}
