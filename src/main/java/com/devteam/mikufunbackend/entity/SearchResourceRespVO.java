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
     * 资源更新时间， 2000-01-01 00:00:00
     */
    private String publishDate;

}
