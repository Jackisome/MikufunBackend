package com.devteam.mikufunbackend.controller;

import com.devteam.mikufunbackend.service.serviceInterface.UserService;
import com.devteam.mikufunbackend.util.Response;
import com.devteam.mikufunbackend.util.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @author Jackisome
 * @date 2021/9/30
 */
@RestController
@RequestMapping("/api/v1/login")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("")
    public Response login(@RequestParam String password) {
        userService.validatePassword(password);
        Map<String, Object> data = ResultUtil.getData();
        data.put("token", userService.getAuthToken());
        return ResultUtil.success(data);
    }
}
