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
     * 删除gid关联的所有文件下载记录
     * @param gid
     * @return
     */
    int deleteDownloadStatusRecordByGid(String gid);
}
