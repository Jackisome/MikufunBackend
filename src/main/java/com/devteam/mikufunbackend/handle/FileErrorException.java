package com.devteam.mikufunbackend.handle;

/**
 * @author Jackisome
 * @date 2021/10/23
 */
public class FileErrorException extends RuntimeException {
    public FileErrorException(String info) {
        super(info);
    }
}
