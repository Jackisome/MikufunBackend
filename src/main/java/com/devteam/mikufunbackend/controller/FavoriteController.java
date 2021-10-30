package com.devteam.mikufunbackend.controller;

import com.devteam.mikufunbackend.service.serviceInterface.FavoriteService;
import com.devteam.mikufunbackend.util.Response;
import com.devteam.mikufunbackend.util.ResultUtil;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Map;

/**
 * @author Jackisome
 * @date 2021/9/26
 */
@RestController
@RequestMapping("/api/v1/favorite")
public class FavoriteController {

    @Resource
    private FavoriteService favoriteService;

    @PutMapping("/resource/{resourceId}/status/{status}")
    public Response setFavoriteStatus(@PathVariable("resourceId") Integer resourceId,
                                        @PathVariable("status") String status){
        favoriteService.setFavoriteStatus(resourceId, status);
        return ResultUtil.success();
    }

    @GetMapping("/status/{status}")
    public Response getFavoriteResourceByStatus(@PathVariable("status") String status) {
        Map<String, Object> data = ResultUtil.getData();
        data.put("entries", favoriteService.getFavoriteResourceByStatus(status));
        return ResultUtil.success(data);
    }

}
