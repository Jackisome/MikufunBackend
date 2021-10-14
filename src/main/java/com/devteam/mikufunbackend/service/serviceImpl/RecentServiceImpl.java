package com.devteam.mikufunbackend.service.serviceImpl;

import com.devteam.mikufunbackend.dao.ResourceInformationDao;
import com.devteam.mikufunbackend.entity.RecentStatusV0;
import com.devteam.mikufunbackend.entity.ResourceEntity;
import com.devteam.mikufunbackend.service.serviceInterface.RecentService;
import com.devteam.mikufunbackend.util.TimeUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class RecentServiceImpl implements RecentService {
    @Autowired
    DataSource dataSource;

    @Autowired
    private ResourceInformationDao resourceInformationDao;

    Logger logger = LoggerFactory.getLogger(RecentServiceImpl.class);

    @Override
    public List<RecentStatusV0> recentPlay() throws Exception{

        List<RecentStatusV0> data = new ArrayList<>();
        try {
            List<ResourceEntity> list = resourceInformationDao.findRecentPlayResource();
            list.forEach(k->{
                RecentStatusV0 recentStatusV0 = RecentStatusV0.builder()
                        .fileId(String.valueOf(k.getFileId()))
                        .resourceName(k.getResourceName())
                        .time(TimeUtil.getFormattedTimeToDay(k.getRecentPlayTime()))
                        .imageUrl(k.getImageUrl())
                        .episode(k.getEpisodeTitle())
                        .build();
                data.add(recentStatusV0);
            });
        } catch (Exception e) {
            logger.error(e.toString());
        }
        return data;
    }

    @Override
    public List<RecentStatusV0> recentDownload() throws Exception{
        List<RecentStatusV0> data = new ArrayList<>();
        try {
            List<ResourceEntity> list = resourceInformationDao.findRecentDownloadResource();
            list.forEach(k->{
                RecentStatusV0 recentStatusV0 = RecentStatusV0.builder()
                        .fileId(String.valueOf(k.getFileId()))
                        .resourceName(k.getResourceName())
                        .time(TimeUtil.getFormattedTimeToDay(k.getDownloadTime()))
                        .imageUrl(k.getImageUrl())
                        .episode(k.getEpisodeTitle())
                        .build();
                data.add(recentStatusV0);
            });
        } catch (Exception e) {
            logger.error(e.toString());
        }
        return data;
    }
}

