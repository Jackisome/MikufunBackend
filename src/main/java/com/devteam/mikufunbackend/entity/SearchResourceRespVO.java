package com.devteam.mikufunbackend.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SearchResourceRespVO {
    /**
     * 文件名
     */
    private String fileName;
    /**
     * 文件大小
     */
    private String size;
    /**
     * 磁力链接
     */
    private String link;

    /**
     * 下载状态
     *
     * enum {'undownload', 'downloading', 'downloaded'}
     */
    private String downloadStatus;

}
