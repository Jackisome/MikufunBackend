package com.devteam.mikufunbackend.entity;

import lombok.Builder;
import lombok.Data;

/**
 * @author Jackisome
 * @date 2021/10/2
 */
@Data
@Builder
public class DownloadStatusEntity {
    String gid;
    String link;
    String fileName;
    String filePath;
    int isFinish;
    int isSourceDelete;
    String status;
}
