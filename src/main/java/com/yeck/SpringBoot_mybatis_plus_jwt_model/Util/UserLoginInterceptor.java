package com.yeck.SpringBoot_mybatis_plus_jwt_model.Util;

import com.google.gson.Gson;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

/**
 * 拦截器：验证用户是否登录
 * 通过拦截器进行对用户Token的验证
 *
 * @author Yeck, 2022-6-14
 * @version 1.1
 */
public class UserLoginInterceptor implements HandlerInterceptor {
    /**
     * 拦截请求后执行逻辑
     * 重写preHandle方法，在请求被执行前拦截请求，并进行Token验证。
     *
     * @param request  前端传入的request请求
     * @param response 后端传回的response响应
     * @param handler
     * @return 如果返回true，则继续执行到Controller；如果返回false，则拦截请求，直接返回到前端
     * @throws ApiException 自己写的类，覆盖Exception(String)构造方法
     * @throws IOException  使用Writer写入response，需要抛出IOException
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws ApiException, IOException {
        // 从http的header中获得token
        String token = request.getHeader(JWTUtils.header);
        // token不存在
        if (token == null || token.equals("")) {
            // 返回状态码设置为401
            response.setStatus(401);
            // 构造传回去的请求头，由于是回传json文本，这里设置为text/json
            response.setHeader("Content-Type", "text/json;charset=utf-8");
            // 通过Writer写入response，构造Map对象，使用Gson转化为JSON字符串
            Writer writer = response.getWriter();
            Map<String, String> map = new HashMap<>();
            map.put("message", "token不存在或已过期");
            Gson gson = new Gson();
            writer.write(gson.toJson(map));
            // 返回false，截断请求
            return false;
        }
        //验证token
        String sub = JWTUtils.validateToken(token);
        if (sub == null || sub.equals("")) {
            // 返回状态码设置为401
            response.setStatus(401);
            // 构造传回去的请求头，由于是回传json文本，这里设置为text/json
            response.setHeader("Content-Type", "text/json;charset=utf-8");
            // 通过Writer写入response，构造Map对象，使用Gson转化为JSON字符串
            Writer writer = response.getWriter();
            Map<String, String> map = new HashMap<>();
            map.put("message", "token不存在或已过期");
            Gson gson = new Gson();
            writer.write(gson.toJson(map));
            // 返回false，截断请求
            return false;
        }
        //更新token有效时间 (如果需要更新其实就是产生一个新的token)
        if (JWTUtils.isNeedUpdate(token)) {
            String newToken = JWTUtils.createToken(sub);
            response.setHeader(JWTUtils.USER_LOGIN_TOKEN, newToken);
        }
        return true;
    }
}