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
                .errorCode(0)
                .errorMessage("success")
                .data(object)
                .build();
        return response;
    }

    public static Response fail(int errorCode, String errorMessage) {
        Response response = Response.builder()
                .success(false)
                .errorCode(errorCode)
                .errorMessage(errorMessage)
                .build();
        return response;
    }

    public static Response fail(ResponseEnum responseEnum) {
        Response response = Response.builder()
                .success(false)
                .errorCode(responseEnum.getErrorCode())
                .errorMessage(responseEnum.getErrorMessage())
                .build();
        return response;
    }
}
