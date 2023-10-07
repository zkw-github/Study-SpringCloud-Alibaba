package com.student.zhaokangwei.listener;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.student.zhaokangwei.config.RabbitMQConfig;
import com.student.zhaokangwei.service.IGoodsSpecService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * RabbitMQL 监听器
 */
@Slf4j
@Component
public class RabbitMQListener {
    @Resource
    IGoodsSpecService goodsSpecService;
    @RabbitListener(queues = RabbitMQConfig.queueGoodsUpdateInventory)
    public void listenQueueGoodsUpdateInventory(String message){
        log.info("接收到RabbitMQ更新商品库存的消息了，消息是：" + message);
        JSONObject jsonMessage= JSON.parseObject(message);
        goodsSpecService.subtractCount(jsonMessage.getInteger("goodsSpecId"),jsonMessage.getInteger("count"));

    }
}
