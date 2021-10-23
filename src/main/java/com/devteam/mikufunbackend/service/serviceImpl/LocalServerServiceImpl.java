package com.devteam.mikufunbackend.service.serviceImpl;

import com.devteam.mikufunbackend.entity.DiskSpaceV0;
import com.devteam.mikufunbackend.entity.FileV0;
import com.devteam.mikufunbackend.handle.FileErrorException;
import com.devteam.mikufunbackend.service.serviceInterface.LocalServerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Jackisome
 * @date 2021/10/21
 */
@Service
public class LocalServerServiceImpl implements LocalServerService {

    Logger logger = LoggerFactory.getLogger(LocalServerService.class);

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
    public int deleteFile(String path) {
        logger.info("begin delete file, file: {}", path);
        File file = new File(path);
        if (!file.exists()) {
            throw new FileErrorException("文件未找到");
        } else if (file.isFile()) {
            return file.delete() ? 1 : 0;
        } else {
            return deleteDirectory(path);
        }
    }

    @Override
    public List<FileV0> getFileInformation(String path) {
        List<FileV0> data = new ArrayList<>();
        File file = new File(path);
        getFileInformation(file, data);
        return data;
    }

    private void getFileInformation(File file, List<FileV0> fileV0s) {
        if (file.exists()) {
            if (file.isFile()) {
                fileV0s.add(getFileV0FromFile(file));
            } else {
                File[] files = file.listFiles();
                if (files != null) {
                    for (File value : files) {
                        if (file.isFile()) {
                            fileV0s.add(getFileV0FromFile(value));
                        } else {
                            getFileInformation(value, fileV0s);
                        }
                    }
                }
            }
        }
    }

    private FileV0 getFileV0FromFile(File file) {
        return FileV0.builder()
                .fileName(file.getName())
                .fileUrl(file.getAbsolutePath())
                .fileSize(file.length() / 1024.0 / 1024.0 + "MiB")
                .build();
    }

    private int deleteDirectory(String path) {
        if (!path.endsWith(File.separator)) {
            path = path + File.separator;
        }
        File directoryFile = new File(path);
        int deleteFilesCount = 0;
        if (!directoryFile.exists() || !directoryFile.isDirectory()) {
            return 0;
        }
        File[] files = directoryFile.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isFile()) {
                    if (file.delete()) {
                        deleteFilesCount += 1;
                    }
                } else {
                    deleteFilesCount += deleteDirectory(file.getAbsolutePath());
                }
            }
        }
        directoryFile.delete();
        return deleteFilesCount;
    }
}
