package com.yeck.SpringBoot_mybatis_plus_jwt_model.Model;

import lombok.Data;

/**
 * User实体类
 * 与数据库中user表相关联
 * @author Yeck
 * @version 1.1
 */
@Data
public class User {
    private String account;
    private String password;
    private String email;
    private String name;
}
