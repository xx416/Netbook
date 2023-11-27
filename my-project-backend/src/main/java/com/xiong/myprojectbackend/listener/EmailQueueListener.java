package com.xiong.myprojectbackend.listener;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.Channel;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Map;

/**
 * @author 10371
 * @Description
 * @create 2023/10/8 16:22:23
 */
@Component
@RabbitListener(queues = "email")
public class EmailQueueListener {

    @Resource
    JavaMailSender sender;

    @Value("${spring.mail.username}")
    String username;

    @RabbitHandler
    public void sendEmailMessage(@Payload Map<String , Object> map, Message mes, Channel channel) throws IOException {
        //确认消息
        channel.basicAck(mes.getMessageProperties().getDeliveryTag(), false);
        String email = map.get("email").toString();
        Integer code = (Integer) map.get("code");
        String type = map.get("type").toString();
        System.out.println("验证码:" + code);
        SimpleMailMessage msg = switch (type) {
            case "register" ->
                createMessage("欢迎注册我们的网站","您的邮件注册验证码为: "+code+
                        " ，有效时间3分钟，为了保障您的账号安全，请勿向他人泄露验证码。", email);
            case "reset" -> createMessage("您的密码重置邮件",
                    "您好，您正在进行重置密码操作，验证码为: "+code+
                            " ，有效时间3分钟，如非本人操作，请无视。",email);
            default -> null;
        };
        if (msg != null){// 发送邮件
            sender.send(msg);
        }
    }

    private SimpleMailMessage createMessage(String title, String content, String email){
        SimpleMailMessage message = new SimpleMailMessage();
        // 邮件主题
        message.setSubject(title);
        // 邮件的文本信息
        message.setText(content);
        // 收件人，支持多个收件人
        message.setTo(email);
        // 寄件人，默认是配置的username
        message.setFrom(username);
        return message;
    }
}
