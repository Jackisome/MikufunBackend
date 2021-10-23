package com.devteam.mikufunbackend.service.serviceInterface;

import com.devteam.mikufunbackend.entity.Aria2FileV0;
import com.devteam.mikufunbackend.entity.ResourceMatchV0;

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
     * @param aria2FileV0
     * @param gid
     * @return
     */
    boolean transferFile(Aria2FileV0 aria2FileV0, String gid) throws IOException, InterruptedException;

    /**
     * 清理下载已完成且转码已完成的文件，以gid对应的文件为单位
     */
    void cleanSourceFiles() throws IOException, InterruptedException;

    /**
     * 根据资源信息匹配匹配可能的番剧和聚集，并获取对应的弹幕库
     * @param fileName
     * @param fileHash
     * @param fileSize
     * @param videoDuration
     * @return
     */
    List<ResourceMatchV0> matchResourceInformation(String fileName, String fileHash, int fileSize, int videoDuration) throws IOException;

    /**
     * 根据fileUuid寻找资源是否存在字幕文件
     * @param fileUuid
     * @return
     */
    String getDefaultSubtitlePath(String fileUuid);
}
