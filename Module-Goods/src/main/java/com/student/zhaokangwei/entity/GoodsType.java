package com.student.zhaokangwei.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serializable;

/**
 * 商品类型 实体类
 */
@Data
public class GoodsType implements Serializable {
    @TableId(value = "id",type = IdType.AUTO)
   private Integer  id;//商品类型ID
   private String   name;//商品类型名称

}
