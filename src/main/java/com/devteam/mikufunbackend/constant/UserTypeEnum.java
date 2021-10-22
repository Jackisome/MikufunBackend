package com.devteam.mikufunbackend.constant;

import lombok.Getter;

/**
 * @author Jackisome
 * @date 2021/10/22
 */
@Getter
public enum UserTypeEnum {
    USER("user"),
    VISITOR("visitor");

    private final String userType;

    UserTypeEnum(String userType) {
        this.userType = userType;
    }
}
