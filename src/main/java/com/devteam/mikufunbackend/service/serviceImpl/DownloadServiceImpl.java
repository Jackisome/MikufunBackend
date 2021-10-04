package com.devteam.mikufunbackend.service.serviceImpl;

import com.devteam.mikufunbackend.constant.Aria2Constant;
import com.devteam.mikufunbackend.dao.DownloadStatusDao;
import com.devteam.mikufunbackend.dao.ResourceInformationDao;
import com.devteam.mikufunbackend.entity.*;
import com.devteam.mikufunbackend.handle.Aria2Exception;
import com.devteam.mikufunbackend.service.serviceInterface.Aria2Service;
import com.devteam.mikufunbackend.service.serviceInterface.DownLoadService;
import com.devteam.mikufunbackend.service.serviceInterface.TransferService;
import com.devteam.mikufunbackend.util.ParamUtil;
import com.devteam.mikufunbackend.util.ResultUtil;
import org.dom4j.DocumentException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

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
    private TransferService transferService;

    @Autowired
    private ResourceInformationDao resourceInformationDao;

    @Autowired
    private DownloadStatusDao downloadStatusDao;

    @Value("${shell.path}")
    private String shellPath;

    @Override
    public boolean download(String link) throws DocumentException, IOException, Aria2Exception {
        aria2Service.addUrl(link);
        // 在下载状态表中增加相应记录
        List<Aria2StatusV0> aria2StatusV0s = aria2Service.getFileStatus(Aria2Constant.METHOD_TELL_ACTIVE);
        aria2StatusV0s.addAll(aria2Service.getFileStatus(Aria2Constant.METHOD_TELL_WAITING));
        aria2StatusV0s.forEach(aria2StatusV0 -> {
            String gid = aria2StatusV0.getGid();
            if (downloadStatusDao.findDownloadStatusRecordByGid(gid).size() == 0) {
                List<Aria2FileV0> aria2FileV0s = aria2StatusV0.getFiles();
                if (ParamUtil.isNotEmpty(aria2FileV0s)) {
                    aria2FileV0s.forEach(aria2FileV0 -> {
                        if (!ResultUtil.getFileName(aria2FileV0.getPath()).startsWith("[METADATA]")) {
                            DownloadStatusEntity downloadStatusEntity = DownloadStatusEntity.builder()
                                    .gid(gid)
                                    .link(link)
                                    .fileName(ResultUtil.getFileName(aria2FileV0.getPath()))
                                    .filePath(aria2FileV0.getPath())
                                    .isFinish(0)
                                    .isSourceDelete(0)
                                    .status(aria2StatusV0.getStatus())
                                    .build();
                            downloadStatusDao.addDownloadStatusRecord(downloadStatusEntity);
                            logger.info("add record to downloadStatus table, downloadStatusEntity: {}", downloadStatusEntity.toString());
                        }
                    });
                }
            }
        });
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
                            .status(status.equals("complete")? "transferring": status)
                            .build();
                    data.add(downloadStatusV0);
                }
            });
        });
        logger.info("get downloading file, files: {}", data);
        return data;
    }

    @Override
    public List<FinishFileV0> getFinishFiles() {
        List<FinishFileV0> data = new ArrayList<>();
        List<ResourceEntity> resourceEntities = resourceInformationDao.findAllResourceInformation();
        resourceEntities.forEach(resourceEntity -> {
            data.add(resourceEntity.getFinishFileV0());
        });
        logger.info("get finish file, files: {}", data);
        return data;
    }

    @Override
    public List<FinishFileV0> getFinishFilesByResourceId(int resourceId) {
        List<FinishFileV0> data = new ArrayList<>();
        List<ResourceEntity> resourceEntities = resourceInformationDao.findResourceInformationByResourceId(resourceId);
        resourceEntities.forEach(resourceEntity -> {
            data.add(resourceEntity.getFinishFileV0());
        });
        logger.info("get resource file by resourceId, files: {}", data);
        return data;
    }

    @Override
    public List<ResourceV0> getResourceList() {
        List<ResourceV0> data = resourceInformationDao.findResourceList();
        logger.info("get resource list, resources: {}", data);
        return data;
    }

    @Override
    public List<SimpleFinishFileV0> deleteLocalFiles(List<Integer> fileIds) {
        List<SimpleFinishFileV0> data = new ArrayList<>();
        fileIds.forEach(fileId -> {
            SimpleFinishFileV0 simpleFinishFileV0;
            ResourceEntity resourceEntity = resourceInformationDao.findResourceInformationByFileId(fileId);
            if (resourceEntity == null) {
                logger.info("not find file wanted to delete, fileId: {}", fileId);
                simpleFinishFileV0 = SimpleFinishFileV0.builder()
                        .fileId(fileId)
                        .delete(false)
                        .build();
            } else {
                simpleFinishFileV0 = resourceEntity.getSimpleFinishFileV0();
                try {
                    if (transferService.deleteFile(resourceEntity.getFileDirectory())) {
                        resourceInformationDao.deleteResourceInformationByFileId(fileId);
                        logger.info("delete record in resourceInformation table by fileId, fileId: {}", fileId);
                        downloadStatusDao.deleteDownloadStatusRecordByGidAndFileName(resourceEntity.getGid(), resourceEntity.getFileName());
                        logger.info("delete record in downloadStatus table by gid and fileName, gid: {}, fileName: {}", resourceEntity.getGid(), resourceEntity.getFileName());
                        simpleFinishFileV0.setDelete(true);
                    }
                } catch (IOException | InterruptedException e) {
                    logger.error(e.toString());
                }
            }
            data.add(simpleFinishFileV0);
        });
        return data;
    }
}
