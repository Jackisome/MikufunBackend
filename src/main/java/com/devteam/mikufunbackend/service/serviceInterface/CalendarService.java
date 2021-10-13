package com.devteam.mikufunbackend.service.serviceInterface;

import com.devteam.mikufunbackend.constant.AnimeTypeEnum;
import com.devteam.mikufunbackend.entity.CalendarInfoV0;

import java.util.List;

/**
 * @author Wooyuwen
 * @date 2021/10/4
 */
public interface CalendarService {
    /**
     * 获取新番时间表
     * @return
     */
    List<CalendarInfoV0> calendar(AnimeTypeEnum type, int week) throws Exception;
}
