package com.devteam.mikufunbackend.controller;

import com.devteam.mikufunbackend.handle.Aria2Exception;
import com.devteam.mikufunbackend.service.serviceImpl.DownloadServiceImpl;
import com.devteam.mikufunbackend.service.serviceInterface.DownLoadService;
import com.devteam.mikufunbackend.util.Response;
import com.devteam.mikufunbackend.util.ResultUtil;
import org.dom4j.DocumentException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Map;

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
    public Response download(@RequestParam("link") String link) throws Aria2Exception, IOException, DocumentException {
        downloadService.download(link);
        return ResultUtil.success();
    }

    @DeleteMapping("/download/{gid}")
    public Response remove(@PathVariable String gid) throws IOException {
        downloadService.remove(gid);
        Map<String, Object> data = ResultUtil.getData();
        data.put("removed", gid);
        return ResultUtil.success(data);
    }

//    @GetMapping("/download/downloading")
//    public Response findDownloadingFile() throws Exception {
//    }
}
