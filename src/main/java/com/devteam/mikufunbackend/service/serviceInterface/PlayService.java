package com.devteam.mikufunbackend.service.serviceInterface;

import com.devteam.mikufunbackend.entity.DanmakuV0;
import com.devteam.mikufunbackend.entity.ResourceEntity;

import java.util.List;

/**
 * @author Wooyuwen
 * @date 2021-10-09
 */
public interface PlayService {
    /**
     * 获取视频地址
     * @param fileId
     * @return
     * @throws Exception
     */
    ResourceEntity getFileAddr(int fileId) throws Exception;

    /**
     * 获取弹幕
     * @param fileId
     * @return
     * @throws Exception
     */
    List<DanmakuV0> getDanmaku(int fileId) throws Exception;
}
