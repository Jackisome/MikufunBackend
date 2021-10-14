package com.devteam.mikufunbackend.controller;

import com.devteam.mikufunbackend.constant.ResponseEnum;
import com.devteam.mikufunbackend.entity.DanmakuPostV0;
import com.devteam.mikufunbackend.entity.ResourceEntity;
import com.devteam.mikufunbackend.service.serviceImpl.PlayServiceImpl;
import com.devteam.mikufunbackend.service.serviceInterface.PlayService;
import com.devteam.mikufunbackend.util.Response;
import com.devteam.mikufunbackend.util.ResultUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
    public Response getFileAddr(@PathVariable int fileId) throws Exception{
        Map<String, Object> data = ResultUtil.getData();
        try {
            ResourceEntity resourceEntity = playService.getFileAddr(fileId);
            data.put("fileUrl", resourceEntity.getFileDirectory());
            data.put("fileName", resourceEntity.getFileName());
            data.put("ResourceId", resourceEntity.getResourceId());
            data.put("ResourceName", resourceEntity.getResourceName());
        }catch (Exception e){
            logger.error(e.toString());
        }
        if(data.get("fileUrl")!=null)
            return ResultUtil.success(data);
        else
            return ResultUtil.fail(ResponseEnum.FILEID_ERROR);
    }

    @PutMapping("/file/{fileId}/record/{videoTime}")
    public void updatePos(@PathVariable int fileId,@PathVariable int videoTime) throws Exception{
        if(playService.updatePos(fileId,videoTime)==true){
            logger.info("update recentPlayPosition success");
        }else{
            logger.info("update recentPlayPosition failed");
        }
    }

    @GetMapping("/danmaku/{fileId}")
    public Response getDanmaku(@PathVariable int fileId) throws Exception{
        Map<String, Object> data = ResultUtil.getData();
        data.put("danmu",playService.getDanmaku(fileId));
        if(data.get("danmu")!=null)
            return ResultUtil.success(data);
        else
            return ResultUtil.fail(ResponseEnum.FILEID_ERROR);
    }

    @PostMapping("/danmaku")
    public void postDanmaku(@RequestBody DanmakuPostV0 body) throws Exception{
        if(playService.postDanmaku(body)==true){
            logger.info("post Danmaku success");
        }else{
            logger.info("post Danmaku failed");
        }
    }

    @GetMapping("/danmaku/rule")
    public Response getRegex() throws Exception{
        Map<String, Object> data = ResultUtil.getData();
        data.put("regexs",playService.getRegex());
        return ResultUtil.success(data);
    }

}
