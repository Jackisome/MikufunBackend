package com.devteam.mikufunbackend.entity;

import lombok.Builder;
import lombok.Data;

/**
 * @author Jackisome
 * @date 2021/10/21
 */
@Data
@Builder
public class FreeDownloadFileV0 {
    String fileName;
    String fileSize;
    String fileUrl;
}
