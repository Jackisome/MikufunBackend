package com.devteam.mikufunbackend.util;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

/**
 * @author Jackisome
 * @date 2021/9/26
 * 封装响应数据
 */
@Data
@Builder
public class Response<T> implements Serializable {

    private boolean success;
    private T data;
    private int errorCode;
    private String errorMessage;
}
