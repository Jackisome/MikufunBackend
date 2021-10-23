package com.devteam.mikufunbackend.service.serviceInterface;

import com.devteam.mikufunbackend.constant.Aria2Constant;
import com.devteam.mikufunbackend.entity.Aria2StatusV0;
import com.devteam.mikufunbackend.entity.DownloadStatusV0;
import org.dom4j.DocumentException;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * @author Jackisome
 * @date 2021/10/1
 */
public interface Aria2Service {
    /**
     * 添加下载任务
     * @param link
     * @return
     * @throws IOException
     * @throws DocumentException
     */
    boolean addUrl(String link) throws IOException, DocumentException;

    /**
     * 添加下载任务，并指定存放目录
     * @param link
     * @param path
     * @return
     */
    boolean addUrl(String link, String path) throws IOException;

    /**
     * 暂停所有文件的下载
     * @return
     */
    boolean pauseAllDownloadingFile() throws IOException;

    /**
     * 恢复所有暂停文件的下载
     * @return
     */
    boolean unpauseAllDownloadingFile() throws IOException;

    /**
     * 切换文件的下载状态
     * @param gid
     * @param method
     * @return
     */
    boolean transferDownloadStatus(String gid, String method) throws IOException;

    /**
     * 获取指定文件的下载状态
     * @param gid
     * @return
     */
    Aria2StatusV0 tellDownloadingFileStatus(String gid);

    /**
     * 根据指定状态获取对应所有文件的下载状态
     * @param type Aria2Constant中定义的常量，waiting, active, stopped
     * @return
     * @throws IOException
     */
    List<Aria2StatusV0> getFileStatus(String type) throws IOException;

    /**
     * 获取全局的下载状态
     * @return
     */
    Map<String, String> getGlobalStatus();
}
