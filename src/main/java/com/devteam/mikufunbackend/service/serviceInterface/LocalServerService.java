package com.devteam.mikufunbackend.service.serviceInterface;

import com.devteam.mikufunbackend.entity.DiskSpaceVO;
import com.devteam.mikufunbackend.entity.FileVO;

import java.util.List;

/**
 * @author Jackisome
 * @date 2021/10/21
 */
public interface LocalServerService {
    DiskSpaceVO getDiskSpace();

    /**
     * 删除文件或目录，并返回删除的文件个数
     * @param path
     * @return
     */
    int deleteFile(String path);

    List<FileVO> getFileInformation(String path);
}
