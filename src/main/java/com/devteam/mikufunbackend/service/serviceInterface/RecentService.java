package com.devteam.mikufunbackend.service.serviceInterface;

import com.devteam.mikufunbackend.entity.RecentDownloadVO;
import com.devteam.mikufunbackend.entity.RecentPlayVO;

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
    List<RecentPlayVO> recentPlay() throws Exception;

    /**
     * 获取最近下载
     * @return
     */
    List<RecentDownloadVO> recentDownload() throws Exception;
}
