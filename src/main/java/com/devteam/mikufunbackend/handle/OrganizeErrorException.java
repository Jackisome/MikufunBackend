package com.devteam.mikufunbackend.handle;

/**
 * @author Jackisome
 * @date 2021/10/23
 */
public class OrganizeErrorException extends RuntimeException {
    public OrganizeErrorException(String info) {
        super(info);
    }
}
