package com.devteam.mikufunbackend.entity;

import lombok.*;

/**
 * @author Jackisome
 * @date 2021/9/30
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DanmakuVO {
    double time;
    int mode;
    int color;
    //String colorHex;
    String message;
    /*
    @Override
    public String toString() {
        return "[" +
                new DecimalFormat("0.000").format(time) + "," +
                mode + "," +
                color + "," +
                // colorHex + "," +
                message +
                "]";

    }
     */
}
