package com.devteam.mikufunbackend.controller;

import com.devteam.mikufunbackend.service.serviceImpl.DownloadServiceImpl;
import com.devteam.mikufunbackend.service.serviceInterface.DownLoadService;
import com.devteam.mikufunbackend.util.Response;
import com.devteam.mikufunbackend.util.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author Jackisome
 * @date 2021/9/26
 */
@RestController
@RequestMapping("/api/v1")
public class DownloadController {
    @Autowired
    DownLoadService downloadService;

    @PostMapping("/download")
    public Response download(@RequestParam("link") String link) throws Exception {
        downloadService.download(link);
        return ResultUtil.success();
    }

//    @GetMapping("/download/downloading")
//    public Response findDownloadingFile() throws Exception {
//    }
}
