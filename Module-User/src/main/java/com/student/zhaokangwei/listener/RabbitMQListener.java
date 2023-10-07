package com.student.zhaokangwei.listener;

import com.student.zhaokangwei.config.RabbitMQConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * RabbitMQL 监听器
 */
@Slf4j
@Component
public class RabbitMQListener {

    @RabbitListener(queues = RabbitMQConfig.queueUserUpdateScore)
    public void listenQueueUserUpdateScore(String message){
        log.info("接收到RabbitMQ更新用户积分的消息了，消息是：" + message);

    }
}
