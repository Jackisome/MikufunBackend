package com.devteam.mikufunbackend.service.serviceInterface;

import com.devteam.mikufunbackend.constant.UserTypeEnum;
import com.devteam.mikufunbackend.entity.LoginVO;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Jackisome
 * @date 2021/10/14
 */
public interface UserService {
    UserTypeEnum validatePassword(String inputPassword);

    LoginVO getAuthToken(String inputPassword);

    boolean removeToken(HttpServletRequest request);

    String getToken(HttpServletRequest request);

    String getUserImageUrl();
}
