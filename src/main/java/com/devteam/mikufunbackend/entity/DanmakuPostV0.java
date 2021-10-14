package com.devteam.mikufunbackend.entity;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

/**
 * 增加弹幕
 */
public class DanmakuPostV0 {
    private String fileId,content,color;
    private Integer videoTime;
}
