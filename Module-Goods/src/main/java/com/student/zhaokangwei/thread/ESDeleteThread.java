package com.student.zhaokangwei.thread;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.data.redis.core.RedisTemplate;

import java.io.IOException;

@Slf4j
public class ESDeleteThread extends Thread{
    Integer goodsId;//要删除ES索引对应的商品ID
    RestHighLevelClient restHighLevelClient;
    RedisTemplate redisTemplate;

    public ESDeleteThread(Integer goodsId, RestHighLevelClient restHighLevelClient, RedisTemplate redisTemplate) {
        this.goodsId = goodsId;
        this.restHighLevelClient = restHighLevelClient;
        this.redisTemplate = redisTemplate;
    }

    @Override
    public void run() {
        super.run();
        JSONObject esRedisData= (JSONObject) redisTemplate.opsForValue().get("ES_" + goodsId);
        //获取要删除数据的索引
        String index=  esRedisData.getString("index");
        //要删除数据ID
        String id=  esRedisData.getString("id");
        //创建删除请求对象
        DeleteRequest deleteRequest= new DeleteRequest(index,id);
        try {
            restHighLevelClient.delete(deleteRequest, RequestOptions.DEFAULT);
            log.info("索引：" + index + ",被删除了...");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
