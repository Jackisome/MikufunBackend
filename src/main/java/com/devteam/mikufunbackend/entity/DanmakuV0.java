package com.devteam.mikufunbackend.entity;

import lombok.*;

import java.text.DecimalFormat;

/**
 * @author Jackisome
 * @date 2021/9/30
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DanmakuV0 {
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
