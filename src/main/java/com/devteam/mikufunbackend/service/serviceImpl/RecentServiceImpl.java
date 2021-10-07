package com.devteam.mikufunbackend.service.serviceImpl;

import com.alibaba.fastjson.JSON;
import com.devteam.mikufunbackend.dao.ResourceInformationDao;
import com.devteam.mikufunbackend.entity.CalendarInfoV0;
import com.devteam.mikufunbackend.entity.RecentStatusV0;
import com.devteam.mikufunbackend.service.serviceInterface.RecentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

@Service
public class RecentServiceImpl implements RecentService {
    @Autowired
    DataSource dataSource;

    Logger logger = LoggerFactory.getLogger(RecentServiceImpl.class);

    private static final String sql1="select file_id, resource_name, recent_play_time, image_url" +
            " from mikufun.mikufun_resource_information order by recent_play_time desc";

    private static final String sql2="select file_id, resource_name, download_time, image_url" +
            " from mikufun.mikufun_resource_information order by download_time desc";

    @Override
    public List<RecentStatusV0> recentPlay() throws Exception{

        List<RecentStatusV0> data = new ArrayList<>();
        try {
            Connection connection = dataSource.getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql1);

            while (resultSet.next()) {
                RecentStatusV0 recentStatusV0 = RecentStatusV0.builder()
                        .fileId(String.valueOf(resultSet.getInt("file_id")))
                        .resourceName(resultSet.getString("resource_name"))
                        .time(resultSet.getTimestamp("recent_play_time"))
                        .imageUrl(resultSet.getString("image_url"))
                        .episode(resultSet.getInt("recent_play_position"))
                        .build();
                data.add(recentStatusV0);
            }
            statement.close();
            connection.close();
        } catch (SQLException e) {
            logger.error(e.toString());
        }
        return data;
    }

    @Override
    public List<RecentStatusV0> recentDownload() throws Exception{
        List<RecentStatusV0> data = new ArrayList<>();
        try {
            Connection connection = dataSource.getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql2);

            while (resultSet.next()) {
                RecentStatusV0 recentStatusV0 = RecentStatusV0.builder()
                        .fileId(String.valueOf(resultSet.getInt("file_id")))
                        .resourceName(resultSet.getString("resource_name"))
                        .time(resultSet.getTimestamp("download_time"))
                        .imageUrl(resultSet.getString("image_url"))
                        .episode(resultSet.getInt("recent_play_position"))
                        .build();
                data.add(recentStatusV0);
            }
            statement.close();
            connection.close();
        } catch (SQLException e) {
            logger.error(e.toString());
        }
        return data;
    }
}

