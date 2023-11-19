package com.devteam.mikufunbackend.entity;

import lombok.Builder;
import lombok.Data;

/**
 * @author Jackisome
 * @date 2021/10/4
 */
@Data
@Builder
public class DandanPlayMatchRequestVO {
    String fileName;
    String fileHash;
    long fileSize;
    int videoDuration;
    // 'hashAndFileName', 'fileNameOnly', 'hashOnly'
    String matchMode;
}
