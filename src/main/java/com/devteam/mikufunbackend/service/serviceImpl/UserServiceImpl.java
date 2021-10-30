package com.devteam.mikufunbackend.service.serviceImpl;

import com.devteam.mikufunbackend.constant.RuntimeVariable;
import com.devteam.mikufunbackend.constant.UserTypeEnum;
import com.devteam.mikufunbackend.entity.LoginV0;
import com.devteam.mikufunbackend.handle.PasswordErrorException;
import com.devteam.mikufunbackend.handle.TokenException;
import com.devteam.mikufunbackend.service.serviceInterface.UserService;
import com.devteam.mikufunbackend.util.ParamUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;

/**
 * @author Jackisome
 * @date 2021/10/14
 */
@Service
public class UserServiceImpl implements UserService {

    Logger logger = LoggerFactory.getLogger(UserService.class);

    @Override
    public UserTypeEnum validatePassword(String inputPassword) {
        if (!ParamUtil.isNotEmpty(inputPassword)) {
            throw new PasswordErrorException("输入密码为空!");
        }
        if (ParamUtil.isNotEmpty(RuntimeVariable.password)) {
            if (RuntimeVariable.password.equals(inputPassword)) {
                return UserTypeEnum.USER;
            } else if (RuntimeVariable.visitorPasswords.contains(inputPassword)) {
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

    @Override
    public boolean removeToken(HttpServletRequest request) {
        String token = getToken(request);
        if (!ParamUtil.isNotEmpty(token)) {
            throw new TokenException("token为空");
        }

        if (token.equals(RuntimeVariable.token)) {
            RuntimeVariable.token = null;
            logger.info("user logout");
            return true;
        } else {
            String visitorPassword = null;
            for (Map.Entry<String, String> entry : RuntimeVariable.visitorToken.entrySet()) {
                if (token.equals(entry.getValue())) {
                    visitorPassword = entry.getKey();
                    break;
                }
            }
            if (visitorPassword != null) {
                RuntimeVariable.visitorToken.remove(visitorPassword);
                logger.info("visitor logout, visitorPassword: {}", visitorPassword);
                return true;
            } else {
                throw new TokenException("传入token未找到");
            }
        }
    }

    @Override
    public String getToken(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        String token = null;
        if (cookies != null) {
            for (Cookie cookie: cookies) {
                if ("token".equals(cookie.getName())) {
                    token = cookie.getValue();
                }
            }
        }
        if (token != null) {
            logger.info("get token from request, token: {}, request: {}", token, request);
        } else {
            logger.info("get no token from request, request: {}", request);
        }
        return token;
    }


}
