package com.devteam.mikufunbackend.service.serviceInterface;


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

}
