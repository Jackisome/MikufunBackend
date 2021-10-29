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

/**
 * @author Jackisome
 * @date 2021/10/22
 */
@Component
public class VisitorInterceptor implements HandlerInterceptor {
    Logger logger = LoggerFactory.getLogger(VisitorInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        logger.info("begin validate user or visitor");
        Cookie[] cookies = request.getCookies();
        String token = null;
        if (cookies != null) {
            for (Cookie cookie: cookies) {
                if ("token".equals(cookie.getName())) {
                    token = cookie.getValue();
                }
            }
        }

        if(!TokenUtil.validateUserToken(token) && !TokenUtil.validateVisitorToken(token)){
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/json;charset=UTF-8");
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            response.getWriter().write(JSONObject.toJSONString(ResultUtil.fail(ResponseEnum.LOGIN_ERROR)));
            logger.info("user or visitor unauthorized");
            return false;
        }
        logger.info("user or visitor authorized");
        return true;
    }
}
