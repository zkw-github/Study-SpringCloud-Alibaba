package com.student.zhaokangwei.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.student.zhaokangwei.entity.UserCollect;
import org.apache.ibatis.annotations.Mapper;

/**
 * 用户收藏类 Mapper
 */
@Mapper
public interface IUserCollectMapper extends BaseMapper<UserCollect> {
}
