package com.devteam.mikufunbackend.dao;

import com.devteam.mikufunbackend.entity.FavoriteStatusRecordEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface FavoriteStatusRecordDao {

    int addFavoriteStatusRecord(Integer resourceId, String status);

    int updateFavoriteStatusRecord(Integer recordId, Integer resourceId, String status);

    FavoriteStatusRecordEntity findFavoriteStatusRecordByResourceId(Integer resourceId);

    List<FavoriteStatusRecordEntity> findFavoriteStatusRecordByStatus(String status);

//    int deleteFavoriteStatusRecord(Integer recordId);

}