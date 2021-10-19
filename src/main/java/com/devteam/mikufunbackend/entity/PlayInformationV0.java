package com.devteam.mikufunbackend.entity;

import lombok.Data;

/**
 * @author Jackisome
 * @date 2021/10/19
 */
@Data
public class PlayInformationV0 {
    String fileUrl;
    String fileName;
    String resourceId;
    String resourceName;
    String subtitleUrl;
    int videoTime;
    String format;

    public PlayInformationV0(ResourceEntity resourceEntity) {
        this.fileUrl = "/docker/resource/" + resourceEntity.getFileUuid() + "/index." + resourceEntity.getTransferFormat();
        this.fileName = resourceEntity.getFileName();
        this.resourceId = String.valueOf(resourceEntity.getResourceId());
        this.resourceName = resourceEntity.getResourceName();
        this.subtitleUrl = resourceEntity.getSubtitlePath();
        this.videoTime = resourceEntity.getRecentPlayPosition();
        this.format = resourceEntity.getTransferFormat();
    }
}
