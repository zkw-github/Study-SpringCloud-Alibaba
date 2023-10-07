package com.student.zhaokangwei.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serializable;

/**
 * 商品规格 实体类
 */
@Data
public class GoodsSpec implements Serializable {
    @TableId(value = "id",type = IdType.AUTO)
    private Integer id;//商品规格ID
    private Integer goodsId;//商品ID
    private String  name;//商品规格名称
    private Float  price;//价格
    private Integer inventory;//库存

    public GoodsSpec() {
    }

    public GoodsSpec(Integer id, Integer goodsId, String name, Float price, Integer inventory) {
        this.id = id;
        this.goodsId = goodsId;
        this.name = name;
        this.price = price;
        this.inventory = inventory;
    }
}
