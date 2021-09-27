package com.devteam.mikufunbackend.interceptor;

import com.devteam.mikufunbackend.constant.ResponseEnum;
import com.devteam.mikufunbackend.util.ResultUtil;
import com.devteam.mikufunbackend.util.TokenUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author Jackisome
 * @date 2021/9/27
 * controller拦截器，用于用户登录状态验证
 */
@Component
public class SignInterceptor implements HandlerInterceptor {
    Logger logger = LoggerFactory.getLogger(SignInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws IOException {
        logger.info("begin to validate user token");
        Cookie[] cookies = request.getCookies();
        String token = null;
        if (cookies != null) {
            for (Cookie cookie: cookies) {
                if ("token".equals(cookie.getName())) {
                    token = cookie.getValue();
                }
            }
        }

        if(!TokenUtil.validateToken(token)){
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/json;charset=UTF-8");
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            response.getWriter().write(ResultUtil.fail(ResponseEnum.LOGIN_ERROR).toString());
            logger.info("unauthorized");
            return false;
        }
        logger.info("authorized");
        return true;
    }
}
