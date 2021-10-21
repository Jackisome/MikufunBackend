package com.devteam.mikufunbackend.constant;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * @author Jackisome
 * @date 2021/9/27
 */
public class RuntimeVariable {
    // todo: 临时设为"token"用于测试，需默认为null
    // 表示用户sessionId，运行时可变
    public static String token = "token";

    public static String password = null;

    public static Set<String> visitorPassword = new HashSet<>(3);

    public static Map<String, String> visitorToken = new HashMap<>(3);
}
