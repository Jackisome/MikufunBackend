package com.devteam.mikufunbackend.entity;

import lombok.Builder;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author Wooyuwen
 * @date 2021-10-24
 */
@Data
@Accessors(chain = true)
@Builder
public class RecentPlayVO {
    String fileId;
    String fileName;
    String resourceName;
    String time;
    String imageUrl;
    String episode;
    String videoTime;
}
