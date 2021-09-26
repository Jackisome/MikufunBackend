package com.devteam.mikufunbackend.constant;

/**
 * @author Jackisome
 * @date 2021/9/26
 */
public enum ResponseEnum {
    UNKNOWN_ERROR(-1, "unknown error"),
    SUCCESS(0, "success");

    private final int errorCode;
    private final String errorMessage;

    ResponseEnum(int errorCode, String errorMessage) {
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}
