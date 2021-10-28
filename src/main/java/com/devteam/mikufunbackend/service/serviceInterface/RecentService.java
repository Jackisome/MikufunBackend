package com.devteam.mikufunbackend.service.serviceInterface;

import com.devteam.mikufunbackend.entity.RecentDownloadV0;
import com.devteam.mikufunbackend.entity.RecentPlayV0;

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
    List<RecentPlayV0> recentPlay() throws Exception;

    /**
     * 获取最近下载
     * @return
     */
    List<RecentDownloadV0> recentDownload() throws Exception;
}
