package com.devteam.mikufunbackend.entity;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author Jackisome
 * @date 2021/10/1
 */
@Data
@Accessors(chain = true)
public class DownloadStatusEntity {
    String fileName;
    int fileId;
    int completedLength;
    int fileSize;
    int downloadSpeed;
    int uploadSpeed;
    String status;
}
