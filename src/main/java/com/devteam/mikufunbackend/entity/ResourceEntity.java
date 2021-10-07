package com.devteam.mikufunbackend.entity;

import com.devteam.mikufunbackend.util.ResultUtil;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.Accessors;

import java.sql.Timestamp;

/**
 * @author Jackisome
 * @date 2021/10/2
 */
@Data
@Builder
@Accessors(chain = true)
public class ResourceEntity {
    int fileId;
    String fileName;
    String fileDirectory;
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
    double danmakuShift;
    String gid;

    public FinishFileV0 getFinishFileV0() {
        return FinishFileV0.builder()
                .fileId(String.valueOf(this.getFileId()))
                .imageUrl(this.getImageUrl())
                .fileName(this.getFileName())
                .fileSize(this.getFileSize() / 1048576.0)
                .resourceName(this.getResourceName())
                .build();
    }

    public SimpleFinishFileV0 getSimpleFinishFileV0() {
        return SimpleFinishFileV0.builder()
                .fileId(String.valueOf(this.getFileId()))
                .fileName(this.getFileName())
                .delete(false)
                .build();
    }
}
