package com.student.zhaokangwei.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.student.zhaokangwei.entity.GoodsType;
import com.student.zhaokangwei.mapper.IGoodsTypeMapper;
import com.student.zhaokangwei.service.IGoodsTypeService;
import org.springframework.stereotype.Service;

/**
 * 商品类型 Service实现类
 */
@Service
public class GoodTypeServiceImpl extends ServiceImpl<IGoodsTypeMapper, GoodsType> implements IGoodsTypeService {
}
