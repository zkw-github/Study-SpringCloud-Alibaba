package com.student.zhaokangwei.service;

/**
 * 管理员 业务接口
 */
public interface IAdminService {
    /**
     * 登录
     * @param username 用户名
     * @param password 密码
     */
    void login(String username,String password);

    /**
     * 修改密码
     * @param username 用户名
     * @param newPassword 新密码
     */
    void changePwd(String username,String newPassword);
}
