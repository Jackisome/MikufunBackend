package com.devteam.mikufunbackend.controller;

import com.devteam.mikufunbackend.entity.DanmakuEntity;
import com.devteam.mikufunbackend.util.Response;
import com.devteam.mikufunbackend.util.ResultUtil;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author Jackisome
 * @date 2021/9/26
 */
@RestController
@RequestMapping("/api/v1/play")
public class PlayerController {
    @GetMapping("danmaku/{fileId}")
    public Response getDanmaku(@PathVariable String fileId) {
        Map<String, Object> data = ResultUtil.getData();
        List<DanmakuEntity> danmakuEntities = new ArrayList<>();
        danmakuEntities.add(new DanmakuEntity(230.523, 0, 16777215, "618c713c", "comment1"));
        danmakuEntities.add(new DanmakuEntity(25.837, 0, 16777215, "6b2884ac", "comment2"));
        data.put("code", 0);
        data.put("data", danmakuEntities);
        return ResultUtil.success(data);
    }
}
