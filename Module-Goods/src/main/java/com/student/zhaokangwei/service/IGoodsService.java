package com.student.zhaokangwei.service;

import com.alibaba.fastjson.JSONArray;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.student.zhaokangwei.entity.Goods;

import java.util.Map;

/**
 * 商品 Service
 */
public interface IGoodsService extends IService<Goods> {
    /**
     * 搜索
     * @param searchStr 搜索关键词
     * @return 搜索结果
     */
    JSONArray search(String searchStr);

    /**
     * 分页查询商品数据
     * @param pageIndex 页码
     * @param pageSize 页大小
     * @param typeId 商品类型ID
     * @param state 商品状态
     * @param unexpired 是否未过期 true：未过期 false:已过期，就不再筛选
     * @return
     */
    IPage<Map<String,Object>> goodsPage(Integer pageIndex, Integer pageSize, Integer typeId, Integer state, Boolean unexpired);

    /**
     * 根据ID查询商品数据
     * @param id 商品ID
     * @return
     */
    Goods goodsGetById(Integer id);

    /**
     * 上架商品
     * @param id 商品ID
     */
    void putOn(Integer id);

    /**
     * 下架商品
     * @param id 商品ID
     */
    void putDown(Integer id);

    /**
     * 创建商品
     * @param goods 商品对象
     */
    void createGoods(Goods goods);
}
