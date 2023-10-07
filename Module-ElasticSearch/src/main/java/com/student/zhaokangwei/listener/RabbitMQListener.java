package com.student.zhaokangwei.listener;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
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
    @RabbitListener(queues = RabbitMQConfig.queueSeckill)
    public void listenQueue(String message){
        log.info("接收到RabbitMQ抢购到的消息了，消息是：" + message);
        JSONObject jsonObject= JSON.parseObject(message);
    }
}
