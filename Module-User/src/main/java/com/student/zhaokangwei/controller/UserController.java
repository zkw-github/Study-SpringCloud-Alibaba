package com.student.zhaokangwei.controller;

import com.alibaba.fastjson.JSONObject;
import com.student.zhaokangwei.entity.User;
import com.student.zhaokangwei.service.IUserService;
import com.student.zhaokangwei.utils.TokenUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import lombok.Data;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.util.Date;

/**
 * 用户类 控制器
 */
@Api(tags="用户相关接口")
@RestController
public class UserController extends BaseController{
    @Resource
    IUserService userService;

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
        User user=new User(loginParam.getUsername(),loginParam.getPassword());
        Integer id=userService.login(user);
        Date issuedTime = new Date();
        Date expiresTime = new Date(issuedTime.getTime() + 1000 * 60 * 60 * 72);  //过期时间为72个小时
        JSONObject resultValue = new JSONObject();
        JSONObject sign=new JSONObject(true);
        sign.put("id", id);
        sign.put("role","user"); //普通用户角色：user，管理员角色：admin
        resultValue.put("token", TokenUtils.generate(sign.toJSONString(), issuedTime, expiresTime));
        return success("登录成功", resultValue);

    }

    /**
     * 用户参数实体
     */
    @ApiModel("用户参数实体")
    @Data
    static class UserParamBody{
        @ApiModelProperty("用户名")
        @NotBlank(message = "请传递用户名")
        private String username;//用户名
        @ApiModelProperty("密码")
        @NotBlank(message = "请传递密码")
        private String password;//密码
        @ApiModelProperty("昵称")
        @NotBlank(message = "请传递昵称")
        private String nickname;//昵称
        @ApiModelProperty("性别")
        @NotBlank(message = "请传递性别")
        private String sex;

    }
    @ApiOperation("注册")
    @PostMapping("register")
    public Object register(@Valid @RequestBody UserParamBody userParamBody,BindingResult result){
        userService.register(new User(userParamBody.getUsername(),userParamBody.getPassword(),
                userParamBody.getNickname(),userParamBody.getSex()));
        return success("注册成功",null);
    }

    @ApiOperation("用户身份信息")
    @GetMapping("get")
    @PreAuthorize("hasAnyRole('ROLE_user')")
    public Object get(){
        //从Security上下文对象中取出当前请求者的身份信息
        Integer userId=Integer.parseInt(SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString());
        return success(null,"身份信息：" + userId);
    }

    /**
     * 禁用用户
     * @param disableUserId 禁用用户ID
     * @return
     */
    @ApiOperation("禁用用户")
    @PostMapping("disableUser")
    @PreAuthorize("hasRole('ROLE_admin')")
    public Object disableUser(@RequestParam Integer disableUserId){
        userService.disableUser(disableUserId);
        return success("禁用成功",null);
    }


}
