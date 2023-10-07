package com.student.zhaokangwei.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.student.zhaokangwei.entity.User;

public interface IUserService extends IService<User> {
    /**
     * 登录
     * @param user 用户对象
     * @return 用户ID
     */
    Integer login(User user);

    /**
     * 用户注册
     * @param user 用户
     */
    void register(User user);

    void disableUser(Integer userId);
}
