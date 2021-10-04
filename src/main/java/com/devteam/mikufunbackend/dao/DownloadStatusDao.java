package com.devteam.mikufunbackend.dao;

import com.devteam.mikufunbackend.entity.DownloadStatusEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author Jackisome
 * @date 2021/10/2
 */
@Mapper
public interface DownloadStatusDao {
    /**
     * 增加下载状态的记录
     * @param downloadStatusEntity
     * @return
     */
    int addDownloadStatusRecord(DownloadStatusEntity downloadStatusEntity);

    /**
     * 根据gid和文件名删除下载状态的记录
     * @param fileName
     * @return
     */
    int deleteDownloadStatusRecordByGidAndFileName(String gid,
                                                   String fileName);

    /**
     * 删除gid关联的所有文件下载记录
     * @param gid
     * @return
     */
    int deleteDownloadStatusRecordByGid(String gid);

    /**
     * 更新下载完成的标识为已完成
     * @param filePath
     * @return
     */
    int updateFinishTag(String filePath);

    /**
     * 更新源文件删除的标识为已删除
     * @param filePath
     * @return
     */
    int updateSourceDeleteTag(String filePath);

    /**
     * 根据gid寻找关联的所有文件
     * @param gid
     * @return
     */
    List<DownloadStatusEntity> findDownloadStatusRecordByGid(String gid);

    /**
     * 根据下载链接寻找下载过的文件
     * @param link
     * @return
     */
    List<DownloadStatusEntity> findDownloadStatusRecordByLink(String link);

    /**
     * 寻找可以删除源文件（下载完成而源文件未删除）的记录
     * @return
     */
    List<DownloadStatusEntity> findAbleDeleteResource();
}
