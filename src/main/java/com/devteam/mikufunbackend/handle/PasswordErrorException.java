package com.devteam.mikufunbackend.handle;

/**
 * @author Jackisome
 * @date 2021/10/14
 */
public class PasswordErrorException extends RuntimeException {
    public PasswordErrorException(String info) {
        super(info);
    }
}
