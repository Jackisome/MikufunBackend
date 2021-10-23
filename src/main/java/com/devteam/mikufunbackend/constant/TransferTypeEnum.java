package com.devteam.mikufunbackend.constant;

import lombok.Getter;
import org.springframework.stereotype.Component;

/**
 * @author Jackisome
 * @date 2021/10/23
 */
@Getter
public enum TransferTypeEnum {
    MP4("mp4"),
    HLS("m3u8"),
    DASH("mpd");

    private final String format;

    TransferTypeEnum(String format) {
        this.format = format;
    }

    public static TransferTypeEnum fromString(String format) {
        for (TransferTypeEnum value : TransferTypeEnum.values()) {
            if (value.getFormat().equals(format)) {
                return value;
            }
        }
        return null;
    }
}
