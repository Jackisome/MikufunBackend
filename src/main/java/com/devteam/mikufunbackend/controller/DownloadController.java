package com.devteam.mikufunbackend.controller;

import com.devteam.mikufunbackend.handle.Aria2Exception;
import com.devteam.mikufunbackend.handle.FileIdException;
import com.devteam.mikufunbackend.service.serviceInterface.DownLoadService;
import com.devteam.mikufunbackend.util.Response;
import com.devteam.mikufunbackend.util.ResultUtil;
import org.dom4j.DocumentException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author Jackisome
 * @date 2021/9/26
 */
@RestController
@RequestMapping("/api/v1/download")
public class DownloadController {
    @Autowired
    DownLoadService downloadService;

    @PostMapping("")
    public Response download(@RequestParam("link") String link) throws Aria2Exception, IOException, DocumentException, InterruptedException {
        downloadService.download(link);
        return ResultUtil.success();
    }

    @DeleteMapping("/{gid}")
    public Response remove(@PathVariable String gid) throws IOException {
        downloadService.remove(gid);
        Map<String, Object> data = ResultUtil.getData();
        data.put("removed", gid);
        return ResultUtil.success(data);
    }

    @GetMapping("/downloading")
    public Response findDownloadingFile() throws Aria2Exception, IOException {
        Map<String, Object> data = ResultUtil.getData();
        data.put("downloadingFiles", downloadService.getDownloadingFiles());
        return ResultUtil.success(data);
    }

    @GetMapping("/finish")
    public Response findLocalFile() {
        Map<String, Object> data = ResultUtil.getData();
        data.put("finishFiles", downloadService.getFinishFiles());
        return ResultUtil.success(data);
    }

    @GetMapping("/finish/list")
    public Response findResourceList() {
        Map<String, Object> data = ResultUtil.getData();
        data.put("simpleResources", downloadService.getResourceList());
        return ResultUtil.success(data);
    }

    @GetMapping("/finish/resource/{resourceId}")
    public Response findResourceFile(@PathVariable int resourceId) {
        Map<String, Object> data = ResultUtil.getData();
        data.put("files", downloadService.getFinishFilesByResourceId(resourceId));
        return ResultUtil.success(data);
    }

    @DeleteMapping("/finish/{fileIdList}")
    public Response deleteLocalFiles(@PathVariable String fileIdList) throws IOException, InterruptedException {
        Map<String, Object> data = ResultUtil.getData();
        List<Integer> fileIds = new ArrayList<>();
        String[] strings = fileIdList.split(",");
        for (String string : strings) {
            try {
                int fileId = Integer.parseInt(string);
                fileIds.add(fileId);
            } catch (NumberFormatException e) {
                throw new FileIdException("fileId不为整数，fileId");
            }
        }
        data.put("deleteFiles", downloadService.deleteLocalFiles(fileIds));
        return ResultUtil.success(data);
    }
}
