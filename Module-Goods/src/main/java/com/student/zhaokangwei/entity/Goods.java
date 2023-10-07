package com.student.zhaokangwei.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * 商品 实体类
 */
@Data
public class Goods implements Serializable {
    @TableId(value = "id",type = IdType.AUTO)
    private Integer id;//商品ID
    private Integer typeId;//类型ID
    private String  name;//商品名称
    private String  image;//商品图片
    private Integer state;//商品状态 0：下架 1：上架
    private String  feature;//商品特色
    private LocalDate expiration;//商品过期时间


}
