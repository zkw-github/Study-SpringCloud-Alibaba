package com.student.zhaokangwei.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 订单实体类
 */
@Data
public class Orderform implements Serializable {
  @TableId(value = "id", type = IdType.AUTO)
  private Integer  id;//订单ID
  private Integer  stateId;//订单状态ID
  private Integer  userId;//用户ID
  private String   goodsName;//商品名称
  private String   goodsSpec;//商品规格
  private Integer  count;//订单数量
  private Float    price;//订单价格
  private Float    money;//订单费用
  private LocalDateTime submittime;//订单提交时间

  @Override
  public String toString() {
    return "Orderform{" +
            "id=" + id +
            ", stateId=" + stateId +
            ", userId=" + userId +
            ", goodsName='" + goodsName + '\'' +
            ", goodsSpec='" + goodsSpec + '\'' +
            ", count=" + count +
            ", price=" + price +
            ", money=" + money +
            ", submittime=" + submittime +
            '}';
  }
}
