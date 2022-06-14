package com.yeck.SpringBoot_mybatis_plus_jwt_model;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yeck.SpringBoot_mybatis_plus_jwt_model.Mapper.UserMapper;
import com.yeck.SpringBoot_mybatis_plus_jwt_model.Model.User;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@MapperScan("com.yeck.spring_boot_demo_0602.mapper")
class SpringBootDemo0602ApplicationTests {

    @Autowired(required = false)
    private UserMapper userMapper;

    @Test
    void contextLoads() {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("account", "admin");
        User user = userMapper.selectOne(queryWrapper);
        System.out.println(user.toString());
    }

}
