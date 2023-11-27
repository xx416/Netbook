package com.xiong.myprojectbackend.listener;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.rabbitmq.client.Channel;
import com.xiong.myprojectbackend.entity.dto.DbBook;
import com.xiong.myprojectbackend.entity.dto.DbOrder;
import com.xiong.myprojectbackend.service.DbBookService;
import com.xiong.myprojectbackend.service.DbOrderService;
import jakarta.annotation.Resource;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Map;

/**
 * @author 10371
 * @Description
 * @create 2023/10/21 02:32:01
 */
@Component
@RabbitListener(queues = "order")
public class OrderQueueListener {

    @Resource
    DbBookService dbBookService;

    @Resource
    DbOrderService dbOrderService;

    @RabbitHandler
    public void review(Map<String, Object> msg, Message message, Channel channel) throws IOException {
        //todo 同步数据库
        DbOrder order = (DbOrder) msg.get("order");//获取订单信息
        DbBook one = dbBookService.getOne(new QueryWrapper<DbBook>().eq("book_id", order.getBookId()));
        UpdateWrapper<DbBook> wrapper = new UpdateWrapper<>();//配置条件
        wrapper
                .eq("book_id",msg.get("book_id"))
                .set("stock",msg.get("stock"))
                .set("sales",one.getSales() + order.getNum());

        dbBookService.update(new DbBook(),wrapper);
        order.setIspay(1);
        dbOrderService.save(order);
        channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);//MQ回调
    }
}
