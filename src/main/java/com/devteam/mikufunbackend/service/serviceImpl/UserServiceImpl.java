package com.devteam.mikufunbackend.service.serviceImpl;

import com.devteam.mikufunbackend.constant.RuntimeVariable;
import com.devteam.mikufunbackend.handle.PasswordErrorException;
import com.devteam.mikufunbackend.service.serviceInterface.UserService;
import com.devteam.mikufunbackend.util.ParamUtil;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * @author Jackisome
 * @date 2021/10/14
 */
@Service
public class UserServiceImpl implements UserService {
    @Override
    public boolean validatePassword(String inputPassword) {
        if (!ParamUtil.isNotEmpty(inputPassword)) {
            throw new PasswordErrorException("输入密码为空!");
        }
        if (ParamUtil.isNotEmpty(RuntimeVariable.password)) {
            if (!RuntimeVariable.password.equals(inputPassword)) {
                throw new PasswordErrorException("密码不正确!");
            } else {
                return true;
            }
        } else {
            RuntimeVariable.password = inputPassword;
            return true;
        }
    }

    @Override
    public String getAuthToken() {
        RuntimeVariable.token = UUID.randomUUID().toString();
        return RuntimeVariable.token;
    }
}
