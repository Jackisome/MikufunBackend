package com.devteam.mikufunbackend.controller;

import com.devteam.mikufunbackend.util.Response;
import com.devteam.mikufunbackend.util.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Jackisome
 * @date 2021/9/26
 */
@RestController
public class TestController {

    @GetMapping("/test")
    public Response test(HttpServletRequest request) {
        String uri = request.getRequestURI();
        System.out.println(uri);
        return ResultUtil.success();
    }
}
