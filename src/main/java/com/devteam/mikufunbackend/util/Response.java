package com.devteam.mikufunbackend.util;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.util.Map;

/**
 * @author Jackisome
 * @date 2021/9/26
 * 封装响应数据
 */
@Data
@Builder
public class Response implements Serializable {

    private boolean success;
    private Map<String, Object> data;
    private int statusCode;
    private String message;
}
