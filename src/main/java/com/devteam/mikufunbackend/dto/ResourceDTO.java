package com.devteam.mikufunbackend.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResourceDTO {
    /**
     * 资源标题
     */
    @JsonProperty("Title")
    private String fileName;
    /**
     * 资源大小
     */
    @JsonProperty("FileSize")
    private String size;

    /**
     * 磁力链接
     */
    @JsonProperty("Magnet")
    private String link;

    /**
     * 发布时间（格式为 yyyy-MM-dd HH:mm:ss)
     */
    @JsonProperty("PublishDate")
    private String publishDate;

    /**
     * 字幕组名称
     */
    @JsonProperty("SubgroupName")
    private String subGroupName;

    /**
     * 字幕组ID
     */
    @JsonProperty("SubgroupId")
    private String subGroupId;

    /**
     * 资源发布页面
     */
    @JsonProperty("PageUrl")
    private String pageUrl;

    /**
     * 资源类别ID
     */
    @JsonProperty("TypeId")
    private Integer typeId;

    /**
     * 资源类别名称
     */
    @JsonProperty("TypeName")
    private String typeName;
}
