package com.devteam.mikufunbackend.entity;

import lombok.Data;

import java.util.List;

/**
 * @author Jackisome
 * @date 2021/10/2
 */
@Data
public class Aria2StatusVO {
    long completedLength;
    long totalLength;
    int downloadSpeed;
    int uploadSpeed;
    List<Aria2FileVO> files;
    String gid;
    String status;
    String dir;
}
