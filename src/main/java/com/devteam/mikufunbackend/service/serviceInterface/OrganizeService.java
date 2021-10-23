package com.devteam.mikufunbackend.service.serviceInterface;

import com.devteam.mikufunbackend.entity.OrganizeV0;

import java.util.List;

/**
 * @author Jackisome
 * @date 2021/10/23
 */
public interface OrganizeService {
    OrganizeV0 getProperty();

    void setProperty(String userPassword, List<String> visitorPasswords, String transferType, String fontSize,
                     String fontColor, String fontBottom, boolean defaultStatus, String regex);
}
