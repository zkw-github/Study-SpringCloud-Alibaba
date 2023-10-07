package com.student.zhaokangwei.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.student.zhaokangwei.config.RabbitMQConfig;
import com.student.zhaokangwei.dubbo.IDubboGoodsService;
import com.student.zhaokangwei.entity.Orderform;
import com.student.zhaokangwei.exception.ServiceValidationException;
import com.student.zhaokangwei.mapper.IOrderformMapper;
import com.student.zhaokangwei.service.IOrderformService;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class OrderformServiceImpl extends ServiceImpl<IOrderformMapper, Orderform> implements IOrderformService {

    @DubboReference(
            timeout = 1000,  //连接并读取超时时间，单位为ms,默认1000（1s）
            mock="com.student.zhaokangwei.dubbo.mock.DubboGoodsServiceMock"
    )
    IDubboGoodsService dubboGoodsService;

    @Resource
    RabbitTemplate rabbitTemplate;
    @Resource
    IOrderformMapper orderformMapper;

    @Override
    public void createOrderform(Integer goodsId,Integer goodsSpecId,Integer count) {
        //获取当前userId
        Integer userId=Integer.parseInt(SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString());
        log.info("开始创建订单...");
        JSONObject goodsObject=dubboGoodsService.getGoodsById(goodsId);//通过Dubbo获取商品数据
        JSONObject goodsSpecObject=dubboGoodsService.getGoodsSpecById(goodsSpecId);//通过Dubbo获取商品规格数据
        //创建订单对象
        Orderform orderform=new Orderform();
        orderform.setStateId(1);
        orderform.setUserId(userId);
        orderform.setGoodsName(goodsObject.getString("name"));
        orderform.setGoodsSpec(goodsSpecObject.getString("name"));
        orderform.setCount(count);
        orderform.setPrice(goodsSpecObject.getFloat("price"));
        orderform.setMoney(count * goodsSpecObject.getFloat("price"));
        orderform.setSubmittime(LocalDateTime.now());
        log.info("构建好的订单对象：" + orderform);
        if(!save(orderform)){
            throw new ServiceValidationException("订单创建失败，请联系客服", 402);
        }
        JSONObject jsonMessageForGoodsUpdateInventory=new JSONObject(true);//构建一个即将发送的消息
        jsonMessageForGoodsUpdateInventory.put("goodsSpecId", goodsSpecId);
        jsonMessageForGoodsUpdateInventory.put("count", count);
        //向RabbitMQ发出更新商品库存的消息
        rabbitTemplate.convertAndSend(RabbitMQConfig.exchangeName, "GoodsUpdateInventory",
                jsonMessageForGoodsUpdateInventory.toString());//构建一条即将要发送的消息

        JSONObject jsonMessageForUserUpdateScore=new JSONObject(true);//构建一个即将发送的消息
        jsonMessageForUserUpdateScore.put("userId", userId);
        //向RabbitMQ发出更新用户积分的消息
        jsonMessageForUserUpdateScore.put("money", orderform.getMoney());
        rabbitTemplate.convertAndSend(RabbitMQConfig.exchangeName, "UserUpdateScore",
                jsonMessageForUserUpdateScore.toString());

    }

    @Override
    public List<Map<String, Object> > getOrderformInfo() {
        QueryWrapper<Orderform> orderformQueryWrapper=new QueryWrapper<>();
        return orderformMapper.selectOrderformInfo(orderformQueryWrapper);
    }

    @Override
    public boolean delete(Integer id) {
        if(orderformMapper.delete(id)==0){
            throw new ServiceValidationException("要移除的订单ID不存在，请检查", 402);
        }
        return true;
    }
}
