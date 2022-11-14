package com.student.zhaokangwei.controller.fc;

import com.alibaba.fastjson.JSON;
import com.student.zhaokangwei.service.IGoodsService;
import com.student.zhaokangwei.service.IGoodsSpecService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;


/**
 * 服务提供方 用于内部通信
 */
@Slf4j
@RestController
@RequestMapping("fc")
public class InnerController {
    @Resource
    IGoodsService goodsService;
    @Resource
    IGoodsSpecService goodsSpecService;
    /**
     * 根据商品ID获取商品数据
     * @param id 商品ID
     * @return 商品数据
     */
    @GetMapping("getGoodsById")
    public Object getGoodsById(@RequestParam Integer id) throws InterruptedException {
        //模拟一个慢请求
        Thread.sleep(1200);
        log.info("goods: " + goodsService.getById(id));
       return JSON.toJSONString(goodsService.getById(id));

    }

    /**
     * 根据商品规格ID获取商品规格数据
     * @param id 商品规格数据
     * @return 商品规格数据
     */
    @GetMapping("getGoodsSpecById")
    public Object getGoodsSpecById(@RequestParam Integer id){
        log.info("goodsSpec: " + goodsSpecService.getById(id));
        return JSON.toJSONString(goodsSpecService.getById(id));

    }
}
