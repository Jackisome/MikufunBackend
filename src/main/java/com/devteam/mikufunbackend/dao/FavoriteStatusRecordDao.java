package com.devteam.mikufunbackend.dao;

import com.devteam.mikufunbackend.entity.FavoriteStatusRecordEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface FavoriteStatusRecordDao {

    int addFavoriteStatusRecord(FavoriteStatusRecordEntity favoriteStatusRecordEntity);

    int updateFavoriteStatusRecord(Integer resourceId, String status);

    FavoriteStatusRecordEntity findFavoriteStatusRecordByResourceId(Integer resourceId);

    List<FavoriteStatusRecordEntity> findFavoriteStatusRecordByStatus(String status);

    List<FavoriteStatusRecordEntity> findFavoriteStatusRecordsByResourceId(List<Integer> resourceIds);

    int deleteFavoriteStatusRecord(int resourceId);
}