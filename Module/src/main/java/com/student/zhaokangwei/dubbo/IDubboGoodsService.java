package com.student.zhaokangwei.dubbo;

import com.alibaba.fastjson.JSONObject;

/**
 * 定义一个接口，用于商品数据通讯
 */
public interface IDubboGoodsService {
    /**
     * 根据商品ID获取商品信息
     * @param id
     * @return
     */
    JSONObject getGoodsById(Integer id);

    /**
     * 根据商品规格ID获取商品规格信息
     * @param id
     * @return
     */
    JSONObject getGoodsSpecById(Integer id);
}
