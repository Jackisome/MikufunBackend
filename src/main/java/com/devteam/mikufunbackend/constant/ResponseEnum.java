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
    ARIA2_ERROR(3, "aria2 error"),
    FILEID_ERROR(4, "fileId error"),
    SHELL_ERROR(5, "shell execute error"),
    FAVORITE_ERROR(6, "favorite status update error"),
    PARAMETER_ERROR(7, "parameter error"),
    AUTO_DOWNLOAD_ERROR(8, "auto download error"),
    FILE_ERROR(9, "file error"),
    DOWNLOADED_EXCEPTION(10, "downloaded exception"),
    ORGANIZE_ERROR(11, "organize error"),
    VISITOR_STOP(12, "访客无权访问"),
    TOKEN_ERROR(13, "token_error"),
    FILE_UPLOAD_ERROR(14, "file upload error");

    private final int statusCode;
    private final String message;

    ResponseEnum(int statusCode, String message) {
        this.statusCode = statusCode;
        this.message = message;
    }
}
