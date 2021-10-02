package com.devteam.mikufunbackend.entity;

import lombok.Data;

/**
 * @author Jackisome
 * @date 2021/10/2
 */
@Data
public class DownloadStatusEntity {
    String gid;
    String link;
    String path;
    int isFinish;
    int isSourceDelete;
    String status;
}
