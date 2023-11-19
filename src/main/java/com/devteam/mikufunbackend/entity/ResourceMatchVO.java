package com.devteam.mikufunbackend.entity;

import lombok.Builder;
import lombok.Data;

/**
 * @author Jackisome
 * @date 2021/10/4
 */
@Data
@Builder
public class ResourceMatchVO {
    int resourceId;
    String resourceName;
    String episodeTitle;
    String type;
    int episodeId;
    double shift;
}
