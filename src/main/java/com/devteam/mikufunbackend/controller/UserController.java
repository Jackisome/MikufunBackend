package com.devteam.mikufunbackend.controller;

import com.devteam.mikufunbackend.constant.ResponseEnum;
import com.devteam.mikufunbackend.constant.RuntimeVariable;
import com.devteam.mikufunbackend.entity.LoginV0;
import com.devteam.mikufunbackend.service.serviceInterface.UserService;
import com.devteam.mikufunbackend.util.ParamUtil;
import com.devteam.mikufunbackend.util.Response;
import com.devteam.mikufunbackend.util.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @author Jackisome
 * @date 2021/9/30
 */
@RestController
@RequestMapping("/api/v1")
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired(required = false)
    HttpServletRequest request;

    @PostMapping("/login")
    public Response login(@RequestParam String password) {
        Map<String, Object> data = ResultUtil.getData();
        LoginV0 loginV0 = userService.getAuthToken(password);
        data.put("user", loginV0);
        return ResultUtil.success(data);
    }

    @PutMapping("/logout")
    public Response logout() {
        userService.removeToken(request);
        return ResultUtil.success();
    }

    @GetMapping("/userimage")
    public Response getUserImage() {
        Map<String, Object> data = ResultUtil.getData();
        String userImageUrl = userService.getUserImageUrl()+ RuntimeVariable.userImageName;
        data.put("userImageUrl", userImageUrl);
        if (ParamUtil.isNotEmpty(userImageUrl)) {
            return ResultUtil.success(data);
        } else {
            return ResultUtil.fail(ResponseEnum.FILE_ERROR);
        }
    }
}
