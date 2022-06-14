package com.yeck.SpringBoot_mybatis_plus_jwt_model.Model;

import lombok.Data;

/**
 * 返回给用户的通用结果实体对象
 * 大多数无直接返回值的接口均使用此对象进行返回，由RestController转换为JSON文本
 * @author YeEeck
 * @version 1.1, 2022-6-13
 */
@Data
public class Status {
    // 返回码
    private int code;
    private String message;
    private Object remarks;
}
