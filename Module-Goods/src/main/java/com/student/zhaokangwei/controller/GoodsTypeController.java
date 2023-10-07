package com.student.zhaokangwei.controller;

import com.student.zhaokangwei.service.IGoodsTypeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@Api(tags = "商品类型相关接口")
@RestController
@RequestMapping("type")
public class GoodsTypeController extends BaseController{
    @Resource
    IGoodsTypeService goodsTypeService;

    @ApiOperation("商品类型列表")
    @GetMapping
    public Object index(){
        return success(null, goodsTypeService.list());
    }
}
