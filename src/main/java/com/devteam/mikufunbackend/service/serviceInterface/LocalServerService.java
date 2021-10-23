package com.devteam.mikufunbackend.service.serviceInterface;

import com.devteam.mikufunbackend.entity.DiskSpaceV0;
import com.devteam.mikufunbackend.entity.FileV0;

import java.util.List;

/**
 * @author Jackisome
 * @date 2021/10/21
 */
public interface LocalServerService {
    DiskSpaceV0 getDiskSpace();

    /**
     * 删除文件或目录，并返回删除的文件个数
     * @param path
     * @return
     */
    int deleteFile(String path);

    List<FileV0> getFileInformation(String path);
}
