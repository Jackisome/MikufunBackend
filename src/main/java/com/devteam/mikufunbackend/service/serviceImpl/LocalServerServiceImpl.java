package com.devteam.mikufunbackend.service.serviceImpl;

import com.devteam.mikufunbackend.entity.DiskSpaceV0;
import com.devteam.mikufunbackend.entity.FreeDownloadFileV0;
import com.devteam.mikufunbackend.service.serviceInterface.LocalServerService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.List;

/**
 * @author Jackisome
 * @date 2021/10/21
 */
@Service
public class LocalServerServiceImpl implements LocalServerService {
    @Value("${root.path}")
    private String rootPath;

    @Override
    public DiskSpaceV0 getDiskSpace() {
        File diskPartition = new File(rootPath);
        double usableSpace = diskPartition.getUsableSpace() / 1024.0 / 1024.0 / 1024.0;
        double totalSpace = diskPartition.getTotalSpace() / 1024.0 / 1024.0 / 1024.0;
        double usedSpace = totalSpace - usableSpace;

        return DiskSpaceV0.builder()
                .usedSpace(usedSpace)
                .totalSpace(totalSpace)
                .build();
    }

    @Override
    public List<FreeDownloadFileV0> getFreeDownloadFiles() {
        return null;
    }
}
