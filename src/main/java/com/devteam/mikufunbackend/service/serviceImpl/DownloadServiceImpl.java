package com.devteam.mikufunbackend.service.serviceImpl;

import com.devteam.mikufunbackend.constant.Aria2Constant;
import com.devteam.mikufunbackend.dao.ResourceInformationDao;
import com.devteam.mikufunbackend.entity.*;
import com.devteam.mikufunbackend.handle.Aria2Exception;
import com.devteam.mikufunbackend.service.serviceInterface.Aria2Service;
import com.devteam.mikufunbackend.service.serviceInterface.DownloadService;
import com.devteam.mikufunbackend.service.serviceInterface.LocalServerService;
import com.devteam.mikufunbackend.util.ParamUtil;
import com.devteam.mikufunbackend.util.ResultUtil;
import lombok.SneakyThrows;
import org.dom4j.DocumentException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.CountDownLatch;

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
    private LocalServerService localServerService;

    @Autowired
    private ResourceInformationDao resourceInformationDao;

    @Override
    public boolean download(String link) throws DocumentException, IOException, Aria2Exception {
        return aria2Service.addUrl(link);
    }

    @Override
    public boolean changeDownloadStatus(String gid, Aria2Constant.downloadAction downloadAction) throws IOException {
        switch (downloadAction) {
            case PAUSE:
                return aria2Service.transferDownloadStatus(gid, Aria2Constant.METHOD_PAUSE);
            case UNPAUSE:
                return aria2Service.transferDownloadStatus(gid, Aria2Constant.METHOD_UNPAUSE);
            case REMOVE:
                return aria2Service.transferDownloadStatus(gid, Aria2Constant.METHOD_REMOVE);
            case REMOVE_DOWNLOAD_RESULT:
                return aria2Service.transferDownloadStatus(gid, Aria2Constant.METHOD_REMOVE_DOWNLOAD_RESULT);
            default:
                return false;
        }
    }

    @Override
    public DownloadStatusTransferV0 changeDownloadStatusAndGetResults(String gid, Aria2Constant.downloadAction downloadAction) throws IOException {
        return DownloadStatusTransferV0.builder()
                .gid(gid)
                .status(changeDownloadStatus(gid, downloadAction))
                .build();
    }

    @Override
    public List<DownloadStatusTransferV0> changeDownloadStatusAndGetResults(List<String> gids, Aria2Constant.downloadAction downloadAction) {
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
        List<DownloadStatusV0> data = new ArrayList<>();
        for (Aria2StatusV0 aria2StatusV0 : aria2StatusV0s) {
            String gid = aria2StatusV0.getGid();
            int downloadSpeed = aria2StatusV0.getDownloadSpeed();
            int uploadSpeed = aria2StatusV0.getUploadSpeed();
            String status = aria2StatusV0.getStatus();
            if ("active".equals(status) && aria2StatusV0.getCompletedLength() == aria2StatusV0.getTotalLength()) {
                status = "seeding";
            }
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
        logger.info("begin delete local files, fileIds: {}", Arrays.toString(fileIds.toArray()));
        List<SimpleFinishFileV0> data = new ArrayList<>();
        for (Integer fileId : fileIds) {
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
                logger.info("begin delete local file, fileId: {}", fileId);
                simpleFinishFileV0 = resourceEntity.getSimpleFinishFileV0();
                // 删除视频截图
                if (localServerService.deleteFile(resourceEntity.getImageUrl()) == 0) {
                    logger.warn("image not delete, image url: {}", resourceEntity.getImageUrl());
                }

                // 删除资源文件
                if (localServerService.deleteFile(ParamUtil.getFileDirectory(resourceEntity.getFileUuid())) == 0) {
                    logger.warn("local resource file not delete, file uuid: {}", resourceEntity.getFileUuid());
                }

                // 删除字幕文件
                if (!"".equals(resourceEntity.getSubtitlePath()) && localServerService.deleteFile(resourceEntity.getSubtitlePath()) == 0) {
                    logger.warn("subtitle not delete, subtitle path: {}", resourceEntity.getSubtitlePath());
                }

                if (resourceInformationDao.deleteResourceInformationByFileId(fileId) == 0) {
                    logger.warn("not delete record in resourceInformation table by fileId, fileId: {}", fileId);
                }

                logger.info("delete local file finish, fileId: {}", fileId);
                simpleFinishFileV0.setDelete(true);
            }
            data.add(simpleFinishFileV0);
        }
        logger.info("delete local files finish, fileIds: {}", Arrays.toString(fileIds.toArray()));
        return data;
    }

    @Override
    public List<DownloadStatusTransferV0> removeDownloadingFile(List<String> gids) throws IOException, InterruptedException {
        List<DownloadStatusTransferV0> data = Collections.synchronizedList(new ArrayList<>());
        if (ParamUtil.isNotEmpty(gids)) {
//            CountDownLatch countDownLatch = new CountDownLatch(gids.size());
//            for (String gid : gids) {
//                new Thread(new Runnable() {
//                    @SneakyThrows
//                    @Override
//                    public void run() {
//                        Aria2StatusV0 aria2StatusV0 = aria2Service.tellDownloadingFileStatus(gid);
//                        if (aria2StatusV0 == null) {
//                            logger.warn("not found download status related to gid, gid: {}", gid);
//                        }
//                        logger.info("begin remove download status and source file, gid: {}, files: {}", gid, aria2StatusV0.getFiles());
//                        changeDownloadStatus(gid, Aria2Constant.downloadAction.REMOVE);
//                        if (ParamUtil.isNotEmpty(aria2StatusV0.getFiles())) {
//                            for (Aria2FileV0 file : aria2StatusV0.getFiles()) {
//                                if (localServerService.deleteFile(file.getPath()) == 0) {
//                                    logger.warn("not delete source file, aria2FileV0: {}", file);
//                                }
//                            }
//                        }
//                        Thread.sleep(5000);
//                        DownloadStatusTransferV0 downloadStatusTransferV0 = changeDownloadStatusAndGetResults(gid, Aria2Constant.downloadAction.REMOVE_DOWNLOAD_RESULT);
//                        if (!downloadStatusTransferV0.isStatus()) {
//                            logger.warn("stop download status fail, gid: {}", gid);
//                        }
//                        data.add(downloadStatusTransferV0);
//                        logger.info("remove download status and source file finish, gid: {}, files: {}", gid, aria2StatusV0.getFiles());
//                        countDownLatch.countDown();
//                    }
//                });
//            }
//            countDownLatch.await();
            for (String gid : gids) {
                Aria2StatusV0 aria2StatusV0 = aria2Service.tellDownloadingFileStatus(gid);
                if (aria2StatusV0 == null) {
                    logger.warn("not found download status related to gid, gid: {}", gid);
                }
                logger.info("begin remove download status and source file, gid: {}, files: {}", gid, aria2StatusV0.getFiles());
                changeDownloadStatus(gid, Aria2Constant.downloadAction.REMOVE);
                Thread.sleep(5000);
                DownloadStatusTransferV0 downloadStatusTransferV0 = changeDownloadStatusAndGetResults(gid, Aria2Constant.downloadAction.REMOVE_DOWNLOAD_RESULT);
                if (!downloadStatusTransferV0.isStatus()) {
                    logger.warn("stop download status fail, gid: {}", gid);
                }
                // 无论是否停止下载状态，都需要进行删除源文件
                if (ParamUtil.isNotEmpty(aria2StatusV0.getFiles())) {
                    for (Aria2FileV0 file : aria2StatusV0.getFiles()) {
                        if (localServerService.deleteFile(file.getPath()) == 0) {
                            logger.warn("not delete source file, aria2FileV0: {}", file);
                        }
                    }
                }
                data.add(downloadStatusTransferV0);
                logger.info("remove download status and source file finish, gid: {}, files: {}", gid, aria2StatusV0.getFiles());
            }
        }
        return data;
    }
}
