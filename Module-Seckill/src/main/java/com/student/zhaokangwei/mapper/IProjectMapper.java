package com.student.zhaokangwei.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.student.zhaokangwei.entity.Project;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

/**
 * 抢购活动 Mapper接口
 */
@Mapper
public interface IProjectMapper extends BaseMapper<Project> {

    /**
     * 自减数量
     * @param count
     * @return
     */
    @Update("update project set count=count-#{count} where id=${id};")
    Integer decrementCount(@Param("count") Integer count, @Param("id") Integer id);
}
