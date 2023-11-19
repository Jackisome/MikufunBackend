package com.devteam.mikufunbackend.entity;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.util.*;

/**
 * @author Jackisome
 * @date 2021/10/23
 */
@Data
@Builder
public class OrganizeVO implements Serializable {
    String userImageUrl;
    String userPassword;
    List<String> visitorPasswords;
    String transferType;
    String fontSize;
    String fontColor;
    String fontBottom;
    Boolean defaultStatus;
    String regex;
    String subscribeEmail;
    Boolean danmakuTranslate;
    String danmakuBottom;
    String animeSearchApi;
}
