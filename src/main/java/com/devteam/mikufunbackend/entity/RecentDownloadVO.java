package com.devteam.mikufunbackend.entity;

import lombok.Builder;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author Wooyuwen
 * @date 2021-10-06
 */
@Data
@Accessors(chain = true)
@Builder
public class RecentDownloadVO {
    String fileId;
    String fileName;
    String resourceName;
    String time;
    String imageUrl;
    String episode;
}
