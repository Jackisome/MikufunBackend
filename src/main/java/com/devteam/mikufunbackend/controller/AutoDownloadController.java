package com.devteam.mikufunbackend.controller;

import com.devteam.mikufunbackend.entity.AutoDownloadRuleRequestV0;
import com.devteam.mikufunbackend.util.Response;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * @author Jackisome
 * @date 2021/10/22
 */
@RestController
@RequestMapping("/api/v1/autodownload")
public class AutoDownloadController {
    @GetMapping("/rule")
    public Response getAutoDownloadRules() {
        return null;
    }

    @PutMapping("/rule/status/{ruleId}")
    public Response changeAutoDownloadRuleStatus(@PathVariable int ruleId) {
        return null;
    }

    @DeleteMapping("/rule/{ruleId}")
    public Response deleteAutoDownloadRule(@PathVariable int ruleId) {
        return null;
    }

    @PostMapping("/rule")
    public Response addAutoDownloadRule(@RequestParam AutoDownloadRuleRequestV0 autoDownloadRuleRequestV0) {
        return null;
    }
}
