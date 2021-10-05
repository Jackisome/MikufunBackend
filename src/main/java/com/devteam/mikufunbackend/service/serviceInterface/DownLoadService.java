package com.devteam.mikufunbackend.service.serviceInterface;

import com.devteam.mikufunbackend.entity.DownloadStatusV0;
import com.devteam.mikufunbackend.entity.FinishFileV0;
import com.devteam.mikufunbackend.entity.ResourceV0;
import com.devteam.mikufunbackend.entity.SimpleFinishFileV0;
import com.devteam.mikufunbackend.handle.Aria2Exception;
import org.dom4j.DocumentException;

import java.io.IOException;
import java.util.List;

/**
 * @author Jackisome
 * @date 2021/9/27
 */
public interface DownLoadService {
    /**
     * 下载指定链接的数据到本地
     * @param link 下载链接
     * @return 下载请求是否完成创建
     * @throws Exception
     */
    boolean download(String link) throws DocumentException, IOException, Aria2Exception, InterruptedException;

    /**
     * 删除一个下载中的文件
     * @param gid
     * @return
     * @throws IOException
     */
    boolean remove(String gid) throws IOException;

    /**
     * 获取下载中的所有文件（状态为waiting、active、stopped）
     * @return
     * @throws IOException
     */
    List<DownloadStatusV0> getDownloadingFiles() throws IOException;

    /**
     * 获取所有下载并转码完成的文件
     * @return
     */
    List<FinishFileV0> getFinishFiles();

    /**
     * 根据resourceId获取指定番剧的所有下载并完成转码的文件
     * @param resourceId
     * @return
     */
    List<FinishFileV0> getFinishFilesByResourceId(int resourceId);

    /**
     * 获取番剧列表，其中任何一个番剧至少有一个完成下载并转码的本地文件
     * @return
     */
    List<ResourceV0> getResourceList();

    List<SimpleFinishFileV0> deleteLocalFiles(List<Integer> fileIds) throws IOException, InterruptedException;
}
