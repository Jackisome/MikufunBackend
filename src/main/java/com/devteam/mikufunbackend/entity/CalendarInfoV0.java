package com.devteam.mikufunbackend.entity;

import lombok.Builder;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * @author Wooyuwen
 * @date 2021/10/05
 */
@Data
@Accessors(chain = true)
@Builder
public class CalendarInfoV0 {
    String resourceId;//一个番剧的标识，用于搜索时获取精确结果
    String resourceName; //番剧名
    Date airDate; //首次放送时间
    double rating; //十分制
    String imageUrl; //番剧图片地址
}
