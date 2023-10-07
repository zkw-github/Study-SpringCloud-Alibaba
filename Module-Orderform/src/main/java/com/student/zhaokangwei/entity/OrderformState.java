package com.student.zhaokangwei.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * 订单状态 实体类
 */
@Data
public class OrderformState implements Serializable {
  private Integer  id;//订单状态ID
  private String   name;//订单状态名称

}
