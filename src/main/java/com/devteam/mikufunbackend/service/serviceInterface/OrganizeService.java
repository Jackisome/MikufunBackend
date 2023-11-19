package com.devteam.mikufunbackend.service.serviceInterface;

import com.devteam.mikufunbackend.entity.OrganizeVO;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @author Jackisome
 * @date 2021/10/23
 */
public interface OrganizeService {
    OrganizeVO getProperty();

    void setProperty(String userPassword, List<String> visitorPasswords, String transferType, String fontSize,
                     String fontColor, String fontBottom, boolean defaultStatus, String regex, String subscribeEmail,
                     boolean danmakuTranslate, String danmakuBottom, String animeSearchApi);

    void setUserImage(MultipartFile image);
}
