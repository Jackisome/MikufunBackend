package com.devteam.mikufunbackend.controller;

import com.devteam.mikufunbackend.entity.SearchResourceRespVO;
import com.devteam.mikufunbackend.entity.SearchResourceIntroductionVO;
import com.devteam.mikufunbackend.entity.SearchSubGroupRespVO;
import com.devteam.mikufunbackend.entity.SearchResourceTypeRespVO;
import com.devteam.mikufunbackend.service.serviceInterface.SearchService;
import com.devteam.mikufunbackend.util.Response;
import com.devteam.mikufunbackend.util.ResultUtil;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Jackisome
 * @date 2021/9/26
 */
@RestController
@RequestMapping("/search")
public class SearchController {

    @Resource
    private SearchService searchService;

    @GetMapping("/introduction")
    public Response getIntroduction(@RequestParam("keyword") String keyword,
                                    @RequestParam("resourceId") Integer resourceId) {
        List<SearchResourceIntroductionVO> searchResourceIntroductionVOs = searchService.getResourceIntroduction(keyword, resourceId);
        Map<String, Object> data = new HashMap<>();
        data.put("entries", searchResourceIntroductionVOs);
        return ResultUtil.success(data);
    }

    @GetMapping("/resource")
    public Response getResource(@RequestParam("keyword") String keyword,
                                @RequestParam(value = "typeId", required = false) Integer typeId,
                                @RequestParam(value = "subtitlesGroupId", required = false) Integer subgroupId) {
        List<SearchResourceRespVO> searchResourceRespVOList = searchService.getResource(keyword, typeId, subgroupId);
        Map<String, Object> data = new HashMap<>();
        data.put("entries", searchResourceRespVOList);
        return ResultUtil.success(data);
    }

    @GetMapping("/group")
    public Response getSubGroups() {
        List<SearchSubGroupRespVO> searchSubGroupRespVOs = searchService.getSubgroups();
        Map<String, Object> data = new HashMap<>();
        data.put("subtitlesGroups", searchSubGroupRespVOs);
        return ResultUtil.success(data);
    }

    @GetMapping("/type")
    public Response getType() {
        List<SearchResourceTypeRespVO> searchResourceTypeRespVOs = searchService.getResourceTypes();
        Map<String, Object> data = new HashMap<>();
        data.put("types", searchResourceTypeRespVOs);
        return ResultUtil.success(data);
    }

}
