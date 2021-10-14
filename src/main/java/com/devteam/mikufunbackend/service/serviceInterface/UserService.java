package com.devteam.mikufunbackend.service.serviceInterface;

/**
 * @author Jackisome
 * @date 2021/10/14
 */
public interface UserService {
    boolean validatePassword(String inputPassword);

    String getAuthToken();
}
