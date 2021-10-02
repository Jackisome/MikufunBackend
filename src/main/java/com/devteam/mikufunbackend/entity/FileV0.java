package com.devteam.mikufunbackend.entity;

import lombok.Data;

/**
 * @author Jackisome
 * @date 2021/10/2
 */
@Data
public class FileV0 {
    int index;
    String path;
    int completedLength;
    int length;
    boolean selected;

}
