package com.devteam.mikufunbackend.util;

import com.devteam.mikufunbackend.constant.ResponseEnum;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Jackisome
 * @date 2021/9/26
 */
public class ResultUtil {

    public static Response success() {
        return success(null);
    }

    public static Response success(Map<String, Object> data) {
        Response response = Response.builder()
                .success(true)
                .statusCode(ResponseEnum.SUCCESS.getStatusCode())
                .message(ResponseEnum.SUCCESS.getMessage())
                .data(data)
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

    public static Map<String, Object> getData() {
        return new HashMap<>();
    }

    public static String getFileName(String path) {
        if (path == null) {
            return "";
        }
        String[] strings = path.split("/");
        return strings[strings.length - 1];
    }
}
