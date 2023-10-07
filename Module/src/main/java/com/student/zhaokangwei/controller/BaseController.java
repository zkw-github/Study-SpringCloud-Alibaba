package com.student.zhaokangwei.controller;


import com.student.zhaokangwei.utils.ViewUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 控制器 父类
 */
@Api(tags = "基础数据相关接口")
public class BaseController {

    @Resource
    HttpServletRequest request;
    @Resource
    HttpServletResponse response;

    /**
     * 成功
     *
     * @param message
     * @param value
     * @return
     */
    @ApiOperation("请求成功")
    protected Object success(String message, Object value) {
        return ViewUtils.view(message, value, 200);
    }

    /**
     * 失败
     *
     * @param message
     * @param state
     * @return
     */
    @ApiOperation("请求失败")
    protected Object fail(String message, int state) {
        response.setStatus(state);
        return ViewUtils.view(message, null, state);
    }

}