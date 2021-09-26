package com.devteam.mikufunbackend.util;

/**
 * @author Jackisome
 * @date 2021/9/26
 */
public class ResultUtil {

    public static Response success() {
        return success(null);
    }

    public static Response success(Object object) {
        Response response = new Response();
        response.setSuccess(true);
        response.setErrorCode(0);
        response.setErrorMessage("success");
        response.setData(object);
        return response;
    }

    public static Response fail(int errorCode, String errorMessage) {
        Response response = new Response();
        response.setSuccess(false);
        response.setErrorCode(errorCode);
        response.setErrorMessage(errorMessage);
        return response;
    }
}
