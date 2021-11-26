package com.devteam.mikufunbackend.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class MatchSubtitleReqVO {

    @NotNull(message = "文件编号不能为空")
    private Integer fileId;

    @NotNull(message = "字幕信息不能为空")
    private String subtitleName;
}
