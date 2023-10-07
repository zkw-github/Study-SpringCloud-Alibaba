package com.student.zhaokangwei.service.impl;

import com.student.zhaokangwei.exception.ServiceValidationException;
import com.student.zhaokangwei.service.IAdminService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;


@Slf4j
@Service
public class AdminServiceImpl implements IAdminService {

    @Resource
    RedisTemplate redisTemplate;

    @Override
    public void login(String username, String password) {

        String redisPassword=redisTemplate.opsForValue().get(username).toString();//从Redis里取出的密码
        log.info("redisPassword: " + redisPassword);
        if(!password.equals(redisPassword)){
            throw new ServiceValidationException("登录失败，账号或密码错误", 401);
        }

    }

    /**
     * 修改密码
     * @param username 用户名
     * @param newPassword 新密码
     */
    @Override
    public void changePwd(String username,String newPassword) {
        redisTemplate.opsForValue().set(username, newPassword);
    }
}
