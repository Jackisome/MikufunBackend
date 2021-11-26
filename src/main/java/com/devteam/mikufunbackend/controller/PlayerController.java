package com.devteam.mikufunbackend.controller;

import com.devteam.mikufunbackend.constant.ResponseEnum;
import com.devteam.mikufunbackend.entity.*;
import com.devteam.mikufunbackend.service.serviceInterface.DownloadService;
import com.devteam.mikufunbackend.service.serviceInterface.PlayService;
import com.devteam.mikufunbackend.util.DanmakuResponse;
import com.devteam.mikufunbackend.util.Response;
import com.devteam.mikufunbackend.util.ResultUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * @author Jackisome
 * @date 2021/9/26
 */
@RestController
@RequestMapping("/api/v1/play")
@Validated
public class PlayerController {

    @Autowired
    PlayService playService;

    @Autowired
    DownloadService downloadService;

    Logger logger = LoggerFactory.getLogger(PlayerController.class);

    @GetMapping("/file/{fileId}")
    public Response getFileAddr(@PathVariable int fileId) throws Exception {
        Map<String, Object> data = playService.findPlayInformation(fileId);
        playService.updateRecentPlayTime(fileId);
        return ResultUtil.success(data);
    }

    @PostMapping("/file/record")
    public Response updatePos(@RequestParam @NotEmpty(message = "文件id不能为空") String fileId,
                              @RequestParam double videoTime) throws Exception {
        int fileIdInInteger = Integer.parseInt(fileId);
        if (playService.updatePos(fileIdInInteger, videoTime) == true) {
            logger.info("update recentPlayPosition success");
            return ResultUtil.success();
        } else {
            logger.info("update recentPlayPosition failed");
            return ResultUtil.fail(11, "record");
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

    @PostMapping("/danmaku/v3")
    public Response postDanmaku(@RequestParam String id,
                                @RequestParam String author,
                                @RequestParam double time,
                                @RequestParam String text,
                                @RequestParam int color,
                                @RequestParam int type) throws Exception {
        if (playService.postDanmaku(null)) {
            logger.info("post Danmaku success");
            return ResultUtil.success();
        } else {
            logger.info("post Danmaku failed");
            return ResultUtil.fail(ResponseEnum.UNKNOWN_ERROR);
        }
    }

    @GetMapping("/match/{fileId}")
    public Response getMatchEpisodes(@PathVariable Integer fileId) throws IOException {
        List<MatchEpisodeRespVO> matchEpisodeRespVOs = playService.getMatchEpisodes(fileId);
        Map<String, Object> data = ResultUtil.getData();
        data.put("matchEpisodes", matchEpisodeRespVOs);
        return ResultUtil.success(data);
    }

    @PutMapping("/match")
    public Response putMatchEpisode(@RequestBody @Valid MatchEpisodePutReqVO matchEpisodePutReqVO) {
        playService.putMatchEpisode(matchEpisodePutReqVO);
        return ResultUtil.success();
    }

    @GetMapping("/subtitles/{fileId}")
    public Response getSubtitles(@PathVariable Integer fileId) {
        List<MatchSubtitleVO> matchSubtitleVOs = playService.getMatchSubtitles(fileId);
        Map<String, Object> data = ResultUtil.getData();
        data.put("matchSubtitles", matchSubtitleVOs);
        return ResultUtil.success(data);
    }

    @PutMapping("/subtitles")
    public Response putMatchSubtitle(@RequestBody @Valid MatchSubtitleReqVO matchSubtitleReqVO) {
        // 暂时未实现
        return ResultUtil.success();
    }

}
