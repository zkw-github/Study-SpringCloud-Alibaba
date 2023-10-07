package com.student.zhaokangwei.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.student.zhaokangwei.entity.ProjectOrder;
import org.apache.ibatis.annotations.Mapper;

/**
 * 抢购活动 订单Mapper接口
 */
@Mapper
public interface IProjectOrderMapper extends BaseMapper<ProjectOrder> {
}
