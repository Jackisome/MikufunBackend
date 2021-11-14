package com.devteam.mikufunbackend.controller;

import com.devteam.mikufunbackend.constant.Aria2Constant;
import com.devteam.mikufunbackend.entity.DownloadStatusTransferV0;
import com.devteam.mikufunbackend.handle.Aria2Exception;
import com.devteam.mikufunbackend.handle.FileIdException;
import com.devteam.mikufunbackend.service.serviceInterface.DownloadService;
import com.devteam.mikufunbackend.service.serviceInterface.LocalServerService;
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
    DownloadService downloadService;

    @Autowired
    LocalServerService localServerService;

    @PostMapping("")
    public Response download(@RequestParam("link") String link) throws Aria2Exception, IOException, DocumentException, InterruptedException {
        downloadService.download(link);
        return ResultUtil.success();
    }

    @PutMapping("/remove/{gids}")
    public Response remove(@PathVariable List<String> gids) throws IOException, InterruptedException {
        Map<String, Object> data = ResultUtil.getData();
        data.put("downloadEntries", downloadService.removeDownloadingFile(gids));
        return ResultUtil.success(data);
    }

    @PutMapping("/pause/{gids}")
    public Response pause(@PathVariable List<String> gids) throws IOException {
        List<DownloadStatusTransferV0> downloadStatusTransferV0s = downloadService.changeDownloadStatusAndGetResults(gids, Aria2Constant.downloadAction.PAUSE);
        Map<String, Object> data = ResultUtil.getData();
        data.put("downloadEntries", downloadStatusTransferV0s);
        return ResultUtil.success(data);
    }

    @PutMapping("/unpause/{gids}")
    public Response unpause(@PathVariable List<String> gids) throws IOException {
        List<DownloadStatusTransferV0> downloadStatusTransferV0s = downloadService.changeDownloadStatusAndGetResults(gids, Aria2Constant.downloadAction.UNPAUSE);
        Map<String, Object> data = ResultUtil.getData();
        data.put("downloadEntries", downloadStatusTransferV0s);
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
    public Response findResourceFile(@PathVariable String resourceId) {
        Map<String, Object> data = ResultUtil.getData();
        data.put("files", downloadService.getFinishFilesByResourceId(Integer.parseInt(resourceId)));
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

    @GetMapping("/diskspace")
    Response getDiskSpace() {
        Map<String, Object> data = ResultUtil.getData();
        data.put("diskSpace", localServerService.getDiskSpace());
        return ResultUtil.success(data);
    }
}
