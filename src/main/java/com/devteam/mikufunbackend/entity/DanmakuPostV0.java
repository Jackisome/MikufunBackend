package com.devteam.mikufunbackend.entity;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

/**
 * 增加弹幕
 */
@Data
public class DanmakuPostV0 {
    String id;
    String author;
    double time;
    String text;
    int color;
    int type;
}
