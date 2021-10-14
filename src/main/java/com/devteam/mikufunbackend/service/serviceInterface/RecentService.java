package com.devteam.mikufunbackend.service.serviceInterface;

import com.devteam.mikufunbackend.constant.AnimeTypeEnum;
import com.devteam.mikufunbackend.entity.CalendarInfoV0;
import com.devteam.mikufunbackend.entity.RecentStatusV0;

import java.util.List;

/**
 * @author Wooyuwen
 * @date 2021/10/04
 */
public interface RecentService {
    /**
     * 获取最近播放
     * @return
     */
    List<RecentStatusV0> recentPlay() throws Exception;

    /**
     * 获取最近下载
     * @return
     */
    List<RecentStatusV0> recentDownload() throws Exception;
}
