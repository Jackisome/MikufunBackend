package com.devteam.mikufunbackend.controller;

import com.devteam.mikufunbackend.entity.ResourceMatchV0;
import com.devteam.mikufunbackend.service.serviceInterface.TransferService;
import com.devteam.mikufunbackend.util.Response;
import com.devteam.mikufunbackend.util.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.List;
import java.util.Map;

/**
 * @author Jackisome
 * @date 2021/9/26
 */
@RestController
public class TestController {

    @Autowired(required = false)
    HttpServletRequest request;

    @Autowired(required = false)
    HttpServletResponse response;

    @Autowired
    private TransferService transferService;

    @GetMapping("/test/cookie")
    public Response testCookie() {
        Cookie[] cookies = request.getCookies();
        Map<String, Object> data = ResultUtil.getData();
        if (cookies == null || cookies.length == 0) {
            response.setStatus(401);
            return ResultUtil.fail(1, "未鉴权");
        }
        data.put("cookies", cookies);
        return ResultUtil.success(data);
    }

    @GetMapping("/test/restful/type/{type}/id/{id}")
    public Response testRestful(@PathVariable String type, @PathVariable int id) {
        Map<String, Object> data = ResultUtil.getData();
        if (type != null) {
            data.put("type", type);
            data.put("id", String.valueOf(id));
            return ResultUtil.success(data);
        }
        return ResultUtil.fail(2, "未获取到参数");
    }

    @GetMapping("/test/get/param")
    public Response testGetParameter(@RequestParam(value = "id", required = false) int id, @RequestParam(value = "type") String type1) {
        Map<String, Object> data = ResultUtil.getData();
        if (type1 != null) {
            data.put("type", type1);
            data.put("id", String.valueOf(id));
            return ResultUtil.success(data);
        }
        return ResultUtil.fail(2, "未获取到参数");
    }

    @PostMapping("/test/post")
    public Response testGetPostParam(@RequestParam("id") int id, @RequestParam("type") String type1) {
        Map<String, Object> data = ResultUtil.getData();
        if (type1 != null) {
            data.put("type", type1);
            data.put("id", String.valueOf(id));
            return ResultUtil.success(data);
        }
        return ResultUtil.fail(2, "未获取到参数");
    }

    @GetMapping("/test/match")
    Response testMatch() throws IOException {
        Map<String, Object> data = ResultUtil.getData();
        List<ResourceMatchV0> resourceMatchV0s = transferService.matchResourceInformation("Dragon Quest The Adventure Of Dai - S01E51 VOSTFR 1080p WEB x264 -NanDesuKa (ADN).mkv", "8c7dd922ad47494fc02c388e12c00eac", 586814875, 1442);
        data.put("resourceMatchV0s", resourceMatchV0s);
        return ResultUtil.success(data);
    }
}
