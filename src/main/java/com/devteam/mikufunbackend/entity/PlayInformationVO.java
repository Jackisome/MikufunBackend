package com.devteam.mikufunbackend.entity;

import lombok.Data;

/**
 * @author Jackisome
 * @date 2021/10/19
 */
@Data
public class PlayInformationVO {
    String fileUrl;
    String fileName;
    String resourceId;
    String resourceName;
    String subtitleUrl;
    double videoTime;
    String format;

    public PlayInformationVO(ResourceEntity resourceEntity) {
        this.fileUrl = "/docker/resource/" + resourceEntity.getFileUuid() + "/index." + resourceEntity.getTransferFormat();
        this.fileName = resourceEntity.getFileName();
        this.resourceId = String.valueOf(resourceEntity.getResourceId());
        this.resourceName = resourceEntity.getResourceName();
        this.subtitleUrl = resourceEntity.getSubtitlePath();
        this.videoTime = resourceEntity.getRecentPlayPosition();
        this.format = resourceEntity.getTransferFormat();
    }
}
