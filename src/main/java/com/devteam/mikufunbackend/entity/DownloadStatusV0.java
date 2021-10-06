package com.devteam.mikufunbackend.entity;

import lombok.Builder;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author Jackisome
 * @date 2021/10/1
 */
@Data
@Accessors(chain = true)
@Builder
public class DownloadStatusV0 {
    String gid;
    String fileName;
    double completedLength;
    double fileSize;
    double downloadSpeed;
    double uploadSpeed;
    String status;
}
