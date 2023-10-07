package com.student.zhaokangwei.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.student.zhaokangwei.entity.Goods;
import com.student.zhaokangwei.exception.ServiceValidationException;
import com.student.zhaokangwei.mapper.IGoodsMapper;
import com.student.zhaokangwei.service.IGoodsService;
import com.student.zhaokangwei.thread.ESAddThread;
import com.student.zhaokangwei.thread.ESDeleteThread;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.IOException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

/**
 * 商品 Service实现类
 */
@Slf4j
@Service
public class GoodServiceImpl extends ServiceImpl<IGoodsMapper, Goods> implements IGoodsService {

    @Resource
    RestHighLevelClient restHighLevelClient;
    @Resource
    RedisTemplate redisTemplate;

    @Override
    public JSONArray search(String searchStr) {
        if(searchStr==null ||searchStr.equals("")){
            throw new ServiceValidationException("搜索关键词不能为空", 400);
        }
        //根据搜索关键词从ES中查询出数据，并将数据存储到下面的array对象中
        //创建搜索源构建对象，创建该对象的目的时执行ES数据的查询
        SearchSourceBuilder searchSourceBuilder=new SearchSourceBuilder();
        //创建一个ES的查询条件构建对象
        QueryBuilder queryBuilder= QueryBuilders.boolQuery()
                .must(QueryBuilders.multiMatchQuery(searchStr, "name"));

        //执行查询
        searchSourceBuilder.query(queryBuilder);
        //设置高亮显示
        //创建一个高亮构建对象
        HighlightBuilder highlightBuilder=new HighlightBuilder();
        //设置高亮字段
        highlightBuilder.field("name");
        //设置高亮字段的开始标签
        highlightBuilder.preTags("<span>");
        //设置高亮字段的结束标签
        highlightBuilder.postTags("</span>");
        //给ES搜索源构建对象设置高亮构建器
        searchSourceBuilder.highlighter(highlightBuilder);
        //对每条搜索结果限制长度
        searchSourceBuilder.size(100);
        //设置查询索引的范围
        String[] indices={"*"};
        //创建ES请求对象
        SearchRequest searchRequest=new SearchRequest(indices);
        //从ES查询对象解析源数据，目的就让上面的queryBuilder和searchRequest对象绑定关联起来
        searchRequest.source(searchSourceBuilder);
        //创建要返回的对象
        JSONArray array=new JSONArray();

        try {
            //创建搜索响应对象
            SearchResponse searchResponse=restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
            //打印结果
//            log.info("搜索结果：" + searchResponse.toString());
            for(SearchHit hit:searchResponse.getHits()){
                //获取存入ES的原始数据
                Map<String,Object> sourceMap=hit.getSourceAsMap();
                Map<String, HighlightField> highlightFieldMap=hit.getHighlightFields();
                log.info(JSON.toJSONString(sourceMap));
                //向sourceMap中重新赋值name字段数据，并覆盖原始name字段数据
                sourceMap.put("name", highlightFieldMap.get("name").getFragments()[0].toString());
                //将每一个搜索结果保存到Array对象中
                array.add(sourceMap);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return array;
    }

    @Override
    public IPage<Map<String, Object>> goodsPage(Integer pageIndex, Integer pageSize, Integer typeId, Integer state, Boolean unexpired) {
        Page page=new Page(pageIndex, pageSize);//创建分页对象
        QueryWrapper queryWrapper=new QueryWrapper();//创建条件构造器对象
        if(typeId>0){
            //设置“type_id”作为条件
            queryWrapper.eq("type_id",typeId);
        }
        if(state!=null){
            //设置“state”作为条件
            queryWrapper.eq("state", state);
        }
        if(unexpired){
            queryWrapper.apply("expiration>now()");
        }
        queryWrapper.orderByAsc("id");
        return pageMaps(page, queryWrapper);
    }

    @Override
    public Goods goodsGetById(Integer id) {
        //根据ID查询单个商品对象
        Goods goods=getById(id);

        //若商品对象为空，则抛出“未找到对应商品信息，请核实传递的ID"
        log.info("goods: "+goods);
        if(goods==null){
            throw new ServiceValidationException("未找到对应商品信息，请核实传递的ID",402);
        }
        //若商品已下架，则抛出”该商品已下架，不再提供查询“
        if(goods.getState()==0){
            throw new ServiceValidationException("该商品已下架，不再提供查询",402);
        }

        //若商品已过期，则抛出”商品已过期，不再提供查询“
        if(goods.getExpiration().toEpochDay()< LocalDate.now().toEpochDay()){
            throw new ServiceValidationException("商品已过期，不再提供查询",402);
        }
        return goods;
    }

    @Override
    public void putOn(Integer id) {
        Goods goods=new Goods();
        goods.setId(id);
        goods.setState(1);
        updateById(goods);
        //执行完修改操作之后，再从MySQL里面查询出数据
        goods=getById(id);
        //去执行ES数据的插入
        //构建一个要存入ES的数据
        Map<String, Object> esDataMap = new HashMap<>();
        esDataMap.put("name", goods.getName());
        esDataMap.put("id", goods.getId());
        esDataMap.put("image", goods.getImage());

        //创建ESAddThread子线程
        ESAddThread esAddThread = new ESAddThread(esDataMap, restHighLevelClient,redisTemplate);
        esAddThread.start();
    }

    @Override
    public void putDown(Integer id) {
        Goods goods=new Goods();
        goods.setId(id);
        goods.setState(0);
        updateById(goods);

        //执行ES数据的删除
        ESDeleteThread esDeleteThread=new ESDeleteThread(id,restHighLevelClient,redisTemplate);
        esDeleteThread.start();

    }

    @Override
    public void createGoods(Goods goods) {
        save(goods);

        //去执行ES数据的插入

        //构建一个要存入ES的数据
        Map<String, Object> esDataMap = new HashMap<>();
        esDataMap.put("name", goods.getName());
        esDataMap.put("id", goods.getId());
        esDataMap.put("image", goods.getImage());

        //创建ESAddThread子线程
        ESAddThread esAddThread = new ESAddThread(esDataMap, restHighLevelClient,redisTemplate);
        esAddThread.start();

    }
}
