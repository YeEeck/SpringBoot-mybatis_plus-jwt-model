package com.yeck.SpringBoot_mybatis_plus_jwt_model.Controller;

import com.google.gson.Gson;
import com.yeck.SpringBoot_mybatis_plus_jwt_model.Model.Status;
import com.yeck.SpringBoot_mybatis_plus_jwt_model.Model.User;
import com.yeck.SpringBoot_mybatis_plus_jwt_model.Service.UserService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 关于User请求的相关控制类
 * 控制有关于用户的接口的控制分流
 * @author Yeck, 2022-6-14
 * @version 1.1
 */
@RestController
public class UserController {

    /**
     * 调用对应的UserService
     * 使用Resource注解注入使用Service注解的UserService类
     */
    // Resource注解，调用Service
    @Resource
    private UserService userService;

    /**
     * 接收/login调用，并进行分流
     * 登录接口，接收包含用户名、密码的JSON字符串，转换为JAVA对象并分流至userService进行处理
     * @param jsonText 传入的json字符串
     * @return 通用返回对象 Status的remarks包含token
     * @see Status
     */
    @PostMapping("/login")
    public Status login(@RequestBody String jsonText) {
        // 将传入的json文本转换为JAVA对象后传入UserService
        Gson gson = new Gson();
        User user = gson.fromJson(jsonText, User.class);
        return userService.login(user);
    }

    /**
     * 接收/login调用，并进行分流
     * 登录接口，接收包含用户名、密码的JSON字符串，转换为JAVA对象并分流至userService进行处理
     * @param jsonText 传入的json字符串
     * @return 通用返回对象
     * @see Status
     */
    @PostMapping("/reg")
    public Status register(@RequestBody String jsonText) {
        // 将传入的json文本转换为JAVA对象后传入UserService
        Gson gson = new Gson();
        User user = gson.fromJson(jsonText, User.class);
        return userService.reg(user);
    }
}
