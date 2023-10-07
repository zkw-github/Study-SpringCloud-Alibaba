package com.student.zhaokangwei.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.student.zhaokangwei.entity.Orderform;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

/**
 * 订单 Mapper
 */
@Mapper
public interface IOrderformMapper extends BaseMapper<Orderform> {

    @Select("select orderform.id as 'orderformId',user_id as 'userId',orderform_state.name as 'orderformStateName',goods_name as 'goodsName',goods_spec as 'goodsSpec',count,price,money,submittime from orderform " +
            "inner join orderform_state on orderform.state_id=orderform_state.id " +
            "${ew.customSqlSegment}")
    List<Map<String,Object>> selectOrderformInfo(@Param("ew") Wrapper queryWrapper);

    @Delete("delete from orderform where id=#{id}")
    int delete(Integer id);
}
