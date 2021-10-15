package com.devteam.mikufunbackend.service.serviceInterface;


import java.util.List;
import java.util.Map;

public interface FavoriteService {

    /**
     * 收藏番剧
     *
     * @param resourceId 番剧 Id
     * @param status 收藏状态
     */
    void setFavoriteStatus(Integer resourceId, String status);

    /**
     * 获取指定番剧收藏状态
     *
     * @param resourceId 番剧 Id
     * @return 收藏状态
     */
    String getFavoriteStatus(Integer resourceId);

    /**
     * 获取指定番剧列表的收藏状态
     *
     * @param resourceIds 番剧 Id 列表
     * @return 番剧 Id 及其收藏状态映射
     */
    Map<Integer, String> getFavoriteStatusListById(List<Integer> resourceIds);

}
