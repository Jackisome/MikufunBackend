package com.devteam.mikufunbackend.service.serviceImpl;

import com.devteam.mikufunbackend.constant.RuntimeVariable;
import com.devteam.mikufunbackend.constant.UserTypeEnum;
import com.devteam.mikufunbackend.entity.LoginV0;
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
    public UserTypeEnum validatePassword(String inputPassword) {
        if (!ParamUtil.isNotEmpty(inputPassword)) {
            throw new PasswordErrorException("输入密码为空!");
        }
        if (ParamUtil.isNotEmpty(RuntimeVariable.password)) {
            if (RuntimeVariable.password.equals(inputPassword)) {
                return UserTypeEnum.USER;
            } else if (RuntimeVariable.visitorPassword.contains(inputPassword)) {
                return UserTypeEnum.VISITOR;
            } else {
                throw new PasswordErrorException("密码不正确!");
            }
        } else {
            RuntimeVariable.password = inputPassword;
            return UserTypeEnum.USER;
        }
    }

    @Override
    public LoginV0 getAuthToken(String inputPassword) {
        UserTypeEnum userTypeEnum = validatePassword(inputPassword);
        String uuid = UUID.randomUUID().toString();
        switch (userTypeEnum) {
            case USER:
                RuntimeVariable.token = uuid;
                return LoginV0.builder()
                        .token(uuid)
                        .visitor(false)
                        .build();
            case VISITOR:
                RuntimeVariable.visitorToken.put(inputPassword, uuid);
                return LoginV0.builder()
                        .token(uuid)
                        .visitor(true)
                        .build();
        }
        return LoginV0.builder()
                .token("")
                .visitor(true)
                .build();
    }
}
