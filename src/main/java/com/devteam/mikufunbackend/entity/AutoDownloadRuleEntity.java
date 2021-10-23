package com.devteam.mikufunbackend.entity;

import lombok.Builder;
import lombok.Data;

import java.sql.Timestamp;

/**
 * @author Jackisome
 * @date 2021/10/22
 */
@Data
@Builder
public class AutoDownloadRuleEntity {
    int ruleId;
    String ruleName;
    String keyword;
    Timestamp activeResourceTime;
    Timestamp createTime;
    Timestamp updateTime;
    int active;
}
