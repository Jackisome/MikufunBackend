package com.devteam.mikufunbackend.entity;

import lombok.*;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class FavoriteStatusRecordEntity {
    /**
     * id
     */
    private Integer recordId;

    /**
     * 番剧标识
     */
    private Integer resourceId;

    /**
     * 收藏状态
     */
    private String status;

    /**
     * 更新时间
     */
    private Date updateTime;
}