package com.devteam.mikufunbackend.service.serviceImpl;

import com.devteam.mikufunbackend.constant.FavoriteStatusEnum;
import com.devteam.mikufunbackend.dao.FavoriteStatusRecordDao;
import com.devteam.mikufunbackend.entity.FavoriteStatusRecordEntity;
import com.devteam.mikufunbackend.handle.FavoriteException;
import com.devteam.mikufunbackend.service.serviceInterface.FavoriteService;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class FavoriteServiceImpl implements FavoriteService {

    @Resource
    private FavoriteStatusRecordDao favoriteStatusRecordDao;

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
            this.favoriteStatusRecordDao.addFavoriteStatusRecord(resourceId, favoriteStatus.getValue());
            return;
        }
        // 检查状态更新是否合法
        if (!FavoriteStatusEnum.checkStatusUpdate(FavoriteStatusEnum.convert(favoriteStatusRecordEntity.getStatus()),
                favoriteStatus)) {
            throw new FavoriteException("illegal status update");
        }
        this.favoriteStatusRecordDao.updateFavoriteStatusRecord(favoriteStatusRecordEntity.getRecordId(), resourceId, status);
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
                favoriteStatusRecordEntity -> favoriteStatusMap.put(favoriteStatusRecordEntity.getRecordId(), favoriteStatusRecordEntity.getStatus())
        );
        return favoriteStatusMap;
    }

}
