package com.devteam.mikufunbackend.entity;

import lombok.Data;

/**
 * @author Jackisome
 * @date 2021/10/2
 */
@Data
public class Aria2FileV0 {
    int index;
    String path;
    long completedLength;
    long length;
    boolean selected;
}
