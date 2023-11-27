package com.xiong.myprojectbackend.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.yulichang.toolkit.JoinWrappers;
import com.github.yulichang.wrapper.MPJAbstractWrapper;
import com.github.yulichang.wrapper.MPJLambdaWrapper;
import com.xiong.myprojectbackend.Utils.JwtUtil;
import com.xiong.myprojectbackend.Utils.RedisCache;
import com.xiong.myprojectbackend.entity.dto.DbNews;
import com.xiong.myprojectbackend.entity.dto.DbOrder;
import com.xiong.myprojectbackend.entity.dto.DbUser;
import com.xiong.myprojectbackend.entity.vo.response.PaginationVO;
import com.xiong.myprojectbackend.entity.vo.resquest.SelectPageVO;
import com.xiong.myprojectbackend.service.DbOrderService;
import com.xiong.myprojectbackend.mapper.DbOrderMapper;
import jakarta.annotation.Resource;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
* @author 10371
* @description 针对表【db_order】的数据库操作Service实现
* @createDate 2023-10-18 18:47:28
*/
@Service
public class DbOrderServiceImpl extends ServiceImpl<DbOrderMapper, DbOrder> implements DbOrderService {

    @Resource
    RedisCache redisCache;

    @Resource
    DbOrderMapper dbOrderMapper;

    @Resource
    AmqpTemplate amqpTemplate;

    @Override
    public boolean confirmOrder(DbOrder dbOrder) throws JsonProcessingException {
        //todo 校验入参和库存
        Map<String, Object> goodsMap = redisCache.getCacheMap("goods");//获取商品库存信息
        //同步锁
        synchronized (this){
            Integer stock = (Integer) goodsMap.get(dbOrder.getBookId().toString());

            if (stock == null) {
                throw new RuntimeException("商品为空");
            }
            if (stock == 0) {
                throw new RuntimeException("库存不足");
            }
            if (stock < dbOrder.getNum()) {
                throw new RuntimeException("库存不足");
            }

            //扣库存
            redisCache.setOneMap("goods", dbOrder.getBookId().toString(), stock - dbOrder.getNum());

            //将商品信息发送至MQ
            Map<String, Object> sendMessageMap = new HashMap<>();
            sendMessageMap.put("book_id",dbOrder.getBookId());
            sendMessageMap.put("stock",stock - dbOrder.getNum());
            sendMessageMap.put("order",dbOrder);

            amqpTemplate.convertAndSend("orderExchange", "orderMQ", sendMessageMap);
        }
        return false;
    }

    @Override
    public List<DbOrder> getPageList(SelectPageVO selectPageVO) {
        Page<DbOrder> producePage = new Page<>(selectPageVO.getCurrentPage(),selectPageVO.getPageSize());
        MPJLambdaWrapper<DbOrder> wrapper = JoinWrappers.lambda(DbOrder.class)
                .like("username",selectPageVO.getTagName())
                .selectAll(DbOrder.class)
                .select(DbUser::getUsername)
                .innerJoin(DbUser.class,DbUser::getUserId,DbOrder::getUserId);
        Page<DbOrder> page = dbOrderMapper.selectPage(producePage,wrapper);
        System.out.println(producePage == page);
        return page.getRecords();
    }


    @Override
    public PaginationVO getPagination(int pageSize) {
        int count = Math.toIntExact(dbOrderMapper.selectCount(new QueryWrapper<>()));
        double pageCount = (double) count / pageSize;
        if (pageCount != (int) pageCount){
            pageCount += 1;
        }
        return new PaginationVO(count,count, (int) pageCount);
    }

    @Override
    public List<DbOrder> getListByUid(int uid) {
        QueryWrapper<DbOrder> wrapper = new QueryWrapper<>();
        wrapper
                .eq("user_id",uid);
        return dbOrderMapper.selectList(wrapper);
    }

    @Override
    public boolean updateById(int id, String column, String setValue) {
        UpdateWrapper<DbOrder> wrapper = new UpdateWrapper<>();
        wrapper
                .eq("order_id",id)
                .set(column,setValue);
        int update = dbOrderMapper.update(new DbOrder(), wrapper);
        if (update>0){return true;}
        return false;
    }

    @Override
    public boolean updateById(int id, String column, int setValue) {
        UpdateWrapper<DbOrder> wrapper = new UpdateWrapper<>();
        wrapper
                .eq("order_id",id)
                .set(column,setValue);
        int update = dbOrderMapper.update(new DbOrder(), wrapper);
        if (update>0){return true;}
        return false;
    }

}




