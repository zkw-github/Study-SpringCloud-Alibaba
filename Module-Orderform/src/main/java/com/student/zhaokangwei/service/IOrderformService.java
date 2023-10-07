package com.student.zhaokangwei.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.student.zhaokangwei.entity.Orderform;

import java.util.List;
import java.util.Map;

/**
 * 订单业务接口
 */
public interface IOrderformService extends IService<Orderform> {
    /**
     * 创建订单
     * @param goodsId
     * @param goodsSpecId
     * @param count
     */
    void createOrderform(Integer goodsId,Integer goodsSpecId,Integer count);
    List<Map<String,Object>> getOrderformInfo();

    /**
     * 根据订单ID删除订单
     * @param id 订单ID
     * @return
     */
    boolean delete(Integer id);
}
