package com.devteam.mikufunbackend.entity;

import lombok.Builder;
import lombok.Data;

/**
 * @author Jackisome
 * @date 2021/10/23
 */
@Data
@Builder
public class AutoDownloadRuleResponseVO {
    String ruleId;
    String ruleName;
    String keyword;
    String activeResourceTime;
    String createTime;
    String updateTime;
    boolean active;
}
