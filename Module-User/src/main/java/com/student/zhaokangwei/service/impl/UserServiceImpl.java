package com.student.zhaokangwei.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.student.zhaokangwei.entity.User;
import com.student.zhaokangwei.exception.ServiceValidationException;
import com.student.zhaokangwei.mapper.IUserMapper;
import com.student.zhaokangwei.service.IUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;

@Slf4j
@Service
public class UserServiceImpl extends ServiceImpl<IUserMapper,User> implements IUserService {
    @Resource
    IUserMapper userMapper;

    @Override
    public Integer login(User user) {
        //1.判断user是否存在
        QueryWrapper userQueryWrapper = new QueryWrapper();
        userQueryWrapper.eq("username", user.getUsername());
        if (count(userQueryWrapper) == 0) {
            //未找到该用户
            throw new ServiceValidationException("登录失败，账号不存在，请前往注册", 401);
        }
        //2.密码判断
        QueryWrapper<User> loginUserQueryWrapper = new QueryWrapper();
        loginUserQueryWrapper.select("id","usable");
        loginUserQueryWrapper.eq("username",user.getUsername());
        loginUserQueryWrapper.inSql("password", "MD5('" + user.getUsername() + user.getPassword() + "')");
        Map<String,Object> map = getMap(loginUserQueryWrapper);
        log.info("map:" + map);
        if (map == null) {
            //检测到用户，但是密码错误
            throw new ServiceValidationException("登录失败，账号或密码错误", 401);
        }
        if(!Boolean.parseBoolean(map.get("usable").toString())){
            //检测到用户，但账号被禁用
            throw new  ServiceValidationException("登录失败，账号被禁用",401);
        }
        return Integer.parseInt(map.get("id").toString());
    }

    @Override
    public void register(User user) {
        //1.先判断用户名是否存在
        QueryWrapper<User> queryWrapper = new QueryWrapper();
        queryWrapper.eq("username", user.getUsername());
        if (count(queryWrapper) > 0) {
            throw new ServiceValidationException("用户名已存在", 402);
        }
            //新增
            user.setUsable(true);
            user.setScore(10);//新用户赠送10个积分
            if (userMapper.insertUser(user) == 0) {
                throw new ServiceValidationException("注册失败，请联系客服", 402);
            }

            log.info("新注册的用户ID：" + user.getId());
        }

    /**
     * 禁用用户
      * @param userId
     */
    @Override
    public void disableUser(Integer userId) {
            User user=new User();
            user.setId(userId);
            user.setUsable(false);
            updateById(user);
    }

}
