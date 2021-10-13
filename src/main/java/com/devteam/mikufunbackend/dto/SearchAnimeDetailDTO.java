package com.devteam.mikufunbackend.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class SearchAnimeDetailDTO {

    /**
     * 作品ID
     */
    @JsonProperty("animeId")
    private Integer animeId;
    /**
     * 作品标题
     */
    @JsonProperty("animeTitle")
    private String animeTitle;
    /**
     * 作品类型
     * 'tvseries', 'tvspecial', 'ova', 'movie', 'musicvideo', 'web', 'other', 'jpmovie', 'jpdrama', 'unknown'
     */
    @JsonProperty("type")
    private String type;
    /**
     * 类型描述
     */
    @JsonProperty("typeDescription")
    private String typeDescription;
    /**
     * 海报图片地址
     */
    @JsonProperty("imageUrl")
    private String imageUrl;
    /**
     * 上映日期
     */
    @JsonProperty("startDate")
    private String startDate;
    /**
     * 剧集总数
     */
    @JsonProperty("episodeCount")
    private Integer episodeCount;
    /**
     * 此作品的综合评分（0-10）
     */
    @JsonProperty("rating")
    private Double rating;

}
