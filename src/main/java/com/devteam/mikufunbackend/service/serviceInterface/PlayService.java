package com.devteam.mikufunbackend.service.serviceInterface;

import com.devteam.mikufunbackend.entity.DanmakuPostV0;
import com.devteam.mikufunbackend.entity.DanmakuV0;
import com.devteam.mikufunbackend.entity.RegExpV0;
import com.devteam.mikufunbackend.entity.ResourceEntity;

import java.util.List;
import java.util.Map;

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
    List<List<Object>> getDanmaku(int fileId) throws Exception;

    /**
     * 增加弹幕
     * @param body
     */
    Boolean postDanmaku(DanmakuPostV0 body) throws Exception;

    /**
     * 获取正则表达式
     * @return
     */
    List<RegExpV0> getRegex() throws Exception;

    /**
     * 更新播放进度
     * @param fileId
     * @param videoTime
     * @return
     * @throws Exception
     */
    Boolean updatePos(int fileId,int videoTime) throws Exception;
}
