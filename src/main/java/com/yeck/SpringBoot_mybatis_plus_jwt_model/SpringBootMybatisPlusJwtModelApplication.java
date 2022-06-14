package com.yeck.SpringBoot_mybatis_plus_jwt_model;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

// MapperScan注解 配置 Mybatis-plus 从Mapper文件夹搜索对应Mapper接口
@SpringBootApplication
@MapperScan("com.yeck.SpringBoot_mybatis_plus_jwt_model.mapper")
public class SpringBootMybatisPlusJwtModelApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringBootMybatisPlusJwtModelApplication.class, args);
    }

}
