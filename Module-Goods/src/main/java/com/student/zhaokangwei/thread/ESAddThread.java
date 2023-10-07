package com.student.zhaokangwei.thread;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.data.redis.core.RedisTemplate;

import java.io.IOException;
import java.util.Date;
import java.util.Map;

@Slf4j
public class ESAddThread extends Thread {

    Map<String, Object> esDataMap;  //要存入ES的数据
    RestHighLevelClient restHighLevelClient;
    RedisTemplate redisTemplate;

    public ESAddThread(Map<String, Object> esDataMap, RestHighLevelClient restHighLevelClient,RedisTemplate redisTemplate) {
        this.esDataMap = esDataMap;
        this.restHighLevelClient = restHighLevelClient;
        this.redisTemplate=redisTemplate;
    }

    @Override
    public void run() {
        super.run();

        log.info("进入了ESAddThread子线程....");

        //创建索引请求对象
        IndexRequest indexRequest = new IndexRequest(String.valueOf(new Date().getTime())).source(esDataMap);

        try {

            //创建索引响应对象
            IndexResponse indexResponse = restHighLevelClient.index(indexRequest, RequestOptions.DEFAULT);

            //输出结果
            log.info("响应结果：" + indexResponse.toString());
            //构建一个存入Redis数据库的数据
            JSONObject responseData=new JSONObject(true);
            responseData.put("id", indexResponse.getId());
            responseData.put("index", indexResponse.getIndex());

            //把上面的responseData对象存入Redis
            redisTemplate.opsForValue().set("ES_" + esDataMap.get("id"),responseData);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
