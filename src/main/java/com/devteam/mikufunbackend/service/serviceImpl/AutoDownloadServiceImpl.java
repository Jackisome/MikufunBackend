package com.devteam.mikufunbackend.service.serviceImpl;

import com.devteam.mikufunbackend.dao.AutoDownloadRuleDao;
import com.devteam.mikufunbackend.entity.AutoDownloadRuleEntity;
import com.devteam.mikufunbackend.entity.AutoDownloadRuleRequestVO;
import com.devteam.mikufunbackend.entity.AutoDownloadRuleResponseVO;
import com.devteam.mikufunbackend.entity.SearchResourceRespVO;
import com.devteam.mikufunbackend.handle.ParameterErrorException;
import com.devteam.mikufunbackend.service.serviceInterface.AutoDownloadService;
import com.devteam.mikufunbackend.service.serviceInterface.DownloadService;
import com.devteam.mikufunbackend.service.serviceInterface.SearchService;
import com.devteam.mikufunbackend.util.ParamUtil;
import org.dom4j.DocumentException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Jackisome
 * @date 2021/10/22
 */
@Service
public class AutoDownloadServiceImpl implements AutoDownloadService {

    Logger logger = LoggerFactory.getLogger(AutoDownloadService.class);

    @Autowired
    private AutoDownloadRuleDao autoDownloadRuleDao;

    @Autowired
    private SearchService searchService;

    @Autowired
    private DownloadService downloadService;

    @Override
    public boolean addAutoDownloadRule(AutoDownloadRuleRequestVO autoDownloadRuleRequestVO) throws ParseException {
        if (!ParamUtil.isNotEmpty(autoDownloadRuleRequestVO.getRuleName()) || !ParamUtil.isNotEmpty(autoDownloadRuleRequestVO.getKeyword()) || !ParamUtil.isLegalDate(autoDownloadRuleRequestVO.getActiveResourceTime())) {
            throw new ParameterErrorException("参数错误");
        }
        logger.info("receive autoDownloadRuleRequestV0: {}", autoDownloadRuleRequestVO);
        Timestamp activeResourceTime = ParamUtil.getDateFromString(autoDownloadRuleRequestVO.getActiveResourceTime());
        AutoDownloadRuleEntity autoDownloadRuleEntity = AutoDownloadRuleEntity.builder()
                .ruleName(autoDownloadRuleRequestVO.getRuleName())
                .keyword(autoDownloadRuleRequestVO.getKeyword())
                .activeResourceTime(activeResourceTime)
                .updateTime(activeResourceTime)
                .active(autoDownloadRuleRequestVO.isActive()? 1: 0)
                .build();
        logger.info("begin add auto download rule to auto download rule table, autoDownloadRuleEntity: {}", autoDownloadRuleEntity);
        int changeLine = autoDownloadRuleDao.addAutoDownloadRule(autoDownloadRuleEntity);
        logger.info("add auto download rule, autoDownloadRuleEntity: {}", autoDownloadRuleEntity);
        return changeLine == 1;
    }

    @Override
    public boolean updateAutoDownloadRuleStatus(String ruleId) {
        int ruleIdInInteger = Integer.parseInt(ruleId);
        int active = autoDownloadRuleDao.getAutoDownloadRuleStatus(ruleIdInInteger);
        logger.info("change auto download rule status, ruleId: {}, status from {} to {}", ruleId, active, (active + 1) % 2);
        int changeLine = autoDownloadRuleDao.updateAutoDownloadRuleStatus(ruleIdInInteger, (active + 1) % 2);
        return changeLine == 1;
    }

    @Override
    public List<AutoDownloadRuleResponseVO> getAllAutoDownloadRules() {
        List<AutoDownloadRuleEntity> autoDownloadRuleEntities = autoDownloadRuleDao.getAllAutoDownloadRules();
        List<AutoDownloadRuleResponseVO> data = new ArrayList<>();
        autoDownloadRuleEntities.forEach(autoDownloadRuleEntity -> {
            data.add(AutoDownloadRuleResponseVO.builder()
                    .ruleId(String.valueOf(autoDownloadRuleEntity.getRuleId()))
                    .ruleName(autoDownloadRuleEntity.getRuleName())
                    .keyword(autoDownloadRuleEntity.getKeyword())
                    .activeResourceTime(ParamUtil.getStringFromDate(autoDownloadRuleEntity.getActiveResourceTime()))
                    .createTime(ParamUtil.getStringFromTime(autoDownloadRuleEntity.getCreateTime()))
                    .updateTime(ParamUtil.getStringFromTime(autoDownloadRuleEntity.getUpdateTime()))
                    .active(autoDownloadRuleEntity.getActive() == 1)
                    .build());
        });
        logger.info("get all auto download rules, data: {}", data);
        return data;
    }

    @Override
    public boolean deleteAutoDownloadRule(List<String> ruleIds) {
        if (!ParamUtil.isNotEmpty(ruleIds)) {
            throw new ParameterErrorException("没有需要删除的自动下载规则");
        }
        int deleteCount = 0;
        for (String ruleId : ruleIds) {
            int ruleIdInInteger = Integer.parseInt(ruleId);
            if (autoDownloadRuleDao.deleteAutoDownloadRule(ruleIdInInteger) == 1) {
                deleteCount += 1;
                logger.info("delete auto download rule, ruleId: {}", ruleIdInInteger);
            } else {
                logger.error("no delete auto download rule, ruleId: {}", ruleIdInInteger);
            }
        }
        return ruleIds.size() == deleteCount;
    }

    @Override
    public void findDownloadableResource() throws ParseException, DocumentException, IOException, InterruptedException {
        List<AutoDownloadRuleEntity> autoDownloadRuleEntities = autoDownloadRuleDao.getActiveAutoDownloadRuleStatus();
        for (AutoDownloadRuleEntity autoDownloadRuleEntity : autoDownloadRuleEntities) {
            findAndUpdateResource(autoDownloadRuleEntity);
        }
    }

    @Override
    public void findDownloadableResource(List<String> ruleIds) {
        if (!ParamUtil.isNotEmpty(ruleIds)) {
            throw new ParameterErrorException("没有需要运行的自动下载规则");
        }
        new Thread(() -> {
            for (String ruleId : ruleIds) {
                int ruleIdInInteger = Integer.parseInt(ruleId);
                AutoDownloadRuleEntity autoDownloadRuleEntity = autoDownloadRuleDao.getAutoDownloadRuleByRuleId(ruleIdInInteger);
                if (autoDownloadRuleEntity == null) {
                    throw new ParameterErrorException("ruleId对应规则不存在");
                }
                try {
                    findAndUpdateResource(autoDownloadRuleEntity);
                } catch (Exception e) {
                    logger.error(e.getMessage());
                }
            }
        }).start();
    }

    private void findAndUpdateResource(AutoDownloadRuleEntity autoDownloadRuleEntity) throws ParseException, DocumentException, IOException, InterruptedException {
        logger.info("begin find downloadable source, auto download rule name: {}, keyword: {}", autoDownloadRuleEntity.getRuleName(), autoDownloadRuleEntity.getKeyword());
        Timestamp updateTime = autoDownloadRuleEntity.getUpdateTime();
        Timestamp biggestUpdateTime = updateTime;
        int ruleId = autoDownloadRuleEntity.getRuleId();
        List<SearchResourceRespVO> searchResourceRespV0s = searchService.getResource(autoDownloadRuleEntity.getKeyword(), 0, 0);
        for (SearchResourceRespVO searchResourceRespV0 : searchResourceRespV0s) {
            Timestamp resourceTime = ParamUtil.getTimeFromString(searchResourceRespV0.getPublishDate());
            if (resourceTime.after(updateTime)) {
                logger.info("begin auto download new resource, file name: {}", searchResourceRespV0.getFileName());
                downloadService.download(searchResourceRespV0.getLink());
                if (biggestUpdateTime.before(resourceTime)) {
                    biggestUpdateTime = resourceTime;
                }
            }
        }
        if (biggestUpdateTime.after(updateTime)) {
            autoDownloadRuleDao.updateAutoDownloadRuleUpdateTime(ruleId, biggestUpdateTime);
        }
        logger.info("find downloadable source finish, auto download rule name: {}, keyword: {}", autoDownloadRuleEntity.getRuleName(), autoDownloadRuleEntity.getKeyword());
    }
}
