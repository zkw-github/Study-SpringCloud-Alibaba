package com.student.zhaokangwei.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.student.zhaokangwei.entity.GoodsSpec;


import java.util.Map;

/**
 * 商品规格 Service
 */
public interface IGoodsSpecService extends IService<GoodsSpec> {
    /**
     * 减少库存
     * @param id
     * @param count
     */
    void subtractCount(Integer id,Integer count);

    /**
     * 根据商品ID获取商品规格数据
     * @param goodsId 商品ID
     * @return 商品规格数据
     */
    Map<String,Object> getGoodsSpecByGoodsId(Integer goodsId);

    /**
     * 商品规格分页
     * @param pageIndex
     * @param pageSize
     * @param goodsId
     * @param goodsSpecName
     * @return
     */
    IPage<Map<String, Object>> selectGoodsSpecPage(Integer pageIndex,Integer pageSize,Integer goodsId,String goodsSpecName);
}
