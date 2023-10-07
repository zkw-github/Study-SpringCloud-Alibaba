package com.student.zhaokangwei.listener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;

import javax.annotation.Resource;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@Slf4j
@WebListener
public class MyListener implements ServletContextListener {
    @Resource
    RedisTemplate redisTemplate;
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        //判断admin键是否存在
        if(!redisTemplate.hasKey("admin")){
            log.info("Redis里面没有admin数据，开始执行数据初始化，插入数据...");
            //如果不存在，则创建一个
            redisTemplate.opsForValue().set("admin","a123456");
        }
    }
}
