package com.devteam.mikufunbackend.handle;

/**
 * @author Jackisome
 * @date 2021/10/22
 */
public class ParameterErrorException extends RuntimeException {
    public ParameterErrorException(String info) {
        super(info);
    }
}
