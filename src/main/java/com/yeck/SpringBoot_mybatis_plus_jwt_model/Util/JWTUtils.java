package com.yeck.SpringBoot_mybatis_plus_jwt_model.Util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.TokenExpiredException;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import java.util.Date;


/**
 * JWT工具类
 * 封装JWT，代码分层；ConfigurationProperties读application.properties配置文件参数
 * @author Yeck, 2022-6-14
 * @version 1.1
 */
@Component
@ConfigurationProperties(prefix = "jwt")
public class JWTUtils {

    /**
     * 定义token返回头部
     */
    public static String header;

    /**
     * token前缀
     */
    public static String tokenPrefix;

    /**
     * 签名密钥
     */
    public static String secret;

    /**
     * 有效期（以分钟记）
     */
    public static long expireTime;

    /**
     * 存进客户端的token的key名
     */
    public static final String USER_LOGIN_TOKEN = "USER_LOGIN_TOKEN";

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        JWTUtils.header = header;
    }

    public void setTokenPrefix(String tokenPrefix) {
        JWTUtils.tokenPrefix = tokenPrefix;
    }

    public void setSecret(String secret) {
        JWTUtils.secret = secret;
    }

    public void setExpireTime(int expireTimeInt) {
        JWTUtils.expireTime = expireTimeInt * 1000L * 60;
    }

    /**
     * 创建TOKEN
     *
     * @param sub 存入token的数据
     * @return 保存了传入数据的token
     */
    public static String createToken(String sub) {
        return tokenPrefix + JWT.create()
                .withSubject(sub)
                .withExpiresAt(new Date(System.currentTimeMillis() + expireTime))
                .sign(Algorithm.HMAC512(secret));
    }


    /**
     * 验证token
     *
     * @param token
     */
    public static String validateToken(String token) throws ApiException {
        try {
            return JWT.require(Algorithm.HMAC512(secret))
                    .build()
                    .verify(token.replace(tokenPrefix, ""))
                    .getSubject();
        } catch (TokenExpiredException e) {
            throw new ApiException("token已经过期");
        } catch (Exception e) {
            throw new ApiException("token验证失败");
        }
    }

    /**
     * 检查token是否需要更新
     *
     * @param token 需要检查更新的token
     * @return
     */
    public static boolean isNeedUpdate(String token) throws ApiException {
        //获取token过期时间
        Date expiresAt = null;
        try {
            expiresAt = JWT.require(Algorithm.HMAC512(secret))
                    .build()
                    .verify(token.replace(tokenPrefix, ""))
                    .getExpiresAt();
        } catch (TokenExpiredException e) {
            return true;
        } catch (Exception e) {
            throw new ApiException("token验证失败");
        }

        // 如果剩余过期时间少于过期时长的一半时 需要更新
        // 2022-6-13 居然用位运算折半？
        return (expiresAt.getTime() - System.currentTimeMillis()) < (expireTime >> 1);
    }
}