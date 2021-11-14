package com.devteam.mikufunbackend.service.serviceInterface;

import com.devteam.mikufunbackend.entity.AutoDownloadRuleEntity;
import com.devteam.mikufunbackend.entity.AutoDownloadRuleRequestV0;
import com.devteam.mikufunbackend.entity.AutoDownloadRuleResponseV0;
import org.dom4j.DocumentException;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;

/**
 * @author Jackisome
 * @date 2021/10/22
 */
public interface AutoDownloadService {
    boolean addAutoDownloadRule(AutoDownloadRuleRequestV0 autoDownloadRuleRequestV0) throws ParseException;

    boolean updateAutoDownloadRuleStatus(String ruleId);

    List<AutoDownloadRuleResponseV0> getAllAutoDownloadRules();

    boolean deleteAutoDownloadRule(List<String> ruleIds);

    void findDownloadableResource() throws ParseException, DocumentException, IOException, InterruptedException;

    void findDownloadableResource(List<String> ruleIds) throws DocumentException, ParseException, IOException, InterruptedException;
}
