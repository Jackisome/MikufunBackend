package com.devteam.mikufunbackend.service.serviceInterface;

import com.devteam.mikufunbackend.entity.*;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * @author Wooyuwen
 * @date 2021-10-09
 */
public interface PlayService {
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

    /**
     * 更新视频最近播放时间
     * @param fileId
     * @return
     */
    boolean updateRecentPlayTime(int fileId);

    /**
     * 寻找播放视频所需信息
     * @param fileId
     * @return
     */
    Map<String, Object> findPlayInformation(int fileId);

    /**
     * 搜索资源关联的弹幕
     * @param fileId 文件id
     * @return 关联弹幕剧集列表
     */
    List<MatchEpisodeRespVO> getMatchEpisodes(Integer fileId) throws IOException;

    /**
     * 更新视频关联的弹幕信息
     * @param matchEpisodePutReqVO 请求 VO
     */
    void putMatchEpisode(MatchEpisodePutReqVO matchEpisodePutReqVO);

}
