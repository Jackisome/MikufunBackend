package com.devteam.mikufunbackend.service.serviceImpl;

import com.devteam.mikufunbackend.dao.ResourceInformationDao;
import com.devteam.mikufunbackend.entity.RecentDownloadVO;
import com.devteam.mikufunbackend.entity.RecentPlayVO;
import com.devteam.mikufunbackend.entity.ResourceEntity;
import com.devteam.mikufunbackend.service.serviceInterface.RecentService;
import com.devteam.mikufunbackend.util.TimeUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Service
public class RecentServiceImpl implements RecentService {
    @Autowired
    DataSource dataSource;

    @Autowired
    private ResourceInformationDao resourceInformationDao;

    Logger logger = LoggerFactory.getLogger(RecentServiceImpl.class);

    @Override
    public List<RecentPlayVO> recentPlay() throws Exception {

        List<RecentPlayVO> data = new ArrayList<>();
        try {
            List<ResourceEntity> list = resourceInformationDao.findRecentPlayResource();
            list.forEach(k -> {
                if (!k.getRecentPlayTime().equals(Timestamp.valueOf("2000-01-01 01:00:00.0"))) {
                    RecentPlayVO recentPlayVO = RecentPlayVO.builder()
                            .fileId(String.valueOf(k.getFileId()))
                            .fileName(k.getFileName())
                            .resourceName(k.getResourceName())
                            .time(TimeUtil.getFormattedTimeToDay(k.getRecentPlayTime()))
                            .imageUrl(k.getImageUrl())
                            .episode(k.getEpisodeTitle())
                            .videoTime(TimeUtil.getFormattedTimeFromSec((int) k.getRecentPlayPosition()))
                            .build();
                    data.add(recentPlayVO);
                    logger.info("timestamp:{}", k.getRecentPlayTime());
                }
            });
        } catch (Exception e) {
            logger.error(e.toString());
        }
        return data;
    }

    @Override
    public List<RecentDownloadVO> recentDownload() throws Exception {

        List<RecentDownloadVO> data = new ArrayList<>();
        try {
            List<ResourceEntity> list = resourceInformationDao.findRecentDownloadResource();
            list.forEach(k -> {
                RecentDownloadVO recentDownloadVO = RecentDownloadVO.builder()
                        .fileId(String.valueOf(k.getFileId()))
                        .fileName(k.getFileName())
                        .resourceName(k.getResourceName())
                        .time(TimeUtil.getFormattedTimeToDay(k.getDownloadTime()))
                        .imageUrl(k.getImageUrl())
                        .episode(k.getEpisodeTitle())
                        .build();
                data.add(recentDownloadVO);
            });
        } catch (Exception e) {
            logger.error(e.toString());
        }
        return data;
    }
}

