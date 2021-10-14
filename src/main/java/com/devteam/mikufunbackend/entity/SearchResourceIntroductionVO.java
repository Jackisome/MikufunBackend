package com.devteam.mikufunbackend.entity;

import com.devteam.mikufunbackend.constant.FavoriteStatusEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class SearchResourceIntroductionVO {

    /**
     * 资源 Id
     */
    private String resourceId;
    /**
     * 资源标题
     */
    private String resourceName;
    /**
     * 上映日期
     */
    private Date airDate;
    /**
     * 番剧简介
     */
    private String description;
    /**
     * 海报图片地址
     */
    private String imageUrl;
    /**
     * 综合评分
     */
    private Double rating;
    /**
     * 播放状态
     *
     * 枚举 {@link FavoriteStatusEnum}
     */
    private String status;
}
