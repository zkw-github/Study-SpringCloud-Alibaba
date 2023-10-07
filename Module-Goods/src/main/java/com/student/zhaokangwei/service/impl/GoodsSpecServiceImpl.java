package com.student.zhaokangwei.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.student.zhaokangwei.entity.GoodsSpec;
import com.student.zhaokangwei.mapper.IGoodsSpecMapper;
import com.student.zhaokangwei.service.IGoodsSpecService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;

@Slf4j
@Service
public class GoodsSpecServiceImpl extends ServiceImpl<IGoodsSpecMapper, GoodsSpec> implements IGoodsSpecService {
    @Resource
    IGoodsSpecMapper goodsSpecMapper;

    @Override
    public void subtractCount(Integer id, Integer count) {
        goodsSpecMapper.updateCount(id, count);

    }

    @Override
    public Map<String,Object> getGoodsSpecByGoodsId(Integer goodsId) {
        QueryWrapper<GoodsSpec> goodsSpecQueryWrapper=new QueryWrapper();//创建商品规格条件构造器对象
        goodsSpecQueryWrapper.eq("goods_id", goodsId);
        log.info("商品规格：" + goodsSpecMapper.getGoodsSpecByGoodsId(goodsSpecQueryWrapper));
        return goodsSpecMapper.getGoodsSpecByGoodsId(goodsSpecQueryWrapper);
    }

    @Override
    public IPage<Map<String, Object>> selectGoodsSpecPage(Integer pageIndex, Integer pageSize, Integer goodsId, String goodsSpecName) {
        //创建分页对象
        Page page=new Page(pageIndex, pageSize);
        //创建商品规格条件构造对象
        QueryWrapper<GoodsSpec> goodsSpecQueryWrapper=new QueryWrapper<>();
        //拼接条件
        if(goodsId!=null && goodsId!=0){
            goodsSpecQueryWrapper.eq("goods_id", goodsId);
        }
        if (goodsSpecName != null && !goodsSpecName.equals("")){
            goodsSpecQueryWrapper.like("name", goodsSpecName);
        }
        goodsSpecQueryWrapper.orderByAsc("id");
        return goodsSpecMapper.selectMapsPage(page, goodsSpecQueryWrapper);
    }

}
