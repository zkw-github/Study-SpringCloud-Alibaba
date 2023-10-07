package com.student.zhaokangwei.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.student.zhaokangwei.entity.GoodsSpec;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.Map;


/**
 * 商品规格 Mapper
 */
@Mapper
public interface IGoodsSpecMapper extends BaseMapper<GoodsSpec> {
    /**
     * 更新商品库存
     * @param id
     * @param count
     * @return
     */
    @Update("update goods_spec set inventory=inventory-${count} where id=${id}")
    Integer updateCount(@Param("id")Integer id, @Param("count") Integer count);

    @Select("select goods_spec.id as 'id',goods.name as 'goodsName',goods.image as 'goodsImage',goods_spec.name as 'goodsSpecName',price,inventory from goods_spec " +
            "inner join goods on goods_spec.goods_id=goods.id " +
            "${ew.customSqlSegment}")
    Map<String,Object> getGoodsSpecByGoodsId(@Param("ew") Wrapper queryWrapper);


    @Select("select goods_spec.id as'id',goods_id as 'goodsId',goods.name as 'goodsName',goods.image as 'goodsImage'," +
            "goods_spec.name as 'goodsSpecName',price,inventory from goods_spec " +
            "inner join goods on goods_spec.goods_id=goods.id " +
            "${ew.customSqlSegment}")
    @Override
    <E extends IPage<Map<String, Object>>> E selectMapsPage(E page,@Param("ew") Wrapper<GoodsSpec> queryWrapper);
}
