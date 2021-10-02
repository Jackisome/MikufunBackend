package com.devteam.mikufunbackend.entity;

import com.devteam.mikufunbackend.util.ResultUtil;
import lombok.Data;

import java.sql.Timestamp;

/**
 * @author Jackisome
 * @date 2021/10/2
 */
@Data
public class ResourceEntity {
    int fileId;
    String filePath;
    String fileHash;
    int fileSize;
    int videoDuration;
    String imageUrl;
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

    public FinishFileV0 getFinishFileV0() {
        return FinishFileV0.builder()
                .fileId(this.getFileId())
                .imageUrl(this.getImageUrl())
                .fileName(ResultUtil.getFileName(this.getFilePath()))
                .fileSize(this.getFileSize() / 1048576.0)
                .resourceName(this.getResourceName())
                .build();
    }
}
