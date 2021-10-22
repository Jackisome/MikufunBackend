package com.devteam.mikufunbackend.service.serviceImpl;

import com.devteam.mikufunbackend.dao.AutoDownloadRuleDao;
import com.devteam.mikufunbackend.entity.AutoDownloadRuleEntity;
import com.devteam.mikufunbackend.entity.AutoDownloadRuleRequestV0;
import com.devteam.mikufunbackend.entity.SearchResourceRespVO;
import com.devteam.mikufunbackend.handle.ParameterErrorException;
import com.devteam.mikufunbackend.service.serviceInterface.AutoDownloadService;
import com.devteam.mikufunbackend.service.serviceInterface.DownloadService;
import com.devteam.mikufunbackend.service.serviceInterface.SearchService;
import com.devteam.mikufunbackend.util.ParamUtil;
import org.dom4j.DocumentException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.util.List;

/**
 * @author Jackisome
 * @date 2021/10/22
 */
@Service
public class AutoDownloadServiceImpl implements AutoDownloadService {

    @Autowired
    private AutoDownloadRuleDao autoDownloadRuleDao;

    @Autowired
    private SearchService searchService;

    @Autowired
    private DownloadService downloadService;

    @Override
    public boolean addAutoDownloadRule(AutoDownloadRuleRequestV0 autoDownloadRuleRequestV0) throws ParseException {
        if (!ParamUtil.isNotEmpty(autoDownloadRuleRequestV0.getRuleName()) || !ParamUtil.isNotEmpty(autoDownloadRuleRequestV0.getKeyword()) || !ParamUtil.isLegalDate(autoDownloadRuleRequestV0.getActiveResourceTime())) {
            throw new ParameterErrorException("参数错误");
        }
        Timestamp activeResourceTime = ParamUtil.getDateFromString(autoDownloadRuleRequestV0.getActiveResourceTime());
        AutoDownloadRuleEntity autoDownloadRuleEntity = AutoDownloadRuleEntity.builder()
                .ruleName(autoDownloadRuleRequestV0.getRuleName())
                .keyword(autoDownloadRuleRequestV0.getKeyword())
                .activeResourceTime(activeResourceTime)
                .updateTime(activeResourceTime)
                .active(autoDownloadRuleRequestV0.isActive()? 1: 0)
                .build();
        int changeLine = autoDownloadRuleDao.addAutoDownloadRule(autoDownloadRuleEntity);
        return changeLine == 1;
    }

    @Override
    public boolean updateAutoDonwloadRuleStatus(String ruleId) {
        int ruleIdInInteger = Integer.parseInt(ruleId);
        int active = autoDownloadRuleDao.getAutoDownloadRuleStatus(ruleIdInInteger);
        int changeLine = autoDownloadRuleDao.updateAutoDownloadRuleStatus(ruleIdInInteger, (active + 1) % 2);
        return changeLine == 1;
    }

    @Override
    public List<AutoDownloadRuleEntity> getAllAutoDownloadRules() {
        return autoDownloadRuleDao.getAllAutoDownloadRules();
    }

    @Override
    public boolean deleteAutoDownloadRule(String ruleId) {
        int ruleIdInInteger = Integer.parseInt(ruleId);
        int changeLine = autoDownloadRuleDao.deleteAutoDownloadRule(ruleIdInInteger);
        return changeLine == 1;
    }

    @Override
    public void findDownloadableResource() throws ParseException, DocumentException, IOException, InterruptedException {
        List<AutoDownloadRuleEntity> autoDownloadRuleEntities = autoDownloadRuleDao.getActiveAutoDownloadRuleStatus();
        for (AutoDownloadRuleEntity autoDownloadRuleEntity : autoDownloadRuleEntities) {
            Timestamp updateTime = autoDownloadRuleEntity.getUpdateTime();
            Timestamp biggestUpdateTime = updateTime;
            int ruleId = autoDownloadRuleEntity.getRuleId();
            List<SearchResourceRespVO> searchResourceRespV0s = searchService.getResource(autoDownloadRuleEntity.getKeyword(), 0, 0);
            for (SearchResourceRespVO searchResourceRespV0 : searchResourceRespV0s) {
                Timestamp resourceTime = ParamUtil.getTimeFromString(searchResourceRespV0.getPublishDate());
                if (resourceTime.after(updateTime)) {
                    downloadService.download(searchResourceRespV0.getLink());
                    if (biggestUpdateTime.before(resourceTime)) {
                        biggestUpdateTime = resourceTime;
                        Thread.sleep(60000);
                    }
                }
            }
            if (biggestUpdateTime.after(updateTime)) {
                autoDownloadRuleDao.updateAutoDownloadRuleUpdateTime(ruleId, biggestUpdateTime);
            }
        }
    }
}
