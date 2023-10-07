package com.student.zhaokangwei.dubbo.mock;

import com.alibaba.fastjson.JSONObject;
import com.student.zhaokangwei.dubbo.IDubboGoodsService;
import lombok.extern.slf4j.Slf4j;

/**
 * 模仿真实提供者返回的结果，降级处理
 */
@Slf4j
public class DubboGoodsServiceMock implements IDubboGoodsService {
    @Override
    public JSONObject getGoodsById(Integer id) {
        log.warn("IGoodsFeign的getGoodsById()方法超时后进行了降级，发送短信给管理员，紧急维护");
        JSONObject result = new JSONObject();
        result.put("id", -1);
        result.put("name", "默认商品名称");
        return result;
    }

    @Override
    public JSONObject getGoodsSpecById(Integer id) {
        return null;
    }
}
