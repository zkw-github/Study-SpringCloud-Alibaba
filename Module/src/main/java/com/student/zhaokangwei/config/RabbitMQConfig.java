package com.student.zhaokangwei.config;


import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;



/**
 * RabbitMQ配置类
 */
@Configuration
public class RabbitMQConfig {

    //定义一个交换机名称
    public static final String exchangeName="myExchange";

    //定义商品规格库存修改的队列名称
    public static final String queueGoodsUpdateInventory="queueGoodsUpdateInventory";

    public static final String queueUserUpdateScore="queueUserUpdateScore";

    public static final String queueSeckill="queueSeckill";

    //定义交换机Bean
    @Bean("myExchange")
    public Exchange exchange(){
        return ExchangeBuilder.topicExchange(exchangeName).durable(true).build();
    }

    //定义队列Bean
    @Bean("queueGoodsUpdateInventory")
    public Queue queueGoodsUpdateInventory(){
        return QueueBuilder.durable(queueGoodsUpdateInventory).build();
    }

    //定义队列Bean
    @Bean("queueUserUpdateScore")
    public Queue queueUserUpdateScore(){
        return QueueBuilder.durable(queueUserUpdateScore).build();
    }

    //定义队列Bean
    @Bean("queueSeckill")
    public Queue queueSeckill(){
        return QueueBuilder.durable(queueSeckill).build();
    }

    //将 更新商品库存的队列和交换机进行绑定
    @Bean
    public Binding bindQueueGoodsUpdateInventoryToExchange(@Qualifier("queueGoodsUpdateInventory") Queue queue,
                                                           @Qualifier("myExchange") Exchange exchange){
        return BindingBuilder.bind(queue).to(exchange).with("GoodsUpdateInventory").noargs();

    }

    //将 更新用户积分的队列和交换机进行绑定
    @Bean
    public Binding bindQueueUserUpdateScoreToExchange(@Qualifier("queueUserUpdateScore") Queue queue,
                                                      @Qualifier("myExchange") Exchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with("UserUpdateScore").noargs();
    }

    //将 秒杀的队列和交换机进行绑定
    @Bean
    public Binding bindQueueSeckillToExchange(@Qualifier("queueSeckill") Queue queue,
                                              @Qualifier("myExchange") Exchange exchange){
        return BindingBuilder.bind(queue).to(exchange).with("Seckill").noargs();

    }

}

