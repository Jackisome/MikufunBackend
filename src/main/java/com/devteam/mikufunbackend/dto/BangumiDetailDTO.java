package com.devteam.mikufunbackend.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.util.CollectionUtils;

import java.text.DateFormat;
import java.text.ParseException;
import java.util.Date;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class BangumiDetailDTO {
    /**
     * 作品类型
     */
    @JsonProperty("type")
    private String type;
    /**
     * 类型描述
     */
    @JsonProperty("typeDescription")
    private String typeDescription;
    /**
     * 剧集列表
     */
    private List<BangumiEpisodeDTO> episodes;
    /**
     * 番剧简介
     */
    @JsonProperty("summary")
    private String description;
    /**
     * 番剧元数据（名称、制作人员、配音人员等）
     */
    private List<String> metadata;
    /**
     * 作品编号
     */
    @JsonProperty("animeId")
    private Integer resourceId;
    /**
     * 作品标题
     */
    @JsonProperty("animeTitle")
    private String resourceName;
    /**
     * 海报图片地址
     */
    @JsonProperty("imageUrl")
    private String imageUrl;
    /**
     * 搜索关键词
     */
    @JsonProperty("searchKeyword")
    private String searchKeyword;
    /**
     * 是否正在连载中
     */
    private Boolean isOnair;
    /**
     * 周几上映，0代表周日，1-6代表周一至周六
     */
    private Integer airDay;
    /**
     * 番剧综合评分（综合多个来源的评分求出的加权平均值，0-10分）
     */
    @JsonProperty("rating")
    private Double rating;


    public String reformatAirDate(DateFormat fmt) {
        // 获取指定格式的日期, 番剧第一集的播出时间作为番剧的上映时间
        if (CollectionUtils.isEmpty(episodes)) {
            return "";
        }
        if (episodes.get(0).getAirDate() == null) {
            return "";
        }
        // todo
        try {
            Date airDate = fmt.parse(episodes.get(0).getAirDate());
            return fmt.format(airDate);
        } catch (ParseException e) {
            e.printStackTrace();
            return "";
        }
    }

}
