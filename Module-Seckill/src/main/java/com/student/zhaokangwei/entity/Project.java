package com.student.zhaokangwei.entity;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 抢购活动 实体类
 */
@Data
public class Project implements Serializable {
    private Integer id;//活动ID
    private String name;//活动名称
    private LocalDateTime starttime;//活动开始时间
    private Integer count;//活动订单数量

}
