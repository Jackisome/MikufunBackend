package com.devteam.mikufunbackend.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MatchEpisodePutReqVO {
    /**
     * 资源文件 id
     */
    private String fileId;
    /**
     * 弹幕库 id
     */
    private String episodeId;
    /**
     * 作品类别
     */
    private String resourceType;
    /**
     * 作品 id
     */
    private String resourceId;
    /**
     * 作品标题
     */
    private String resourceName;
    /**
     *  剧集标题
     */
    private String episode;
}
