package com.devteam.mikufunbackend.util;

import com.devteam.mikufunbackend.constant.ResponseEnum;

/**
 * @author Jackisome
 * @date 2021/9/26
 */
public class ResultUtil {

    public static Response success() {
        return success(null);
    }

    public static Response success(Object object) {
        Response response = Response.builder()
                .success(true)
                .statusCode(ResponseEnum.SUCCESS.getStatusCode())
                .message(ResponseEnum.SUCCESS.getMessage())
                .data(object)
                .build();
        return response;
    }

    public static Response fail(int statusCode, String message) {
        Response response = Response.builder()
                .success(false)
                .statusCode(statusCode)
                .message(message)
                .build();
        return response;
    }

    public static Response fail(ResponseEnum responseEnum) {
        Response response = Response.builder()
                .success(false)
                .statusCode(responseEnum.getStatusCode())
                .message(responseEnum.getMessage())
                .build();
        return response;
    }
}
