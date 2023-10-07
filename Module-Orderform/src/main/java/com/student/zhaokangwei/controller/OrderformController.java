package com.student.zhaokangwei.controller;


import com.student.zhaokangwei.service.IOrderformService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.constraints.NotNull;


/**
 * 订单类控制器
 */
@Api(tags = "订单相关接口")
@Slf4j
@RestController
public class OrderformController extends BaseController {
    @Resource
    IOrderformService orderformService;

    @GetMapping
    public Object index(){
        return success("success", orderformService.getOrderformInfo());
    }
    @ApiModel("创建订单参数实体")
    @Data
    static class CreateOrderformParan {
        @ApiModelProperty("商品ID")
        @NotNull(message = "必须传递goodsId")
        Integer goodsId;//商品ID
        @ApiModelProperty("商品类型ID")
        @NotNull(message = "必须传递goodsSpecId")
        Integer goodsSpecId;//商品规格ID
        @ApiModelProperty("商品数量")
        @NotNull(message = "必须传递商品数量")
        Integer count;//购买数量
    }

    /**
     * 创建订单
     */
    @ApiOperation("订单创建")
    @PostMapping("create")
    public Object create(@RequestBody CreateOrderformParan createOrderformParan) {
        log.info("开始创建订单...");
        orderformService.createOrderform(createOrderformParan.getGoodsId(), createOrderformParan.goodsSpecId,
                createOrderformParan.getCount());
        return success("订单创建成功", null);
    }

    /**
     * 移除订单
     * @param id 订单ID
     * @return
     */
    @PostMapping("remove")
    public Object remove(@RequestBody Integer id){
        orderformService.delete(id);
        return success("删除成功", null);
    }
}
