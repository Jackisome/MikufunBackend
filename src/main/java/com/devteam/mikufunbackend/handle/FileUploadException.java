package com.devteam.mikufunbackend.handle;

/**
 * @author Jackisome
 * @date 2021/10/30
 */
public class FileUploadException extends RuntimeException {
    public FileUploadException(String info) {
        super(info);
    }
}
