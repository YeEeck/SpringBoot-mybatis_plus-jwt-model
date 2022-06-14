package com.yeck.SpringBoot_mybatis_plus_jwt_model.Mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yeck.SpringBoot_mybatis_plus_jwt_model.Model.User;

/**
 * UserMapper接口
 * 继承BaseMapper接口(Mybatis-plus)，User泛型，对User实体类的BaseMapper操作。
 */
public interface UserMapper extends BaseMapper<User> {
}
