package com.student.zhaokangwei;

import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;


import javax.annotation.Resource;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@SpringBootTest
class ModuleGoodsApplicationTests {

    @Test
    void contextLoads() {
    }

    /**
     * ES高级别客户端对象，用该对象可以操作ES数据的插入、查询等操作
     */
    @Resource
    RestHighLevelClient restHighLevelClient;
    @Test
    void testESAdd(){
       //构建一个要存入ES的数据
        Map<String,Object> esDataMap=new HashMap<>();
        esDataMap.put("name", "冬季新款韩版徽章外套宽松显瘦轻薄连帽中长款狐狸大毛领羽绒服女");
        esDataMap.put("id", "341");
        esDataMap.put("image","/upload/image/17.jpg");

        //还可以继续添加其他的数据

        //创建索引请求对象
        IndexRequest indexRequest=new IndexRequest(String.valueOf(new Date().getTime())).source(esDataMap);

        try{
            //创建索引响应对象
            IndexResponse   indexResponse = restHighLevelClient.index(indexRequest, RequestOptions.DEFAULT);

            //输出结果
            log.info("响应结果：" + indexResponse.toString());

            } catch (IOException e) {
                e.printStackTrace();
            }


        }

    /**
     * 测试 向ES中删除数据
     */
    @Test
    void testESDelete(){
        //获取要删除数据的索引
        String index="1669463692853";
        //要删除数据ID
        String id="99fKs4QB6Sr4ZYeoU9_J";
        //创建删除请求对象
        DeleteRequest deleteRequest= new DeleteRequest(index,id);
        try {
            restHighLevelClient.delete(deleteRequest,RequestOptions.DEFAULT);
            log.info("索引：" + index + ",被删除了...");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    }


