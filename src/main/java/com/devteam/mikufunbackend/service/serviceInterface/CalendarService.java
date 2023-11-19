package com.devteam.mikufunbackend.service.serviceInterface;

import com.devteam.mikufunbackend.constant.AnimeTypeEnum;
import com.devteam.mikufunbackend.entity.CalendarInfoVO;

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
    List<CalendarInfoVO> calendar(AnimeTypeEnum type, int week) throws Exception;
}
