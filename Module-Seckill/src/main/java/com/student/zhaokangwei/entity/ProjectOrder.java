package com.student.zhaokangwei.entity;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 抢购活动订单 实体类
 */
@Data
public class ProjectOrder implements Serializable {
    private Integer id;//抢购活动订单ID
    private Integer projectId;//活动ID
    private Integer userId;//用户ID
    private LocalDateTime submittime;//订单提交时间

}
