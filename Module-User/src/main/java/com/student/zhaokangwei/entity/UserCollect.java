package com.student.zhaokangwei.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serializable;

/**
 * 用户收藏 实体类
 */
@Data
public class UserCollect implements Serializable {
   @TableId(value = "id",type = IdType.AUTO)
   private Integer id;//用户收集ID
   private Integer userId;//用户ID
   private Integer goodsId;//商品ID


}
