package com.devteam.mikufunbackend.dao;

import com.devteam.mikufunbackend.entity.AutoDownloadRuleEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author Jackisome
 * @date 2021/10/22
 */
@Mapper
public interface AutoDownloadRuleDao {
    List<AutoDownloadRuleEntity> getAllAutoDownloadRules();

    int getAutoDownloadRuleStatus(int ruleId);

    List<AutoDownloadRuleEntity> getActiveAutoDownloadRuleStatus();

    int addAutoDownloadRule(AutoDownloadRuleEntity autoDownloadRuleEntity);

    int updateAutoDownloadRuleStatus(int ruleId, int active);

    int deleteAutoDownloadRule(int ruleId);
}
