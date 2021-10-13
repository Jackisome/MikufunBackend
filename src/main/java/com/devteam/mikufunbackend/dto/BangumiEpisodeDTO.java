package com.devteam.mikufunbackend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class BangumiEpisodeDTO {
    /**
     * 剧集ID（弹幕库编号）
     */
    private Integer episodeId;
    /**
     * 剧集完整标题
     */
    private String episodeTitle;
    /**
     * 剧集短标题（可以用来排序，非纯数字，可能包含字母）
     */
    private String episodeNumber;
    /**
     * 本集上映时间（当地时间）
     */
    private String airDate;

}
