package com.student.zhaokangwei.service;

/**
 * 秒杀业务接口
 */
public interface ISeckillService{

    /**
     * 抢购方法
     * @param projectId 活动ID
     */
    void buy(Integer projectId);

    /**
     * 创建订单
     * @param projectId
     * @param userId
     */
    void createOrder(Integer projectId,Integer userId);
}
