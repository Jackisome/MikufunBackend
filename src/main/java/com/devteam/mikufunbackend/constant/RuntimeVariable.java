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

    // 用户密码
    public static String password = null;

    // 访客密码
    public static Set<String> visitorPasswords = new HashSet<>(3);

    // 访客密码和访客token
    public static Map<String, String> visitorToken = new HashMap<>(3);

    // 转码方式
    public static TransferTypeEnum transferType = TransferTypeEnum.HLS;

    // 字幕字体大小
    public static String fontSize = "20px";

    // 字幕颜色
    public static String fontColor = "#ffffff";

    // 字幕距播放器底部距离
    public static String fontBottom = "10px";

    // 是否启用弹幕过滤规则
    public static boolean defaultStatus = false;

    // 弹幕过滤正则表达式
    public static String regex = null;

    // 订阅自动下载更新信息的邮箱
    public static String subscribeEmail = "";

    // 是否启用弹幕简繁体的转换
    public static boolean danmakuTranslate = false;

    // 弹幕距离播放器底部的距离
    public static String danmakuBottom = "10px";

    // 资源搜索接口的地址
    public static String animeSearchApi = "https://k.dandanplay.workers.dev";

    // 用户头像名称
    public static String userImageName = "default.jpg";
}
