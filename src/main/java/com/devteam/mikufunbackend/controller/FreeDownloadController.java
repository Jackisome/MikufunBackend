package com.devteam.mikufunbackend.controller;

import com.devteam.mikufunbackend.entity.FileV0;
import com.devteam.mikufunbackend.service.serviceInterface.FreeDownloadService;
import com.devteam.mikufunbackend.service.serviceInterface.LocalServerService;
import com.devteam.mikufunbackend.util.Response;
import com.devteam.mikufunbackend.util.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * @author Jackisome
 * @date 2021/10/23
 */
@RestController
@RequestMapping("/api/v1/freedownload")
public class FreeDownloadController {

    @Autowired
    private FreeDownloadService freeDownloadService;

    @Autowired
    private LocalServerService localServerService;

    @PostMapping("")
    public Response addFreeDownloadFile(@RequestParam String link) throws IOException {
        freeDownloadService.createFreeDownloadFile(link);
        return ResultUtil.success();
    }

    @GetMapping("/finish")
    public Response getFreeDownloadFile() {
        Map<String, Object> data = ResultUtil.getData();
        List<FileV0> fileV0s = freeDownloadService.getAllFreeDownloadFile();
        data.put("finishFiles", fileV0s);
        return ResultUtil.success(data);
    }

    @PostMapping("/finish/delete")
    public Response deleteFreeDownloadFile(@RequestParam String fileUrl) {
        Map<String, Object> data = ResultUtil.getData();
        int deleteFileNumber = localServerService.deleteFile(fileUrl);
        data.put("deleteFileNumber", deleteFileNumber);
        return ResultUtil.success(data);
    }
}
