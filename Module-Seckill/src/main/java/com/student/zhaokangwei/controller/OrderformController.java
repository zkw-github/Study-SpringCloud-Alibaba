package com.student.zhaokangwei.controller;

import com.student.zhaokangwei.service.ISeckillService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;


/**
 * 秒杀相关 接口
 */
@Api(tags = "秒杀相关接口")
@Slf4j
@RestController
public class OrderformController extends BaseController{

    @Resource
    ISeckillService seckillService;

    /**
     * 抢购
     * @return
     */
    @ApiOperation("抢购")
    @PostMapping("buy")
    public Object buy(){
        seckillService.buy(1);
        return success("恭喜你，抢到了，订单正在生成中......",null);
    }

}
