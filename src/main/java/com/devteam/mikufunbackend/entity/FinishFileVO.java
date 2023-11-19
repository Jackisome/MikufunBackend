package com.devteam.mikufunbackend.entity;

import lombok.Builder;
import lombok.Data;

/**
 * @author Jackisome
 * @date 2021/10/2
 */
@Data
@Builder
public class FinishFileVO {
    String fileId;
    String imageUrl;
    String fileName;
    double fileSize;
    String resourceName;
    String episode;
    boolean played;
}
