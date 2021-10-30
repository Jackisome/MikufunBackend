package com.devteam.mikufunbackend.service.serviceInterface;

import com.devteam.mikufunbackend.constant.UserTypeEnum;
import com.devteam.mikufunbackend.entity.LoginV0;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Jackisome
 * @date 2021/10/14
 */
public interface UserService {
    UserTypeEnum validatePassword(String inputPassword);

    LoginV0 getAuthToken(String inputPassword);

    boolean removeToken(HttpServletRequest request);

    String getToken(HttpServletRequest request);
}
