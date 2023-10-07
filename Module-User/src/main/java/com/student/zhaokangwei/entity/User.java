package com.student.zhaokangwei.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 用户 实体类
 */
@Data
public class User implements Serializable {
    @TableId(value = "id",type = IdType.AUTO)
   private Integer id;//用户ID
   private String username;//用户名
   private String password;//密码
   private String nickname;//匿名
   private String sex;//性别
   private LocalDateTime registerTime;//注册时间
   private Boolean usable;//是否禁用用户
   private Integer score;//积分

   public User() {
   }

   public User(String username, String password) {
      this.username = username;
      this.password = password;
   }

   public User(String username, String password, String nickname, String sex) {
      this.username = username;
      this.password = password;
      this.nickname = nickname;
      this.sex = sex;
   }
}
