package com.student.zhaokangwei.controller;

import com.alibaba.fastjson.JSONObject;
import com.student.zhaokangwei.service.IAdminService;
import com.student.zhaokangwei.utils.TokenUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import lombok.Data;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;


import javax.annotation.Resource;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.util.Date;

/**
 * 用户类 控制器
 */
@Api(tags = "超级管理员相关接口")
@RestController
public class AdminController extends BaseController {

    @Resource
    IAdminService adminService;

    /**
     * 登录参数实体
     */
    @ApiModel("登录参数实体")
    @Data
    static class LoginParam{
        @ApiModelProperty("用户名")
        @NotBlank(message = "用户名不能为空")
       private String username;//用户名
        @ApiModelProperty("密码")
        @NotBlank(message = "密码不能为空")
        private String password;//密码
    }

    /**
     * 用户登录
     * @param loginParam 登录参数实体
     * @param result 获取校验结果对象
     * @return
     */
    @ApiOperation("登录")
    @PostMapping("login")
    public Object login(@Valid @RequestBody LoginParam loginParam, BindingResult result){
        //判断管理员账号和密码是否正确
        adminService.login(loginParam.getUsername(), loginParam.getPassword());
        Date issuedTime = new Date();
        Date expiresTime = new Date(issuedTime.getTime() + 1000 * 60 * 60 * 72);  //过期时间为72个小时
        JSONObject resultValue = new JSONObject();
        JSONObject sign=new JSONObject(true);
        sign.put("id", 1);
        sign.put("role","admin"); //普通用户角色：user，管理员角色：admin
        resultValue.put("token", TokenUtils.generate(sign.toJSONString(), issuedTime, expiresTime));
        return success("登录成功", resultValue);

    }

    /**
     * 修改密码
     * @param newPassword 新密码
     * @return
     */
    @ApiOperation("修改密码")
    @PostMapping("changePassword")
    @PreAuthorize("hasRole('ROLE_admin')")
    public Object changePassword(@RequestParam String newPassword){
        adminService.changePwd("admin", newPassword);
        return success("修改成功", null);
    }

}
