package com.devteam.mikufunbackend.interceptor;

import com.alibaba.fastjson.JSONObject;
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
public class UserInterceptor implements HandlerInterceptor {
    Logger logger = LoggerFactory.getLogger(UserInterceptor.class);

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

        if(!TokenUtil.validateUserToken(token)){
            if (!TokenUtil.validateVisitorToken(token)) {
                response.setCharacterEncoding("UTF-8");
                response.setContentType("application/json;charset=UTF-8");
                response.setStatus(HttpStatus.UNAUTHORIZED.value());
                response.getWriter().write(JSONObject.toJSONString(ResultUtil.fail(ResponseEnum.LOGIN_ERROR)));
                logger.info("user unauthorized");
            } else {
                response.setCharacterEncoding("UTF-8");
                response.setContentType("application/json;charset=UTF-8");
                response.setStatus(HttpStatus.FORBIDDEN.value());
                response.getWriter().write(JSONObject.toJSONString(ResultUtil.fail(ResponseEnum.VISITOR_STOP)));
                logger.info("visitor stop to visit");
            }
            return false;
        }
        logger.info("user authorized");
        return true;
    }
}
