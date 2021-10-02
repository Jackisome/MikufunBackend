package com.devteam.mikufunbackend.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.text.DecimalFormat;

/**
 * @author Jackisome
 * @date 2021/9/30
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DanmakuV0 {
    double time;
    int type;
    int color;
    String colorHex;
    String comment;

    @Override
    public String toString() {
        return "[" +
                new DecimalFormat("0.000").format(time) + "," +
                type + "," +
                color + "," +
                colorHex + "," +
                comment +
                "]";

    }
}
