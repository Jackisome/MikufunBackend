package com.devteam.mikufunbackend.service.serviceImpl;

import com.devteam.mikufunbackend.constant.FavoriteStatusEnum;
import com.devteam.mikufunbackend.dao.FavoriteStatusRecordDao;
import com.devteam.mikufunbackend.entity.FavoriteStatusRecordEntity;
import com.devteam.mikufunbackend.entity.SearchResourceIntroductionVO;
import com.devteam.mikufunbackend.handle.FavoriteException;
import com.devteam.mikufunbackend.service.serviceInterface.FavoriteService;
import com.devteam.mikufunbackend.service.serviceInterface.SearchService;
import com.devteam.mikufunbackend.util.ParamUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.*;

@Service
public class FavoriteServiceImpl implements FavoriteService {

    @Resource
    private FavoriteStatusRecordDao favoriteStatusRecordDao;

    @Autowired
    private SearchService searchService;

    @Override
    public void setFavoriteStatus(Integer resourceId, String status) {
        // 检查 status 参数
        FavoriteStatusEnum favoriteStatus;
        favoriteStatus = FavoriteStatusEnum.convert(status);
        if (favoriteStatus == null) {
            throw new FavoriteException("illegal status value");
        }
        FavoriteStatusRecordEntity favoriteStatusRecordEntity = this.favoriteStatusRecordDao
                .findFavoriteStatusRecordByResourceId(resourceId);
        // 新增收藏记录
        if (favoriteStatusRecordEntity == null) {
            List<SearchResourceIntroductionVO> searchResourceIntroductionVOS = searchService.getResourceIntroduction("", resourceId);
            if (ParamUtil.isNotEmpty(searchResourceIntroductionVOS)) {
                if (FavoriteStatusEnum.NOT_SET.getValue().equals(status)) {
                    return;
                }
                SearchResourceIntroductionVO searchResourceIntroductionVO = searchResourceIntroductionVOS.get(0);
                FavoriteStatusRecordEntity favoriteStatusRecordEntity1 = FavoriteStatusRecordEntity.builder()
                        .resourceId(Integer.parseInt(searchResourceIntroductionVO.getResourceId()))
                        .resourceName(searchResourceIntroductionVO.getResourceName())
                        .airDate(searchResourceIntroductionVO.getAirDate())
                        .description(searchResourceIntroductionVO.getDescription())
                        .imageUrl(searchResourceIntroductionVO.getImageUrl())
                        .rating(searchResourceIntroductionVO.getRating())
                        .status(status)
                        .build();
                favoriteStatusRecordDao.addFavoriteStatusRecord(favoriteStatusRecordEntity1);
            } else {
                throw new FavoriteException("番剧详情未找到");
            }
        } else {
            // 检查状态更新是否合法
            if (favoriteStatus.getValue().equals(favoriteStatusRecordEntity.getStatus())) {
                throw new FavoriteException("illegal status update");
            }
            if (FavoriteStatusEnum.NOT_SET.getValue().equals(status)) {
                favoriteStatusRecordDao.deleteFavoriteStatusRecord(resourceId);
            } else {
                this.favoriteStatusRecordDao.updateFavoriteStatusRecord(resourceId, status);
            }
        }
    }

    @Override
    public String getFavoriteStatus(Integer resourceId) {
        FavoriteStatusRecordEntity favoriteStatusRecordEntity = this.favoriteStatusRecordDao.findFavoriteStatusRecordByResourceId(resourceId);
        if (favoriteStatusRecordEntity == null) {
            return FavoriteStatusEnum.NOT_SET.getValue();
        }
        return favoriteStatusRecordEntity.getStatus();
    }

    @Override
    public Map<Integer, String> getFavoriteStatusListById(List<Integer> resourceIds) {
        List<FavoriteStatusRecordEntity> favoriteStatusRecordEntityList =  this.favoriteStatusRecordDao.findFavoriteStatusRecordsByResourceId(resourceIds);
        Map<Integer, String> favoriteStatusMap = new HashMap<>();
        if (CollectionUtils.isEmpty(favoriteStatusRecordEntityList)) {
            return favoriteStatusMap;
        }
        favoriteStatusRecordEntityList.forEach(
                favoriteStatusRecordEntity -> favoriteStatusMap.put(favoriteStatusRecordEntity.getResourceId(), favoriteStatusRecordEntity.getStatus())
        );
        return favoriteStatusMap;
    }

    @Override
    public List<SearchResourceIntroductionVO> getFavoriteResourceByStatus(String status) {
        List<SearchResourceIntroductionVO> data = new ArrayList<>();
        FavoriteStatusEnum favoriteStatusEnum = FavoriteStatusEnum.convert(status);
        if (favoriteStatusEnum == null) {
            throw new FavoriteException("传入状态非法");
        }
        List<FavoriteStatusRecordEntity> favoriteStatusRecordEntities = favoriteStatusRecordDao.findFavoriteStatusRecordByStatus(status);
        for (FavoriteStatusRecordEntity favoriteStatusRecordEntity : favoriteStatusRecordEntities) {
            SearchResourceIntroductionVO searchResourceIntroductionVO = SearchResourceIntroductionVO.builder()
                    .resourceId(String.valueOf(favoriteStatusRecordEntity.getResourceId()))
                    .resourceName(favoriteStatusRecordEntity.getResourceName())
                    .airDate(favoriteStatusRecordEntity.getAirDate())
                    .description(favoriteStatusRecordEntity.getDescription())
                    .imageUrl(favoriteStatusRecordEntity.getImageUrl())
                    .rating(favoriteStatusRecordEntity.getRating())
                    .status(favoriteStatusRecordEntity.getStatus())
                    .build();
            data.add(searchResourceIntroductionVO);
        }
        return data;
    }

}
