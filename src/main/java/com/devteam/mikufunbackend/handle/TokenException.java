package com.devteam.mikufunbackend.handle;

/**
 * @author Jackisome
 * @date 2021/10/30
 */
public class TokenException extends RuntimeException {
    public TokenException(String info) {
        super(info);
    }
}
