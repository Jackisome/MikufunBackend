package com.devteam.mikufunbackend.service.serviceImpl;

import com.devteam.mikufunbackend.dao.ResourceInformationDao;
import com.devteam.mikufunbackend.entity.OrganizeV0;
import com.devteam.mikufunbackend.entity.RecentDownloadV0;
import com.devteam.mikufunbackend.entity.RecentPlayV0;
import com.devteam.mikufunbackend.entity.ResourceEntity;
import com.devteam.mikufunbackend.service.serviceInterface.RecentService;
import com.devteam.mikufunbackend.util.IOUtil;
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
    public List<RecentPlayV0> recentPlay() throws Exception {

        List<RecentPlayV0> data = new ArrayList<>();
        try {
            List<ResourceEntity> list = resourceInformationDao.findRecentPlayResource();
            list.forEach(k -> {
                if (!k.getRecentPlayTime().equals(Timestamp.valueOf("2000-01-01 01:00:00.0"))) {
                    RecentPlayV0 recentPlayV0 = RecentPlayV0.builder()
                            .fileId(String.valueOf(k.getFileId()))
                            .fileName(k.getFileName())
                            .resourceName(k.getResourceName())
                            .time(TimeUtil.getFormattedTimeToDay(k.getRecentPlayTime()))
                            .imageUrl(k.getImageUrl())
                            .episode(k.getEpisodeTitle())
                            .videoTime(TimeUtil.getFormattedTimeFromSec((int) k.getRecentPlayPosition()))
                            .build();
                    data.add(recentPlayV0);
                    logger.info("timestamp:{}", k.getRecentPlayTime());
                }
            });
        } catch (Exception e) {
            logger.error(e.toString());
        }
        return data;
    }

    @Override
    public List<RecentDownloadV0> recentDownload() throws Exception {

        List<RecentDownloadV0> data = new ArrayList<>();
        try {
            List<ResourceEntity> list = resourceInformationDao.findRecentDownloadResource();
            list.forEach(k -> {
                RecentDownloadV0 recentDownloadV0 = RecentDownloadV0.builder()
                        .fileId(String.valueOf(k.getFileId()))
                        .fileName(k.getFileName())
                        .resourceName(k.getResourceName())
                        .time(TimeUtil.getFormattedTimeToDay(k.getDownloadTime()))
                        .imageUrl(k.getImageUrl())
                        .episode(k.getEpisodeTitle())
                        .build();
                data.add(recentDownloadV0);
            });
        } catch (Exception e) {
            logger.error(e.toString());
        }
        return data;
    }
}

