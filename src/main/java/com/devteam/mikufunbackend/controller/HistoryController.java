package com.devteam.mikufunbackend.controller;

import com.devteam.mikufunbackend.service.serviceInterface.RecentService;
import com.devteam.mikufunbackend.util.Response;
import com.devteam.mikufunbackend.util.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @author Jackisome
 * @date 2021/9/26
 */
@RestController
@RequestMapping("/api/v1/recent")
public class HistoryController {
    @Autowired
    RecentService recentService;

    @GetMapping("/play")
    public Response recentPlay() throws Exception{
        Map<String,Object> data=ResultUtil.getData();
        data.put("recentPlayFiles",recentService.recentPlay());
        return ResultUtil.success(data);
    }

    @GetMapping("/download")
    public Response recentDownload() throws Exception{
        Map<String,Object> data=ResultUtil.getData();
        data.put("recentDownloadFiles",recentService.recentDownload());
        return ResultUtil.success(data);
    }
}
