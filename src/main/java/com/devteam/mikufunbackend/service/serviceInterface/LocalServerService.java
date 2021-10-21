package com.devteam.mikufunbackend.service.serviceInterface;

import com.devteam.mikufunbackend.entity.DiskSpaceV0;
import com.devteam.mikufunbackend.entity.FreeDownloadFileV0;

import java.util.List;

/**
 * @author Jackisome
 * @date 2021/10/21
 */
public interface LocalServerService {
    DiskSpaceV0 getDiskSpace();

    List<FreeDownloadFileV0> getFreeDownloadFiles();
}
