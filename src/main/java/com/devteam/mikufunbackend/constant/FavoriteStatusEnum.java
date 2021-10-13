package com.devteam.mikufunbackend.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Objects;

@AllArgsConstructor
@Getter
public enum FavoriteStatusEnum {

    NOT_SET("no-set"),
    WANT_TO_WATCH("want-to-watch"),
    WATCHING("watching"),
    WATCHED("watched");

    private String value;

    public static Boolean checkStatusUpdate(FavoriteStatusEnum oldStatus, FavoriteStatusEnum newStatus) {
        // 检查状态更新是否合法
        if (Objects.equals(oldStatus, newStatus)) {
            return false;
        }
        if (Objects.equals(NOT_SET, newStatus)) {
            return false;
        }
        if (Objects.equals(WANT_TO_WATCH, newStatus)) {
            return false;
        }
        if (Objects.equals(WATCHED, oldStatus)) {
            return false;
        }
        return true;
    }

    public static FavoriteStatusEnum convert(String value) {
        for (FavoriteStatusEnum favoriteStatusEnum:FavoriteStatusEnum.values()) {
            if (Objects.equals(favoriteStatusEnum.getValue(), value)) {
                return favoriteStatusEnum;
            }
        }
        return null;
    }
}
