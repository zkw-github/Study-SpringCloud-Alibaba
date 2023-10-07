package com.student.zhaokangwei.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.student.zhaokangwei.service.IGoodsSpecService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.Data;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.constraints.NotNull;
import java.util.Map;

/**
 * 商品规格控制器
 */
@Api(tags = "商品规格相关接口")
@RestController
@RequestMapping("spec")
public class GoodsSpecController extends BaseController{
    @Resource
    IGoodsSpecService goodsSpecService;

    @Data
    static class GoodsSpecParamBody{
        @NotNull(message = "必须传递页码")
        private Integer pageIndex;
        private Integer goodsId;
        private String  name;
    }
    @PostMapping
    public Object goodsSpecpage(@RequestBody GoodsSpecParamBody paramBody){
        IPage<Map<String, Object>> goodsSpecMapPage= goodsSpecService.selectGoodsSpecPage(paramBody.getPageIndex(), 15,paramBody.getGoodsId(),paramBody.getName());
        return success("success", goodsSpecMapPage);


    }
    @ApiOperation("根据规格ID获取规格数据")
    @GetMapping("get/{goodsId}")
    public Object getGoodsSpec(@PathVariable Integer goodsId){
        return success(null, goodsSpecService.getGoodsSpecByGoodsId(goodsId));
    }
}
