package com.devteam.mikufunbackend.controller;

import com.devteam.mikufunbackend.entity.OrganizeV0;
import com.devteam.mikufunbackend.service.serviceInterface.OrganizeService;
import com.devteam.mikufunbackend.util.Response;
import com.devteam.mikufunbackend.util.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @author Jackisome
 * @date 2021/10/23
 */
@RestController
@RequestMapping("/api/v1/organize")
public class OrganizeController {

    @Autowired
    private OrganizeService organizeService;

    @GetMapping("/setting")
    public Response getSetting() {
        Map<String, Object> data = ResultUtil.getData();
        OrganizeV0 organizeV0 = organizeService.getProperty();
        data.put("setting", organizeV0);
        return ResultUtil.success(data);
    }

    @PostMapping("/setting")
    public Response setSetting(@RequestParam String userPassword, @RequestParam List<String> visitorPasswords, @RequestParam String transferType,
                               @RequestParam String fontSize, @RequestParam String fontColor, @RequestParam String fontBottom,
                               @RequestParam boolean defaultStatus, @RequestParam String regex) {
        organizeService.setProperty(userPassword, visitorPasswords, transferType, fontSize, fontColor, fontBottom, defaultStatus, regex);
        return ResultUtil.success();
    }
}
