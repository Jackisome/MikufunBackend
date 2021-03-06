package com.devteam.mikufunbackend.controller;

import com.devteam.mikufunbackend.constant.ResponseEnum;
import com.devteam.mikufunbackend.entity.AutoDownloadRuleEntity;
import com.devteam.mikufunbackend.entity.AutoDownloadRuleRequestV0;
import com.devteam.mikufunbackend.entity.AutoDownloadRuleResponseV0;
import com.devteam.mikufunbackend.service.serviceInterface.AutoDownloadService;
import com.devteam.mikufunbackend.util.Response;
import com.devteam.mikufunbackend.util.ResultUtil;
import org.dom4j.DocumentException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.text.ParseException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * @author Jackisome
 * @date 2021/10/22
 */
@RestController
@RequestMapping("/api/v1/autodownload")
public class AutoDownloadController {

    @Autowired
    private AutoDownloadService autoDownloadService;

    @GetMapping("/rule")
    public Response getAutoDownloadRules() {
        Map<String, Object> data = ResultUtil.getData();
        List<AutoDownloadRuleResponseV0> autoDownloadRuleEntities = autoDownloadService.getAllAutoDownloadRules();
        data.put("rules", autoDownloadRuleEntities);
        return ResultUtil.success(data);
    }

    @PutMapping("/rule/status/{ruleId}")
    public Response changeAutoDownloadRuleStatus(@PathVariable String ruleId) {
        if (autoDownloadService.updateAutoDownloadRuleStatus(ruleId)) {
            return ResultUtil.success();
        } else {
            return ResultUtil.fail(ResponseEnum.AUTO_DOWNLOAD_ERROR);
        }
    }

    @DeleteMapping("/rule/{ruleId}")
    public Response deleteAutoDownloadRule(@PathVariable List<String> ruleId) {
        if (autoDownloadService.deleteAutoDownloadRule(ruleId)) {
            return ResultUtil.success();
        } else {
            return ResultUtil.fail(ResponseEnum.AUTO_DOWNLOAD_ERROR);
        }
    }

    @PostMapping("/rule")
    public Response addAutoDownloadRule(@RequestParam String ruleName, @RequestParam String keyword, @RequestParam String activeResourceTime, @RequestParam boolean active) throws ParseException {
        AutoDownloadRuleRequestV0 autoDownloadRuleRequestV0 = AutoDownloadRuleRequestV0.builder()
                .ruleName(ruleName)
                .keyword(keyword)
                .activeResourceTime(activeResourceTime)
                .active(active)
                .build();
        if (autoDownloadService.addAutoDownloadRule(autoDownloadRuleRequestV0)) {
            return ResultUtil.success();
        } else {
            return ResultUtil.fail(ResponseEnum.AUTO_DOWNLOAD_ERROR);
        }
    }

    @PutMapping("/rule/{ruleIds}")
    public Response findAutoDownloadResource(@PathVariable List<String> ruleIds) throws DocumentException, ParseException, IOException, InterruptedException {
        autoDownloadService.findDownloadableResource(ruleIds);
        return ResultUtil.success();
    }
}
