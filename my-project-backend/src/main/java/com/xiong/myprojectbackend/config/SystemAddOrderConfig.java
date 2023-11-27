package com.xiong.myprojectbackend.config;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.xiong.myprojectbackend.Utils.RedisCache;
import com.xiong.myprojectbackend.entity.po.DbBook;
import com.xiong.myprojectbackend.service.DbBookService;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 10371
 * @Description
 * @create 2023/10/20 19:40:45
 */
@Component
@Slf4j
public class SystemAddOrderConfig {
    @Resource
    RedisCache redisCache;

    @Resource
    DbBookService dbBookService;

    @PostConstruct
    public void addOrder() {
        redisCache.deleteObject("goods");//删除redis中的商品库存信息

        int integer = Math.toIntExact(dbBookService.count(new QueryWrapper<>()));//获取商品数量

        if (integer != 0) {
            List<DbBook> goodsList = dbBookService.getStock();//获取商品库存信息
            Map<String, Integer> goodMap = new HashMap<>();
            goodsList.forEach(c->{
                //将数据库的商品库存信息放入redis，存储形式Map： goods:商品id:库存数量
                goodMap.put(String.valueOf(c.getBookId()), c.getStock());
            });
            redisCache.setCacheMap("goods",goodMap);
            return;
        }
        log.info("有缓存商品库存");
    }
}
