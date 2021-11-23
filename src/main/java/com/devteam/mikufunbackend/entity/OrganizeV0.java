package com.devteam.mikufunbackend.entity;

import lombok.Builder;
import lombok.Data;

import java.util.*;

/**
 * @author Jackisome
 * @date 2021/10/23
 */
@Data
@Builder
public class OrganizeV0 {
    String userImageUrl;
    String userPassword;
    List<String> visitorPasswords;
    String transferType;
    String fontSize;
    String fontColor;
    String fontBottom;
    boolean defaultStatus;
    String regex;
    String subscribeEmail;
    boolean danmakuTranslate;
    String danmakuBottom;
    String animeSearchApi;
}
