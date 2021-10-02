package com.devteam.mikufunbackend.constant;

import lombok.Getter;

/**
 * @author Jackisome
 * @date 2021/9/26
 */
@Getter
public enum ResponseEnum {
    SUCCESS(0, "success"),
    UNKNOWN_ERROR(1, "unknown error"),
    LOGIN_ERROR(2, "login error"),
    ARIA2_ERROR(3, "aria2 error");

    private final int statusCode;
    private final String message;

    ResponseEnum(int statusCode, String message) {
        this.statusCode = statusCode;
        this.message = message;
    }
}
