package com.xiong.myprojectbackend.config;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author 10371
 * @Description
 * @create 2023/10/8 15:14:20
 */
@Configuration
public class RabbitConfiguration {
    @Bean
    public Queue emailQueue(){
        return QueueBuilder
                .nonDurable("email")
                .build();
    }

    @Bean
    public Queue OrderQueue(){
        return QueueBuilder
                .nonDurable("order")
                .build();
    }

    @Bean
    public DirectExchange emailExchange(){
        return new DirectExchange("emailExchange",true,false);
    }

    @Bean
    public DirectExchange OrderExchange(){
        return new DirectExchange("orderExchange",true,false);
    }

    @Bean
    public Binding bindingDirect(){
        return BindingBuilder
                .bind(emailQueue())
                .to(emailExchange())
                .with("emailMQ");
    }

    @Bean
    public Binding bindingDirect2(){
        return BindingBuilder
                .bind(OrderQueue())
                .to(OrderExchange())
                .with("orderMQ");
    }
    
}
