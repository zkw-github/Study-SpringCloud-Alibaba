package com.student.zhaokangwei.dubbo.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.student.zhaokangwei.dubbo.IDubboGoodsService;
import com.student.zhaokangwei.service.IGoodsService;
import com.student.zhaokangwei.service.IGoodsSpecService;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboService;

import javax.annotation.Resource;

/**
 * IDubboGoodsService实现类，用于实现商品通讯功能
 */
@Slf4j
@DubboService //加上该注解表示将该类定义为Dubbo服务提供者
public class DubboGoodsServiceImpl implements IDubboGoodsService {
    @Resource
    IGoodsService goodsService;
    @Resource
    IGoodsSpecService goodsSpecService;
    @Override
    public JSONObject getGoodsById(Integer id) {
//        log.info("执行getGoodsById方法...参数是：" + id +",开始等待2s...");
//        try {
//            Thread.sleep(2000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
        return (JSONObject) JSON.toJSON(goodsService.goodsGetById(id));
    }

    @Override
    public JSONObject getGoodsSpecById(Integer id) {
        return (JSONObject) JSON.toJSON(goodsSpecService.getById(id));
    }
}
