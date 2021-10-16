package com.devteam.mikufunbackend.controller;

import com.devteam.mikufunbackend.constant.ResponseEnum;
import com.devteam.mikufunbackend.entity.DanmakuPostV0;
import com.devteam.mikufunbackend.entity.ResourceEntity;
import com.devteam.mikufunbackend.service.serviceImpl.PlayServiceImpl;
import com.devteam.mikufunbackend.service.serviceInterface.PlayService;
import com.devteam.mikufunbackend.util.DanmakuResponse;
import com.devteam.mikufunbackend.util.Response;
import com.devteam.mikufunbackend.util.ResultUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @author Jackisome
 * @date 2021/9/26
 */
@RestController
@RequestMapping("/api/v1/play")
public class PlayerController {

    @Autowired
    PlayService playService;

    Logger logger = LoggerFactory.getLogger(PlayerController.class);

    @GetMapping("/file/{fileId}")
    public Response getFileAddr(@PathVariable int fileId) {
        Map<String, Object> data = ResultUtil.getData();
        try {
            ResourceEntity resourceEntity = playService.getFileAddr(fileId);
            data.put("fileUrl", resourceEntity.getFileDirectory() + "/index.m3u8");
            data.put("fileName", resourceEntity.getFileName());
            data.put("ResourceId", resourceEntity.getResourceId());
            data.put("ResourceName", resourceEntity.getResourceName());
        } catch (Exception e) {
            logger.error(e.toString());
        }
        return ResultUtil.success(data);
    }

    @PutMapping("/file/{fileId}/record/{videoTime}")
    public void updatePos(@PathVariable int fileId, @PathVariable int videoTime) throws Exception {
        if (playService.updatePos(fileId, videoTime) == true) {
            logger.info("update recentPlayPosition success");
        } else {
            logger.info("update recentPlayPosition failed");
        }
    }

    @GetMapping("/danmaku/v3")
    public DanmakuResponse getDanmaku(@RequestParam int id) throws Exception {
        List<List<Object>> data = playService.getDanmaku(id);
        return ResultUtil.getDanmaku(data);
//        Map<String, Object> data = ResultUtil.getData();
//        data.put("danmu", playService.getDanmaku(fileId));
//        if (data.get("danmu") != null)
//            return ResultUtil.success(data);
//        else
//            return ResultUtil.fail(ResponseEnum.FILEID_ERROR);
    }

    @PostMapping("/danmaku")
    public Response postDanmaku(@RequestBody DanmakuPostV0 body) throws Exception {
        if (playService.postDanmaku(body)) {
            logger.info("post Danmaku success");
            return ResultUtil.success();
        } else {
            logger.info("post Danmaku failed");
            return ResultUtil.fail(ResponseEnum.UNKNOWN_ERROR);
        }
    }

    @GetMapping("/danmaku/rule")
    public Response getRegex() throws Exception {
        Map<String, Object> data = ResultUtil.getData();
        data.put("regexs", playService.getRegex());
        return ResultUtil.success(data);
    }

}
