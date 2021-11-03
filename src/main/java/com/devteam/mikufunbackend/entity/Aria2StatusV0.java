package com.devteam.mikufunbackend.entity;

import lombok.Data;

import java.util.List;

/**
 * @author Jackisome
 * @date 2021/10/2
 */
@Data
public class Aria2StatusV0 {
    long completedLength;
    long totalLength;
    int downloadSpeed;
    int uploadSpeed;
    List<Aria2FileV0> files;
    String gid;
    String status;
}
