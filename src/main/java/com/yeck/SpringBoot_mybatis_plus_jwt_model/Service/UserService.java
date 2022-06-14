package com.yeck.SpringBoot_mybatis_plus_jwt_model.Service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yeck.SpringBoot_mybatis_plus_jwt_model.Mapper.UserMapper;
import com.yeck.SpringBoot_mybatis_plus_jwt_model.Model.Status;
import com.yeck.SpringBoot_mybatis_plus_jwt_model.Model.Token;
import com.yeck.SpringBoot_mybatis_plus_jwt_model.Model.User;
import com.yeck.SpringBoot_mybatis_plus_jwt_model.Util.JWTUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 用户相关的系统逻辑与数据操作
 * UserController分流至这里，用于系统功能的实现。
 * @author Yeck, 2022-6-14
 * @version 1.1
 */
@Service
public class UserService {

    /**
     * 通过构造器注入方式注入的UserMapper
     * 防止组件为空或循环注入
     */
    private final UserMapper userMapper;

    @Autowired
    public UserService(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    /**
     * 登录服务，对用户的登录操作进行校检
     * 对传入的User对象的用户名与密码与数据库信息进行校验
     * @param user User实体类，由UserController从JSON字符串转换后塞入
     * @return 校验通过则返回的通用返回对象中remarks对象中包含token，且code为0，否则code为-1
     */
    public Status login(User user) {
        // mybatis-plus 建立条件构造器 筛选
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("account", user.getAccount());
        // 查询结果存入 User 对象
        User sqlResult = userMapper.selectOne(queryWrapper);
        Status status = new Status();
        // mybatis-plus baseMapper接口的selectOne方法在没有符合结果的情况下会返回Null
        if (sqlResult == null) {
            status.setCode(-1);
            status.setMessage("用户名或密码错误");
        } else {
            if (sqlResult.getPassword().equals(user.getPassword())) {
                status.setCode(0);
                status.setMessage("校验通过");
                // Token对象用于使RestController在转换时能将remarks字段看做对象（不过这里用Map似乎更好？）。
                Token token = new Token();
                token.setToken(JWTUtils.createToken(user.getAccount()));
                status.setRemarks(token);
            } else {
                status.setCode(-1);
                status.setMessage("用户名或密码错误");
            }
        }
        return status;
    }

    /**
     * 注册服务，新增账号
     * 在验证当前用户名的账号不存在的情况下，为用户创建一个新的账户
     * @param user User实体类，由UserController从JSON字符串转换后塞入
     * @return 通用返回对象 成功注册返回code为0，用户名已存在返回-1，其他的sql错误导致的未插入则返回-2
     */
    public Status reg(User user) {
        // mybatis-plus 建立条件构造器 筛选account与传入User对象的account匹配的记录
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("account", user.getAccount());
        // 如果记录已存在（!= null）则说明该用户名已有用户，则返回-1
        if (userMapper.selectOne(queryWrapper) != null) {
            Status status = new Status();
            status.setCode(-1);
            status.setMessage("用户名已存在");
            return status;
        }
        // num为插入记录的数目，由于插入User单对象，插入成功为1，失败为0
        int num = userMapper.insert(user);
        Status status = new Status();
        // 通过num判断插入是否成功
        if (num >= 1) {
            status.setCode(0);
            status.setMessage("注册成功");
        } else {
            status.setCode(-2);
            status.setMessage("注册失败，未知错误");
        }
        return status;
    }
}
