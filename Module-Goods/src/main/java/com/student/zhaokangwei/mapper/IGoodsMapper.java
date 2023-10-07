package com.student.zhaokangwei.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.student.zhaokangwei.cache.RedisCache;
import com.student.zhaokangwei.entity.Goods;
import org.apache.ibatis.annotations.CacheNamespace;
import org.apache.ibatis.annotations.Mapper;



/**
 * 商品 Mapper
 * implementation 实现
 * eviction 驱逐
 */

@CacheNamespace(implementation = RedisCache.class,eviction = RedisCache.class)
@Mapper
public interface IGoodsMapper extends BaseMapper<Goods> {

}
