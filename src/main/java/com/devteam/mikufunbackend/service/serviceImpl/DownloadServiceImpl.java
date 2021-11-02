package com.devteam.mikufunbackend.service.serviceImpl;

import com.devteam.mikufunbackend.constant.Aria2Constant;
import com.devteam.mikufunbackend.dao.DownloadStatusDao;
import com.devteam.mikufunbackend.dao.ResourceInformationDao;
import com.devteam.mikufunbackend.entity.*;
import com.devteam.mikufunbackend.handle.Aria2Exception;
import com.devteam.mikufunbackend.handle.DownloadedException;
import com.devteam.mikufunbackend.service.serviceInterface.Aria2Service;
import com.devteam.mikufunbackend.service.serviceInterface.DownloadService;
import com.devteam.mikufunbackend.service.serviceInterface.LocalServerService;
import com.devteam.mikufunbackend.service.serviceInterface.TransferService;
import com.devteam.mikufunbackend.util.ParamUtil;
import com.devteam.mikufunbackend.util.ResultUtil;
import org.dom4j.DocumentException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author Jackisome
 * @date 2021/9/27
 */
@Service
public class DownloadServiceImpl implements DownloadService {

    Logger logger = LoggerFactory.getLogger(DownloadServiceImpl.class);

    @Autowired
    private Aria2Service aria2Service;

    @Autowired
    private TransferService transferService;

    @Autowired
    private LocalServerService localServerService;

    @Autowired
    private ResourceInformationDao resourceInformationDao;

    @Autowired
    private DownloadStatusDao downloadStatusDao;

    @Override
    public boolean download(String link) throws DocumentException, IOException, Aria2Exception {
        if (downloadStatusDao.findDownloadStatusRecordByLink(link).size() > 0) {
            throw new DownloadedException("文件已下载");
        }
        aria2Service.addUrl(link);
        new Thread(() -> {
            AtomicInteger runTimeCount = new AtomicInteger(0);
            AtomicInteger count = new AtomicInteger(0);
            while (runTimeCount.get() < 10 && count.get() == 0) {
                try {
                    Thread.sleep(6000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                // 在下载状态表中增加相应记录
                List<Aria2StatusV0> aria2StatusV0s = new ArrayList<>();
                try {
                    aria2StatusV0s = aria2Service.getFileStatus(Aria2Constant.METHOD_TELL_WAITING);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                try {
                    aria2StatusV0s.addAll(aria2Service.getFileStatus(Aria2Constant.METHOD_TELL_ACTIVE));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                for (Aria2StatusV0 aria2StatusV0 : aria2StatusV0s) {
                    logger.info("add record to downloadStatus table begin, aria2StatusV0: {}", aria2StatusV0.toString());
                    String gid = aria2StatusV0.getGid();
                    // todo: 线程安全
                    if (downloadStatusDao.findDownloadStatusRecordByGid(gid).size() == 0) {
                        List<Aria2FileV0> aria2FileV0s = aria2StatusV0.getFiles();
                        if (ParamUtil.isNotEmpty(aria2FileV0s)) {
                            aria2FileV0s.forEach(aria2FileV0 -> {
                                if (!ResultUtil.getFileName(aria2FileV0.getPath()).equals(aria2FileV0.getPath())) {
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
                                    count.addAndGet(1);
                                    logger.info("add record to downloadStatus table, downloadStatusEntity: {}", downloadStatusEntity.toString());
                                }
                            });
                        }
                    }
                    if (count.get() > 0) {
                        break;
                    }
                }
                runTimeCount.addAndGet(1);
            }
        }).start();
        return true;
    }

    @Override
    public boolean changeDownloadStatus(String gid, Aria2Constant.downloadAction downloadAction) throws IOException {
        switch (downloadAction) {
            case PAUSE:
                return aria2Service.transferDownloadStatus(gid, Aria2Constant.METHOD_PAUSE);
            case UNPAUSE:
                return aria2Service.transferDownloadStatus(gid, Aria2Constant.METHOD_UNPAUSE);
            case REMOVE:
                return aria2Service.transferDownloadStatus(gid, Aria2Constant.METHOD_REMOVE_DOWNLOAD_RESULT);
            default:
                return false;
        }
    }

    @Override
    public List<DownloadStatusTransferV0> changeDownloadStatus(List<String> gids, Aria2Constant.downloadAction downloadAction) {
        List<DownloadStatusTransferV0> data = new ArrayList<>();
        if (ParamUtil.isNotEmpty(gids)) {
            gids.forEach(gid -> {
                DownloadStatusTransferV0 downloadStatusTransferV0 = null;
                try {
                    downloadStatusTransferV0 = DownloadStatusTransferV0.builder()
                            .gid(gid)
                            .status(changeDownloadStatus(gid, downloadAction))
                            .build();
                } catch (IOException e) {
                    logger.error(e.toString());
                }
                data.add(downloadStatusTransferV0);
            });
        }
        return data;
    }

    @Override
    public List<DownloadStatusV0> getDownloadingFiles() throws Aria2Exception, IOException {
        List<Aria2StatusV0> aria2StatusV0s = aria2Service.getFileStatus(Aria2Constant.METHOD_TELL_ACTIVE);
        aria2StatusV0s.addAll(aria2Service.getFileStatus(Aria2Constant.METHOD_TELL_WAITING));
        aria2StatusV0s.addAll(aria2Service.getFileStatus(Aria2Constant.METHOD_TELL_STOPPED));
        List<DownloadStatusV0> data = new ArrayList<>();
        for (Aria2StatusV0 aria2StatusV0 : aria2StatusV0s) {
            String gid = aria2StatusV0.getGid();
            int downloadSpeed = aria2StatusV0.getDownloadSpeed();
            int uploadSpeed = aria2StatusV0.getUploadSpeed();
            String status = aria2StatusV0.getStatus();
            if ("active".equals(status) && aria2StatusV0.getCompletedLength() == aria2StatusV0.getTotalLength()) {
                status = "seeding";
            }
            if (!"complete".equals(status) && !"removed".equals(status)) {
                for (Aria2FileV0 file : aria2StatusV0.getFiles()) {
                    if (file.isSelected() && !ResultUtil.getFileName(file.getPath()).equals(file.getPath())) {
                        DownloadStatusV0 downloadStatusV0 = DownloadStatusV0.builder()
                                .gid(gid)
                                .fileName(ResultUtil.getFileName(file.getPath()))
                                .completedLength(file.getCompletedLength() / (1024.0 * 1024))
                                .fileSize(file.getLength() / (1024.0 * 1024))
                                .downloadSpeed(downloadSpeed / (1024.0 * 1024))
                                .uploadSpeed(uploadSpeed / (1024.0 * 1024))
                                .status(status)
                                .build();
                        data.add(downloadStatusV0);
                    }
                }
            }
        }
        logger.info("get downloading file, files: {}", data);
        return data;
    }

    @Override
    public List<FinishFileV0> getFinishFiles() {
        List<FinishFileV0> data = new ArrayList<>();
        List<ResourceEntity> resourceEntities = resourceInformationDao.findAllResourceInformation();
        resourceEntities.forEach(resourceEntity -> data.add(resourceEntity.getFinishFileV0()));
        logger.info("get finish file, files: {}", data);
        return data;
    }

    @Override
    public List<FinishFileV0> getFinishFilesByResourceId(int resourceId) {
        List<FinishFileV0> data = new ArrayList<>();
        List<ResourceEntity> resourceEntities = resourceInformationDao.findResourceInformationByResourceId(resourceId);
        resourceEntities.forEach(resourceEntity -> data.add(resourceEntity.getFinishFileV0()));
        logger.info("get resource file by resourceId, files: {}", data);
        return data;
    }

    @Override
    public List<ResourceResponseV0> getResourceList() {
        List<ResourceV0> resourceV0s = resourceInformationDao.findResourceList();
        List<ResourceResponseV0> data = new ArrayList<>();
        resourceV0s.forEach(resourceV0 -> data.add(ResourceResponseV0.builder()
                .resourceId(String.valueOf(resourceV0.getResourceId()))
                .resourceName(resourceV0.getResourceName())
                .build()));
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
                        .fileId(String.valueOf(fileId))
                        .fileName("")
                        .delete(false)
                        .build();
            } else {
                simpleFinishFileV0 = resourceEntity.getSimpleFinishFileV0();
                DownloadStatusEntity downloadStatusEntity = downloadStatusDao.findDownloadStatusRecordByFileName(resourceEntity.getFileName());
                String gid = downloadStatusEntity.getGid();
                try {
                    changeDownloadStatus(gid, Aria2Constant.downloadAction.REMOVE);
                } catch (IOException e) {
                    logger.error(e.toString());
                } catch (Aria2Exception e) {
                    logger.warn(e.toString());
                }
                // 如果源文件存在，先删除源文件
                if (downloadStatusEntity.getIsSourceDelete() == 0) {
                    localServerService.deleteFile(downloadStatusEntity.getFilePath());
                }
                // 删除转码文件，清除数据表记录
                if (localServerService.deleteFile(resourceEntity.getImageUrl()) > 0 && localServerService.deleteFile(ParamUtil.getFileDirectory(resourceEntity.getFileUuid())) > 0) {
                    resourceInformationDao.deleteResourceInformationByFileId(fileId);
                    logger.info("delete record in resourceInformation table by fileId, fileId: {}", fileId);
                    downloadStatusDao.deleteDownloadStatusRecordByGidAndFileName(resourceEntity.getGid(), resourceEntity.getFileName());
                    logger.info("delete record in downloadStatus table by gid and fileName, gid: {}, fileName: {}", resourceEntity.getGid(), resourceEntity.getFileName());
                    simpleFinishFileV0.setDelete(true);
                }
            }
            data.add(simpleFinishFileV0);
        });
        return data;
    }
}
