package com.devteam.mikufunbackend.service.serviceImpl;

import com.devteam.mikufunbackend.constant.Aria2Constant;
import com.devteam.mikufunbackend.dao.ResourceInformationDao;
import com.devteam.mikufunbackend.entity.*;
import com.devteam.mikufunbackend.handle.Aria2Exception;
import com.devteam.mikufunbackend.service.serviceInterface.Aria2Service;
import com.devteam.mikufunbackend.service.serviceInterface.DownLoadService;
import org.dom4j.DocumentException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Jackisome
 * @date 2021/9/27
 */
@Service
public class DownloadServiceImpl implements DownLoadService {

    Logger logger = LoggerFactory.getLogger(DownloadServiceImpl.class);

    @Autowired
    private Aria2Service aria2Service;

    @Autowired
    private ResourceInformationDao resourceInformationDao;

    @Override
    public boolean download(String link) throws DocumentException, IOException, Aria2Exception {
        String gid = aria2Service.addUrl(link);
        System.out.println(gid);
        return true;
    }

    @Override
    public boolean remove(String gid) throws IOException {
        return aria2Service.removeDownloadingFile(gid);
    }

    @Override
    public List<DownloadStatusV0> getDownloadingFiles() throws Aria2Exception, IOException {
        List<Aria2StatusV0> aria2StatusV0s = aria2Service.getFileStatus(Aria2Constant.METHOD_TELL_ACTIVE);
        aria2StatusV0s.addAll(aria2Service.getFileStatus(Aria2Constant.METHOD_TELL_WAITING));
        aria2StatusV0s.addAll(aria2Service.getFileStatus(Aria2Constant.METHOD_TELL_STOPPED));
        List<DownloadStatusV0> data = new ArrayList<>();
        aria2StatusV0s.forEach(k -> {
            String gid = k.getGid();
            int downloadSpeed = k.getDownloadSpeed();
            int uploadSpeed = k.getUploadSpeed();
            String status = k.getStatus();
            k.getFiles().forEach(fileV0 -> {
                if (fileV0.isSelected()) {
                    DownloadStatusV0 downloadStatusV0 = DownloadStatusV0.builder()
                            .gid(gid)
                            .fileName(fileV0.getPath())
                            .completedLength(fileV0.getCompletedLength())
                            .fileSize(fileV0.getLength())
                            .downloadSpeed(downloadSpeed)
                            .uploadSpeed(uploadSpeed)
                            .status(status.equals("complete")? "transfering": status)
                            .build();
                    data.add(downloadStatusV0);
                }
            });
        });
        return data;
    }

    @Override
    public List<FinishFileV0> getFinishFiles() {
        List<FinishFileV0> data = new ArrayList<>();
        List<ResourceEntity> resourceEntities = resourceInformationDao.findAllResourceInformation();
        resourceEntities.forEach(resourceEntity -> {
            data.add(resourceEntity.getFinishFileV0());
        });
        return data;
    }

    @Override
    public List<FinishFileV0> getFinishFilesByResourceId(int resourceId) {
        List<FinishFileV0> data = new ArrayList<>();
        List<ResourceEntity> resourceEntities = resourceInformationDao.findResourceInformationByResourceId(resourceId);
        resourceEntities.forEach(resourceEntity -> {
            data.add(resourceEntity.getFinishFileV0());
        });
        return data;
    }

    @Override
    public List<ResourceV0> getResourceList() {
        List<ResourceV0> data = resourceInformationDao.findResourceList();
        return data;
    }

    @Override
    public List<SimpleFinishFileV0> deleteLocalFiles(List<Integer> fileIds) {
        List<SimpleFinishFileV0> data = new ArrayList<>();
        fileIds.forEach(fileId -> {
            SimpleFinishFileV0 simpleFinishFileV0;
            ResourceEntity resourceEntity = resourceInformationDao.findResourceInformationByFileId(fileId);
            if (resourceEntity == null) {
                simpleFinishFileV0 = SimpleFinishFileV0.builder()
                        .fileId(fileId)
                        .delete(false)
                        .build();
            } else {
                resourceInformationDao.deleteResourceInformationByFileId(fileId);
                simpleFinishFileV0 = resourceEntity.getSimpleFinishFileV0();
                String path = resourceEntity.getFilePath();
                File file = new File(path);
                if (!file.exists() || file.delete()) {
                    simpleFinishFileV0.setDelete(true);
                }
            }
            data.add(simpleFinishFileV0);
        });
        return data;
    }
}
