package com.devteam.mikufunbackend.controller;

import com.devteam.mikufunbackend.util.Response;
import com.devteam.mikufunbackend.util.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
    public Response testGetParameter(@RequestParam(value = "id", required = false) int id, @RequestParam(value = "type", required = true) String type1) {
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
}
