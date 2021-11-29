package com.devteam.mikufunbackend.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MatchEpisodePutReqVO {
    /**
     * 资源文件 id
     */
    @NotEmpty(message = "文件编号不能空")
    private String fileId;
    /**
     * 弹幕库 id
     */
    @NotEmpty(message = "弹幕库 id不能为空")
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
