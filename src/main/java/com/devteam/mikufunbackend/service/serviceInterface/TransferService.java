package com.devteam.mikufunbackend.service.serviceInterface;

import com.devteam.mikufunbackend.entity.Aria2FileVO;
import com.devteam.mikufunbackend.entity.ResourceMatchVO;

import java.io.IOException;
import java.util.List;

/**
 * @author Jackisome
 * @date 2021/10/3
 */
public interface TransferService {
    /**
     * 寻找并转码视频资源，每次最多对一个gid的对应文件进行操作
     * @return
     */
    void transfer() throws IOException, InterruptedException;

    /**
     * 对一个文件进行转码，变更相应的数据记录
     * @param aria2FileVO
     * @param gid
     * @return
     */
    boolean transferFile(Aria2FileVO aria2FileVO, String gid) throws IOException, InterruptedException;

    /**
     * 根据资源信息匹配匹配可能的番剧和聚集，并获取对应的弹幕库
     * @param fileName
     * @param fileHash
     * @param fileSize
     * @param videoDuration
     * @return
     */
    List<ResourceMatchVO> matchResourceInformation(String fileName, String fileHash, long fileSize, int videoDuration) throws IOException;

    /**
     * 根据fileUuid寻找资源是否存在字幕文件
     * @param fileUuid
     * @return
     */
    String getDefaultSubtitlePath(String fileUuid);
}
