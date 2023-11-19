package com.devteam.mikufunbackend.entity;

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
    String fileUuid;
    String fileHash;
    long fileSize;
    String srcFilePath;
    String transferFormat;
    int videoDuration;
    String imageUrl;
    String subtitlePath;
    Timestamp recentPlayTime;
    double recentPlayPosition;
    Timestamp downloadTime;
    int exactMatch;
    int resourceId;
    String resourceName;
    String episodeTitle;
    String type;
    int episodeId;
    double danmakuShift;
    String gid;

    public FinishFileVO getFinishFileV0() {
        return FinishFileVO.builder()
                .fileId(String.valueOf(this.getFileId()))
                .imageUrl(this.getImageUrl())
                .fileName(this.getFileName())
                .fileSize(this.getFileSize() / 1048576.0)
                .resourceName(this.getResourceName())
                .episode(this.getEpisodeTitle())
                .played(this.recentPlayTime.after(Timestamp.valueOf("2001-01-01 01:00:00.0")))
                .build();
    }

    public SimpleFinishFileVO getSimpleFinishFileV0() {
        return SimpleFinishFileVO.builder()
                .fileId(String.valueOf(this.getFileId()))
                .fileName(this.getFileName())
                .delete(false)
                .build();
    }
}
