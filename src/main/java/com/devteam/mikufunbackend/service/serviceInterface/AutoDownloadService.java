package com.devteam.mikufunbackend.service.serviceInterface;

import com.devteam.mikufunbackend.entity.AutoDownloadRuleEntity;
import com.devteam.mikufunbackend.entity.AutoDownloadRuleRequestV0;
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

    boolean updateAutoDonwloadRuleStatus(String ruleId);

    List<AutoDownloadRuleEntity> getAllAutoDownloadRules();

    boolean deleteAutoDownloadRule(String ruleId);

    void findDownloadableResource() throws ParseException, DocumentException, IOException, InterruptedException;
}
