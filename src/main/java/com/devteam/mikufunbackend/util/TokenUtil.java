package com.devteam.mikufunbackend.util;

import com.devteam.mikufunbackend.constant.RuntimeVariable;

/**
 * @author Jackisome
 * @date 2021/9/27
 */
public class TokenUtil {
    public static boolean validateToken(String userToken) {
        if (userToken == null || RuntimeVariable.token == null) {
            return false;
        }

        return RuntimeVariable.token.equals(userToken);
    }
}
