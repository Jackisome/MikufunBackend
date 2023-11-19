package com.devteam.mikufunbackend.dao;

import com.devteam.mikufunbackend.entity.ResourceEntity;
import com.devteam.mikufunbackend.entity.ResourceVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author Jackisome
 * @date 2021/10/2
 */
@Mapper
public interface ResourceInformationDao {
    /**
     * 增加新的已下载文件记录
     *
     * @param resourceEntity
     * @return
     */
    int addResourceInformation(ResourceEntity resourceEntity);

    /**
     * 根据文件id删除文件记录
     *
     * @param fileId
     * @return
     */
    int deleteResourceInformationByFileId(int fileId);

    /**
     * 更新番剧匹配的信息
     *
     * @param exactMatch
     * @param resourceId
     * @param resourceName
     * @param episodeTitle
     * @param type
     * @param episodeId
     * @return
     */
    int updateResourceInformation(int fileId,
                                  int exactMatch,
                                  int resourceId,
                                  String resourceName,
                                  String episodeTitle,
                                  String type,
                                  int episodeId);

    /**
     * 更新视频播放的位置
     *
     * @param fileId
     * @param recentPlayPosition
     * @return
     */
    int updatePlayPosition(int fileId,
                           double recentPlayPosition);

    /**
     * 更新视频最近播放时间
     * @param fileId
     * @return
     */
    int updateRecentPlayTime(int fileId);

    /**
     * 根据文件id寻找相应记录
     *
     * @param fileId
     * @return
     */
    ResourceEntity findResourceInformationByFileId(int fileId);

    /**
     * 寻找最近播放的文件
     *
     * @return
     */
    List<ResourceEntity> findRecentPlayResource();

    /**
     * 寻找最近下载的文件
     *
     * @return
     */
    List<ResourceEntity> findRecentDownloadResource();

    /**
     * 根据番剧id寻找番剧的所有集数
     *
     * @param resourceId
     * @return
     */
    List<ResourceEntity> findResourceInformationByResourceId(int resourceId);

    /**
     * 寻找番剧列表
     *
     * @return
     */
    List<ResourceVO> findResourceList();

    /**
     * 查找所有文件信息记录
     *
     * @return
     */
    List<ResourceEntity> findAllResourceInformation();

    /**
     * 寻找所有已下载并已转码完成的gid
     * @return
     */
    List<String> findAllGid();

    /**
     * 更新视频文件的字幕
     * @param fileId
     * @param subtitlePath
     * @return
     */
    int updateSubtitlePath(int fileId,
                           String subtitlePath);
}
