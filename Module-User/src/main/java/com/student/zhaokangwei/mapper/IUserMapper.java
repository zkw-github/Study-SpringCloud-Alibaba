package com.student.zhaokangwei.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.student.zhaokangwei.entity.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;

/**
 * 用户类 Mapper
 */
@Mapper
public interface IUserMapper extends BaseMapper<User> {
    @Insert("insert into user values(default,#{username},MD5(CONCAT(#{username},#{password})),#{nickname},#{sex},now(),#{usable},#{score})")
    @Options(
            keyProperty = "id",
            useGeneratedKeys = true
    )
    int insertUser(User user);

}
