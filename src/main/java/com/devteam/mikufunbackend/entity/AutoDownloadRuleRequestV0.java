package com.devteam.mikufunbackend.entity;

import lombok.Data;

/**
 * @author Jackisome
 * @date 2021/10/22
 */
@Data
public class AutoDownloadRuleRequestV0 {
    String ruleName;
    String keyword;
    String activeResourceTime;
    boolean active;
}
