package com.devteam.mikufunbackend.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SearchSubGroupRespVO {

    /**
     * 字幕组编号
     */
    private String id;

    /**
     * 字幕组名称
     */
    private String name;
}
