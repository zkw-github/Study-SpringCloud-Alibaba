package com.student.zhaokangwei.controller;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.student.zhaokangwei.entity.Goods;
import com.student.zhaokangwei.service.IGoodsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiOperation;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Map;
import java.util.Random;

/**
 * 商品 Controller
 */
@RefreshScope
@RestController
@Api(tags = "商品相关接口")
public class GoodsController extends BaseController{
    @Resource
    IGoodsService goodsService;

    /**
     * 搜索
     * @param searchStr 搜索关键词
     * @return 搜索结果
     */
    @ApiOperation("商品搜索")
    @PostMapping("search")
    public Object search(@RequestBody String searchStr){
        return success(null, goodsService.search(searchStr));
    }

    /**
     * 商品分页查询数据
     */
    @ApiModel("商品分页参数实体")
    @Data
    static class GoodsPageBody{
        @NotNull(message = "请传递页码")
        private Integer pageIndex;//当前页码
       @NotNull(message = "请传递类型，默认传递0")
        private Integer typeId;//商品类型ID
    }

    @SentinelResource(value = "goodsPage", //用于定义资源名称

                      fallback = "pageFallback"   //用于定义请求失败后的降级处理
    )
    @ApiOperation("商品分页")
    @PostMapping("page")
    public Object page(@Valid @RequestBody GoodsPageBody goodsPageBody, BindingResult result) throws InterruptedException {
        //创建随机对象
        Random random=new Random();
        //产生随机数
        int time=random.nextInt(5000);
        //让程序执行到此处暂停5秒，用于模拟慢请求
        Thread.sleep(time);
        IPage<Map<String,Object>> goodsMaps=goodsService.goodsPage(goodsPageBody.pageIndex,15,
                goodsPageBody.getTypeId(),1,true);
        return success("time: " + time, goodsMaps);
    }

    @ApiOperation("根据ID获取商品")
    @SentinelResource(value = "goodsGetById")
    @GetMapping("get/{id}")
    public Object get(@PathVariable Integer id){
        return success(null, goodsService.goodsGetById(id));
    }

    /**
     * page 接口请求失败后的降级处理
     * @param goodsPageBody
     * @param result
     * @return
     */
    public Object pageFallback(@Valid @RequestBody  GoodsPageBody goodsPageBody,BindingResult result){
        return fail("请求失败，请后重试！", 429);

    }



    /**
     * 创建一个新商品
     * @param goods 商品对象
     * @return
     */
    @ApiOperation("商品创建")
    @PostMapping("create")
    public Object create(@RequestBody Goods goods){
        goodsService.createGoods(goods);
        return success("创建成功", null);
    }

    @ApiOperation("商品上架")
    @GetMapping("putOn/{id}")
    public Object putOn(@PathVariable Integer id){
        goodsService.putOn(id);
        return success("上架成功", null);
    }
    /**
     * 下架一个商品
     * @return
     */
    @ApiOperation("商品下架")
    @GetMapping("putDown/{id}")
    public Object putDown(@PathVariable Integer id){
        goodsService.putDown(id);
        return success("下架成功", null);
    }

    /**
     * 获取黑名单列表
     * @return 黑名单
     */
    @Value("${ipBlackList}")
    String ipBlackList;

    @ApiOperation("黑名单列表")
    @GetMapping("test")
    public Object test(){
        return success(null, ipBlackList);
    }
}
