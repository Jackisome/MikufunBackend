package com.devteam.mikufunbackend.service.serviceInterface;

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
    String addUrl(String link) throws IOException, DocumentException;

    boolean removeDownloadingFile(String gid) throws IOException;

    boolean pauseDownloadingFile(String gid);

    boolean pauseAllDownloadingFile();

    boolean unpauseDownloadingFile(String gid);

    boolean unpauseAllDownloadingFile();

    DownloadStatusV0 tellDownloadingFileStatus(String gid, String keys);

    List<DownloadStatusV0> getActiveFileStatus() throws IOException;

    List<DownloadStatusV0> getWaitingFileStatus();

    List<DownloadStatusV0> getStoppedFilesStatus();

    Map<String, String> getGlobalStatus();
}
