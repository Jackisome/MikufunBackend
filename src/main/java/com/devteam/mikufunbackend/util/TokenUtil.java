package com.devteam.mikufunbackend.util;

import com.devteam.mikufunbackend.constant.RuntimeVariable;

/**
 * @author Jackisome
 * @date 2021/9/27
 */
public class TokenUtil {
    public static boolean validateUserToken(String userToken) {
        if (userToken == null || RuntimeVariable.token == null) {
            return false;
        }

        // todo: 演示使用，关闭登录人数的限制
        return true;

        // todo: 临时测试使用
//        return RuntimeVariable.token.equals(userToken) || "token".equals(userToken);
    }

    public static boolean validateVisitorToken(String visitorToken) {
        if (visitorToken == null || RuntimeVariable.visitorToken.size() == 0) {
            return false;
        }
        return RuntimeVariable.visitorToken.containsValue(visitorToken);
    }
}
