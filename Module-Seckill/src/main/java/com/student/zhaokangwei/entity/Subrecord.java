package com.student.zhaokangwei.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 抢购活动 提交记录 实体类
 */
@Data
public class Subrecord implements Serializable {
    @TableId(value = "id",type = IdType.AUTO)
    private Integer id;//抢购活动 提交记录ID
    private Integer projectId;//活动ID
    private Integer userId;//用户ID
    private LocalDateTime submittime;//提交时间

}
