package com.devteam.mikufunbackend.entity;

import lombok.Data;

import java.sql.Timestamp;

/**
 * @author Jackisome
 * @date 2021/10/2
 */
@Data
public class ResourceEntity {
    int fileId;
    String fileName;
    String fileHash;
    int fileSize;
    int videoDuration;
    Timestamp recentPlayTime;
    int recentPlayPosition;
    Timestamp downloadTime;
    int exactMatch;
    int resourceId;
    String resourceName;
    String episodeTitle;
    String type;
    int episodeId;
    String gid;
}
